


class Api::V1::AppUsersController < ApplicationController

  skip_before_filter :verify_authenticity_token
  respond_to :json
  include DashboardsHelper

  def new
    @app_user = AppUser.new
  end

  def update_app_user
    app_user = AppUser.find_by_email(params[:email])
    if app_user.present?
      if params[:user_type].present? || params[:first_name].present? || params[:last_name].present? || params[:address].present? || params[:state].present? || params[:city].present? || params[:zip].present? || params[:picture_data].present?
        app_user.update(app_user_params)
        # code=SecureRandom.hex(5)
        # app_user.update_attributes(:email_verification_token=>code)
        # app_user.update_attributes(:email_verified=>true)
        app_user.update_attributes(:primary_id=>params[:primary_id], :secondary_id => params[:secondary_id])
        if app_user.user_type == AppUser::BUSINESS
          business = Business.create_business(params)
          business_addresses = BusinessAddress.create_business_addresses(params,business.id)
          business_user = BusinessAppUser.create_business_app_user(business.id,app_user.id) if business.present?
        else
          app_user_addresses = AppUserAddress.create_app_user_addresses(params,app_user.id)
        end
        render :status => 200,
               :json => { :success => true }
      else
        render :status => 400,
               :json => { :success => false }
      end
    else
      render :status => 400,
             :json => { :success => false }
    end
  end

  def create
    @app_user = AppUser.find_by_email(params[:email])
    if @app_user.present?
      render :status => 400,
             :json => {:success => false}
    else
      @app_user = AppUser.new(app_user_params)
      @app_user.unhashed_password = params[:password]
      # @app_user.referral_code = rand(36**4).to_s(36).upcase

      @app_user.referral_code = (decode_api_data(params[:first_name]).split(" ").first + rand(36**4).to_s(36)).upcase
      code=SecureRandom.hex(5)
      @app_user.email_verification_token=code
      # @app_user.email_verified=true
      if @app_user.save
        render :status => 200,
               :json => { :success => true, :app_user_id => @app_user.id }
        AppUserMailer.delay.send_verification_mail(@app_user.id,code)
        AppUserMailer.delay.sign_up_mail(@app_user)
      else
        render :status => 400,
               :json => { :success => false }
      end
    end
  end

  def get_app_user
      app_user = AppUser.find_by_id(params[:id]) if params[:id].present?
      app_user = AppUser.find_by_email(params[:email]) if params[:email].present?
      if app_user.present?
        if app_user.user_type == AppUser::BUSINESS
          app_user_addresses = app_user.business_app_users.first.business.business_addresses.where(address_type: 2).order('updated_at DESC').first rescue nil if app_user.business_app_users.present?
        else
          app_user_addresses = app_user.app_user_addresses.where(address_type: 2).order('updated_at DESC').first rescue nil
        end
          user_preference = app_user.service_preferences.present? ? true :false
          render :status => 200,
                 :json => {
                     :success => true,
                     :app_user => app_user.as_json(:except => [:created_at, :updated_at, :avatar], :methods => [:avatar_url]),
                     :user_preference => user_preference,
                     :app_user_addresses => app_user_addresses.as_json,
                     :business => app_user.business_app_users.first.try(:business).as_json(:except => [:is_service_address_same,:is_shipping_address_same])
                 }
      else
        render :status => 404,
               :json => { :success => false }
      end
  end

  def get_app_user_bck
    if params[:id].present? && params[:email].blank?
      app_user = AppUser.find_by_id(params[:id])
      if app_user.present?
        render :status => 200,
               :json => {
                   :success => true,
                   :app_user => app_user.as_json(:except => [:created_at, :updated_at, :avatar], :methods => [:avatar_url]),
                   :app_user_addresses => app_user.app_user_addresses.as_json,
                   :business => app_user.business_app_users.first.try(:business).as_json
               }
      else
        render :status => 404,
               :json => { :success => false }
      end
    elsif params[:email].present? && params[:id].blank?
      app_user = AppUser.find_by_email(params[:email])
      if app_user.present?
        if app_user.service_preferences.present?
          user_preference = true
        else
          @user_preference = false
        end
        render :status => 200,
               :json => {
                   :success => true,
                   :app_user => app_user.as_json(:except => [:created_at, :updated_at, :avatar], :methods => [:avatar_url]),
                   :user_preference => user_preference,
                   :app_user_addresses => app_user.app_user_addresses.as_json,
                   :business => app_user.business_app_users.first.try(:business).as_json
               }
      else
        render :status => 404,
               :json => { :success => false }
      end
    else
      render :status => 404,
             :json => { :success => false }
    end
  end

  def recover_password
    @app_user = AppUser.find_by_email(params[:email])
    if @app_user.present?
      @email = @app_user.email
      @app_user.send_password_reset 
      # AppUserMailer.recover_password_email(@app_user).deliver_now
      render  :json => { :success => true }
    else
      render  :status => 404,
              :json => { :success => false }
    end
  end

  def my_referral_code
    if params[:app_user_id].present?
      app_user = AppUser.find(params[:app_user_id])
      app_user_code = app_user.try(:referral_code)
      refer_status = app_user.try(:refer_status)
      render  :json => { :success => true, :app_user_code => app_user_code, :refer_status => refer_status}
    else
      render  :json => { :success => false }
    end
  end

  def referrals_and_gifts
    if params[:app_user_id].present?
      params[:referrer_id] = params[:app_user_id]
      app_user = AppUser.find_by_id(params[:app_user_id])
      gifts = app_user.gifts.order("id DESC")
      gift_amount = gifts.collect(&:amount).sum
      account_referral = AccountReferral.where("referral_id = ? or referrer_id = ?", params[:app_user_id], params[:app_user_id]).order("id DESC")
      total_referral_amount = AccountReferral.where("referral_id = ?",params[:app_user_id]).map{ |referral_gift| referral_gift.referral_gift_amount.referral_amount}.sum +  AccountReferral.where("referrer_id = ?",params[:app_user_id]).map{ |referral_gift| referral_gift.referral_gift_amount.referrer_amount}.sum
      total_amount = gift_amount + total_referral_amount
      total_redeem_amount =  app_user.total_amount
      redeem_amount = (gift_amount + total_referral_amount) - total_redeem_amount
      render  :json => { :success => true, :account_referral => account_referral.as_json(:only=>[],:param_for_message => params[:app_user_id],:param_for_image =>params[:app_user_id]), :gifts=> gifts.as_json(:methods => :gift_image_url, :except => :image), total_referral_amount: total_referral_amount, gift_amount: gift_amount, total_amount: total_amount, redeem_amount: redeem_amount}
      # :include => {:deal => {:methods => :deal_image_url}}
    else
      render  :json => { :success => false }
    end
  end



  def refer_contact
    if params['referred_contacts'].class.to_s == 'Array'
      error = false
      params['referred_contacts'].each do |p|
        keys = p.keys
        arr_match = ["app_user_id", "email_id", "mobile_no", "name"] - keys
        if arr_match.present?
          error = true
          break
        end
      end
      unless error
        params.permit!
        referred_contacts = ReferContactDetail.create_referred_users(params['referred_contacts'])
        render  :json => { :success => true,:referred_contacts => referred_contacts.as_json}
      else
        render  :json => { :success => false,:message => 'Some keys are missing in JSON, please verify.'}
      end
    else
      render  :json => { :success => false,:message => 'Invalid Json Input'}
    end
  end

  def you_save
    if params[:id].present?
      allowed_best_deal_sum = 0
       service_preference_sum = 0
      app_user = AppUser.find_by_id(params[:id])
      if app_user.service_preferences.present?
       # service_preference_sum = app_user.service_preferences.collect(&:price).sum
        app_user.service_preferences.map do |sp|
          if category_best_deal(app_user.user_type,sp,app_user.zip,1,false).present?
        service_preference_sum = service_preference_sum + sp.price
            allowed_best_deal_sum=allowed_best_deal_sum  + category_best_deal(app_user.user_type,sp,app_user.zip,1,false).effective_price.to_f
          else
           allowed_best_deal_sum=allowed_best_deal_sum
          end
        end
        puts "service_preference_sum=#{service_preference_sum}"
        puts "allowed_best_deal_sum=#{allowed_best_deal_sum}"
        you_save = (12*(service_preference_sum - allowed_best_deal_sum.to_f))
        if session[:new_user] == true
          session[:new_user] = false
        end
        if you_save!=0
          yousaveprecision=you_save.round(1).to_s.split(".")[1].to_i
            if yousaveprecision > 5
              you_save=you_save.ceil.to_i
            elsif yousaveprecision < 5
              you_save=you_save.floor.to_i
            elsif yousaveprecision == 5
              you_save=you_save.floor.to_i
            end
          else
            you_save=0
        end

        render  :json => { :success => true, :you_save => you_save}
      else
        render  :json => { :success => false}
      end
    end
  end

  def verify_user
	if params[:id].present?
    data=AppUser.find(params[:id])
    if data.email_verified == true
      render :json=>{
        verified:true
      }
    else
      render :json=>{
        verified:false
      }
    end
else 
render :json=>{
        verified:false
      }
end
  end

  def primary_information
    if params[:app_user_id].present?
      primary_ids = AppUser::PRIMARY_ID
       secondary_ids = AppUser::SECONDARY_ID
      render  :json => { :success => true, primary_ids: primary_ids,secondary_ids: secondary_ids}
    else
      render  :json => { :success => false}
    end

  end

  private
  def app_user_params
    params[:avatar] = decode_picture_data(params[:picture_data]) if params[:picture_data].present?
    params.permit(:user_type,:business_name,:first_name, :last_name, :email, :state, :city, :zip, :password, :unhashed_password, :address, :active, :avatar, :gcm_id, :device_flag,:referral_code,:refer_status,:primary_id,:secondary_id,:primary_id_number,:secondary_id_number,:mobile)
  end

  def decode_picture_data(picture_data)
    # decode the base64
    data = StringIO.new(Base64.decode64(picture_data))

    # assign some attributes for carrierwave processing
    data.class.class_eval { attr_accessor :original_filename, :content_type }
    data.original_filename = "upload.png"
    data.content_type = "image/png"

    # return decoded data
    data
  end

  # def referral_points(app_user)
  #    @total_coin_earn = AccountReferral.all.where(:referral_id => @app_user.id).collect(&:referral_gift_coins).sum + AccountReferral.all.where(:referrer_id => @app_user.id).collect(&:referrer_gift_coins).sum

  # end

end
