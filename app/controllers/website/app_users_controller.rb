class Website::AppUsersController < ApplicationController
  layout 'website_layout'
  def new
  end

  def create
    if  request.referrer.split('/').last.match('checkout') == nil
      reset_session
    end
    @app_user = AppUser.find_by_email(params[:app_user][:email]) if params[:app_user][:email].present?
    if @app_user.present?
      redirect_to website_home_index_path
    else
      params[:app_user][:first_name]=encode_api_data(params[:app_user][:first_name])
      params[:app_user][:last_name]=encode_api_data(params[:app_user][:last_name])

      @app_user = AppUser.new(app_user_params)
      @app_user.unhashed_password = params[:app_user][:password]
      @app_user.referral_code = rand(36**4).to_s(36).upcase
      @app_user.zip = params[:app_user][:zip].present? ? encode_api_data(params[:app_user][:zip]) : encode_api_data("75024")
      if @app_user.save!
        session[:user_id] = @app_user.id
        session[:user_name] = @app_user.first_name.present? ? Base64.decode64(@app_user.first_name) : @app_user.email.split('@')[0]
        session[:user_type] = @app_user.user_type
        session[:new_user] = true
        code=SecureRandom.hex(5)
        @app_user.update_attributes(:email_verification_token=>code)
        AppUserMailer.delay.send_verification_mail(@app_user.id,code)
        AppUserMailer.delay.sign_up_mail(@app_user)

        if @app_user.user_type == AppUser::BUSINESS
          params[:business][:business_name]=encode_api_data(params[:business][:business_name])
          if params[:business][:business_type] == "SOLE_PROPRIETOR"
            params[:business][:business_type] = 0
          elsif params[:business][:business_type] == "REGISTERED"
            params[:business][:business_type] = 1
          end
           @business = Business.create_business(params)
           business_user = BusinessAppUser.create_business_app_user(@business.id,@app_user.id)
             session[:business] = Base64.decode64(@business.business_name)
          #  raise business_user.to_yaml

        end

        # flash[:notice] = 'SignUp Successfull! Please Verify your email by clicking link in your email'
        if session[:deal].present?
          redirect_to order_website_app_users_path(:deal_id=> session[:deal])
        else
          redirect_to "/website/home?new_user=true"
        end
      else
        flash[:warning] = @app_user.errors.full_messages
        redirect_to request.referrer
      end
    end
  end

  def edit

  end

  def proxy_verify
    if session[:user_id].present?
      AppUser.find(session[:user_id]).update_attributes(:email_verified=>true)
      flash[:notice] = 'proxy verified'
      redirect_to :back
    end
  end

  def update
    if session[:user_id].present?
      # raise params.to_yaml
      @app_user = AppUser.find(session[:user_id])
      # address = params[:address].present? ? params[:address] : ''
      # address1 = params[:address1].present? ? params[:address1] : ''
      # address2 = params[:address2].present? ? params[:address2] : ''
      # @app_user.address = address + '===' + address1 + '===' + address2
      # @app_user.state = params[:billing_state]
      @app_user.first_name = encode_api_data(params[:first_name])
      @app_user.last_name = encode_api_data(params[:last_name])
      @app_user.mobile =  encode_api_data(params[:mobile])
      @app_user.primary_id = params[:primary_id]
      @app_user.primary_id_number = params[:primary_id_number]
      @app_user.secondary_id = params[:secondary_id]
      @app_user.secondary_id_number = params[:secondary_id_number]
      @app_user.user_type = params[:user_type] if params[:user_type].present?
      @app_user.avatar=params[:avatar] if params[:avatar].present?
      # raise @app_user.state.to_s
      if @app_user.save!
        if @app_user.user_type == AppUser::BUSINESS
          params[:business][:federal_number]=encode_api_data(params[:business][:federal_number]) if params[:business][:federal_number].present?
          params[:business][:ssn]=encode_api_data(params[:business][:ssn]) if params[:business][:ssn].present?
          params[:business][:business_name]=encode_api_data(params[:business][:business_name]) if params[:business][:business_name].present?
          @business = Business.create_business(params)
          # raise @business.to_yaml
          if @business.present?
            address_hash = {:business_addresses => [params[:addresses]]}
            business_user = BusinessAppUser.create_business_app_user(@business.id,@app_user.id)
            business_addresses = BusinessAddress.create_business_addresses(address_hash,@business.id)
          end
        else
          address_hash = {:app_user_addresses => [params[:addresses]]}
          app_user_addresses = AppUserAddress.create_app_user_addresses(address_hash,@app_user.id)
        end

        flash[:notice] = 'User Updated successfully'
        redirect_to profile_website_app_users_path
      else
        flash[:warning] = @app_user.errors.full_messages
        redirect_to profile_website_app_users_path
      end
      # @app_user.update_attributes(:avatar=>params[:avatar])

    else
      redirect_to website_home_index_path
    end
  end

  def preferences
    if session[:user_id].present?

      if params[:notification][:recieve_trending_deals] == "on"
        params[:notification][:recieve_trending_deals]=true
      else
        params[:notification][:recieve_trending_deals]=false
      end

      if params[:notification][:receive_call] == "on"
        params[:notification][:receive_call]=true
      else
        params[:notification][:receive_call]=false
      end

      if params[:notification][:receive_email] == "on"
        params[:notification][:receive_email]=true
      else
        params[:notification][:receive_email]=false
      end

      if params[:notification][:receive_text] == "on"
        params[:notification][:receive_text]=true
      else
        params[:notification][:receive_text]=false
      end

      @notification = Notification.create_notification(params,session[:user_id])
      flash[:notice] = 'Preference Updated successfully'
      redirect_to profile_website_app_users_path
    end
  end

  def order_history
    if session[:user_id].present?
      app_user = AppUser.find(session[:user_id])
      @orders = app_user.try(:orders)
      @orders.each do |order|
        category = ServiceCategory.select(" distinct name").joins(:deals).where("deals.id = ?",order.order_items.first.deal_id).first.name.downcase
        if  params[:id] == category
          category_id = ServiceCategory.find_by_name(params[:id].capitalize).id
          @order_history = Order.joins("inner join order_items on order_items.order_id = orders.id inner join deals on deals.id = order_items.deal_id where app_user_id = "+session[:user_id].to_s+" and deals.service_category_id="+category_id.to_s)
        elsif params[:id] == "home"
          @order_history = app_user.try(:orders).order("ID DESC")
        end
      end
    end
  end


  def create_order
    if session[:user_id].present?
      @app_user = AppUser.find(session[:user_id])
      user_type = @app_user.user_type.present? ? @app_user.user_type : nil
      if [AppUser::RESIDENCE,AppUser::BUSINESS].include?(user_type)
        order = Order.new(:app_user_id => @app_user.id,:status => "new_order",:order_type => Order::TRANSACTIONAL_ORDER, :primary_id => params[:primary_id], :secondary_id => params[:secondary_id], :primary_id_number => params[:primary_id_number], :secondary_id_number => params[:secondary_id_number]  )
        order.order_id=rand(36**8).to_s(36).upcase
        params[:app_user][:first_name]=encode_api_data(params[:app_user][:first_name])
        params[:app_user][:last_name]=encode_api_data(params[:app_user][:last_name])
        params[:app_user][:mobile]=encode_api_data(params[:app_user][:mobile])
        if order.save
          order_item_hash = {:order_items => [params[:order_items]] }
          order_items = OrderItem.create_order_items(order_item_hash,order.id)
        if  order_items.first.deal.is_customisable == true
          order_equipment_hash = {:order_equipments => eval(params[:order_equipments])}
          order_attribute_hash = {:order_attributes => eval(params[:order_attributes])}
          order_extra_service_hash = {:order_extra_services => eval(params[:order_extra_services])}
          order_equipment = OrderAttribute.create_order_attributes(order_attribute_hash,order.id)
          order_equipment = OrderEquipment.create_order_equipments(order_equipment_hash,order.id)
          if order_items.first.deal.service_category_id == Deal::CELLPHONE_CATEGORY
            order_extra_services = OrderExtraService.create_order_extra_services(order_extra_service_hash,order.id)
          end
        end
          app_user_hash = {:app_user => params[:app_user] }
          @app_user_update = AppUser.update_app_user(app_user_hash,order.app_user_id,order)
          if user_type == AppUser::BUSINESS
            params[:business_addresses][:address2]=params[:business_addresses][:address2]
            params[:business_shipping_addresses][:address2]=params[:business_shipping_addresses][:address2]
            params[:business_service_addresses][:address2]=params[:business_service_addresses][:address2]
            # params[:business][:business_name]= encode_api_data(params[:business][:business_name]) if  params[:business][:business_name].present?
          else
            params[:app_user_addresses][:address2]=params[:app_user_addresses][:address2]
            params[:shipping_addresses][:address2]=params[:shipping_addresses][:address2]
            params[:service_addresses][:address2]=params[:service_addresses][:address2]
          end
          address_hash = {:app_user_addresses => [params[:shipping_addresses],params[:app_user_addresses],params[:service_addresses]] } if @app_user.user_type == "residence"
          address_hash = {:business_addresses => [params[:business_addresses],params[:business_shipping_addresses],params[:business_service_addresses]] } if @app_user.user_type == "business"
          # raise address_hash.to_yaml
          order_addresses = OrderAddress.create_order_addresses(address_hash ,order.id)
          # raise params[:business][:business_name]
          if user_type == AppUser::BUSINESS
            params[:business][:ssn]= encode_api_data(params[:business][:ssn]) if params[:business][:ssn].present?
            params[:business][:federal_number]= encode_api_data(params[:business][:federal_number]) if  params[:business][:federal_number].present?
            params[:business][:business_name]= encode_api_data(params[:business][:business_name]) if  params[:business][:business_name].present?
            business_hash = {:business => params[:business] }
            # raise business_hash.to_yaml
            business = Business.create_business(business_hash)
            if business.present?
              business_addresses = BusinessAddress.create_business_addresses(address_hash,business.id)
              business_user = BusinessAppUser.create_business_app_user(business.id,@app_user.id)
            end
            OrderMailer.delay.order_confirmation(@app_user,order)
            redirect_to "/website/app_users/order_detail?order_id=#{order.id}"
          else
            app_user_addresses = AppUserAddress.create_app_user_addresses(address_hash,@app_user.id)
            OrderMailer.delay.order_confirmation(@app_user,order)
            redirect_to "/website/app_users/order_detail?order_id=#{order.id}"
          end
        else
          redirect_to "/website/app_users/order_detail?order_id=#{order.id}"
        end
      else
        # redirect_to website_home_index_path
      end
      flash[:notice] = 'Order submitted successfully'
    end
  end

  def checkout
    @deal_details=Deal.find(params[:deal_id])
  end

  def user_addresses
    if params[:id].present?
    @addresses = AppUser.get_addresses(params)
    render :json=>{
        :status=>@addresses
      }
    end
    # if params[:id].present? and AppUser.find(params[:id]).orders.present?
    #   @addresses=AppUser.find(params[:id]).orders.last.order_addresses
    #   render :json=>{
    #       :type=>"residence_user",
    #     :status=>@addresses
    #   }
    # elsif params[:id].present? and AppUser.find(params[:id]).app_user_addresses.where(:address_type=>2).last.present?
    #   @addresses=AppUser.find(params[:id]).app_user_addresses.where(:address_type=>2).last
    #   render :json=>{
    #     :type=>"residence_user_first_order",
    #     :status=>@addresses
    #   }
    # else
    #   render :json=>{
    #     :status=>"first_order",
    #     :status=>@addresses
    #   }
    # end
  end

  def business_user_addresses
    if params[:id].present?
      @addresses = AppUser.get_addresses(params)
    # @addresses =app_user.business_app_users.last.business.business_addresses.first
    # if BusinessAddress.exists?(AppUser.find(params[:id]).business_app_users.last.business_id)
    #   @addresses=BusinessAddress.find(AppUser.find(params[:id]).business_app_users.last.business_id)
      render :json=>{
        :status=>"business_user_first_order",
        :status=>@addresses
      }
    end
  end

  def order
      if session[:user_id].present?
        # current_user_data=AppUser.find(session[:user_id])
        # if !current_user_data.first_name.present? or !current_user_data.last_name.present? or !current_user_data.mobile.present? or !current_user_data.primary_id_number.present?
        #   redirect_to "/website/app_users/profile?message=fill_profile"
        # else
          @app_user = AppUser.find(session[:user_id])
          @deal = Deal.find_by_id(params[:deal_id])
          @deal_detail = @deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [ :deal_price,:service_category_name, :service_provider_name,:deal_equipments,:deal_attributes,:additional_offers])
          @effective_price = params[:effective_price].present? ? params[:effective_price] : @deal.effective_price
          if @deal.cellphone_equipments.present? && @deal.service_category_id == Deal::CELLPHONE_CATEGORY &&@deal.is_customisable != true
            @equipments =@deal.cellphone_equipments
          end
      else
        session[:deal] = params[:deal_id]
        redirect_to checkout_website_app_users_path(deal_id:params[:deal_id])
      end
  end

  def order_detail
    @order = Order.find_by_id(params[:order_id].to_i)
  end

  def signin
    if  request.referrer.split('/').last.match('checkout') == nil
      reset_session
    end
    if request.method.eql? 'POST'
      @app_user = AppUser.authenticate(params[:user][:email], params[:user][:password])
      if @app_user.present?
        session[:user_id] = @app_user.id
        session[:user_name] = @app_user.first_name.present? ?  Base64.decode64(@app_user.first_name) : @app_user.email.split('@')[0]
        session[:zip_code] = @app_user.zip
        session[:user_type] = @app_user.user_type
        # if params[:remember_me]
        #   cookies.signed[:user_id] = { value: @app_user.id, expires: 2.weeks.from_now }
        # else
        #  cookies.signed[:user_id] = @app_user.id
        # end
        if session[:user_type] == AppUser::BUSINESS
          session[:business] = Base64.decode64(@app_user.business_app_users.last.business.business_name)
        end
        # flash[:notice] = 'Signin Successfull'
        if session[:deal].present?
          redirect_to order_website_app_users_path(:deal_id=> session[:deal])
        else
          redirect_to website_home_index_path
        end
      else
        # flash[:warning] = 'Incorrect Username or Password!'
        redirect_to request.referrer and return
      end
    else
      redirect_to website_home_index_path
    end
  end

  def signout
    reset_session
    # cookies.delete(:auth_token)
    redirect_to website_home_index_path and return
  end

  def check_user_email_ajax
    email = params[:email]
    user = AppUser.select('email').where(:email => email) if email.present?
    user = user.first if user.present?
    respond_to do |format|
      format.html {render :nothing => true }
      format.js { render :json => { :data => user, :layout => false}.to_json}
    end
  end

  def order_attributes
    type = params[:type]
    attribute_data = eval(type.titleize+"DealAttribute").find(params[:id])
    render :json => {
      :success =>true,
      :data => attribute_data
    }
  end

  def order_channel_packages
    package_data = ChannelPackage.find(params[:id])
    render :json => {
      :success =>true,
      :data => package_data
    }
  end

  def order_cable_equipments
    equipment_data = CableEquipment.find(params[:id])
    render :json => {
      :success =>true,
      :data => equipment_data
    }
  end
  
  def order_extra_services
    price = DealExtraService.find(params[:id]).price
    name = DealExtraService.find(params[:id]).extra_service.service_name
    render :json => {
      :success =>true,
      :price => price,
      :name => name
    }
  end  

  def order_equipment_data
    color_txt = []
    price = []
    name = []
    brand = []
    image = []
      params[:data].each do |a|
      id= a.split('_')[0]
      color_id = a.split('_')[1]
      cellphone_equipment = CellphoneEquipment.find(id)
      price << cellphone_equipment.price
      name << cellphone_equipment.cellphone_detail.cellphone_name
      brand << cellphone_equipment.cellphone_detail.brand
      image << cellphone_equipment.cellphone_detail.image.url
      color_txt << EquipmentColor.find(color_id).color_name
      end
    # raise params[:data].to_yaml
    render :json => {
      :price => price,
      :color => color_txt,
      :name  => name,
      :brand => brand,
      :image => image
    }

  end

  def check_user_credential
    email = params[:email]
    password = params[:password]
    user = AppUser.find_by_email(params[:email])
    if user.present? and user.unhashed_password == password
      if user.present?
         render :json => { :status => true}
       else
         render :json => { :status=> false}
      end
    else
     render :json => { :status => false}
    end
  end


  def profile
    if session[:user_id].present?
      @app_user = AppUser.find(session[:user_id])
      @business = Business.select('businesses.*').joins(:business_app_users).where("business_app_users.app_user_id = ?",@app_user.id).last
      # raise "text"
    else
      redirect_to website_home_index_path
    end
  end

  def forget_password
    @app_user = AppUser.find_by_email(params[:email])
    if @app_user.present?
      @email = @app_user.email
      @app_user.send_password_reset 
      # AppUserMailer.recover_password_email(@app_user).deliver_now
      flash[:notice] = 'You will receive your password soon in email.'
      redirect_to request.referrer and return
    else
      flash[:warning] = 'This Email is not registered with us.'
      redirect_to request.referrer and return
    end
  end

  def edit_password
    @app_user = AppUser.find_by_password_reset_token!(params[:id])
  end

  def update_password
     @app_user = AppUser.find_by_password_reset_token!(params[:id])
  # if @app_user.password_reset_sent_at < 3.days.ago
  #   redirect_to website_home_index_path, :alert => "Password &crarr; 
  #     reset has expired."
  # elsif 
  encrypted_password =  BCrypt::Password.create(params[:app_user][:password])
  @app_user.update_attributes(:encrypted_password=>encrypted_password, :unhashed_password => params[:app_user][:password])
  flash[:success] = "Password Changed"
  # @app_user.encrypted_password = Digest::SHA1.hexdigest("#{params[:app_user][:password]}")
  redirect_to root_url, :notice => "Password has been reset."
  # else
  #   render :edit
  # end
  end

  def contact_us
    AppUserMailer.contact_us(params[:name],params[:email],params[:subject],params[:message]).deliver_now
    flash[:notice] = 'Your Request is forwarded to Service Dealz Team.'
    redirect_to request.referrer and return
  end

  def verify_email
    secure_token=params[:secure_token]
    email_verification_token=AppUser.find(params[:user_id]).email_verification_token
    if secure_token == email_verification_token
      flash[:notice]="Email successfully verified"
      AppUser.find(params[:user_id]).update_attributes(:email_verified=>true)
      redirect_to website_home_index_path
    end
  end

  def edit_addresses
    if params[:user_type] == "residence"
      row=AppUserAddress.find(params[:row_id])
      row.update_attributes(
      :address_name => params[:address_name],
      :address1 => params[:address1],
      :address2 => params[:address2],
      :zip => params[:zip],
      :state => params[:state],
      :city=> params[:city]
      )
      flash[:success] == "address updated"
      render :json => {
        status:"saved"
      }
    else
      row=BusinessAddress.find(params[:row_id])
      row.update_attributes(
      :address_name => params[:address_name],
      :address1 => params[:address1],
      :address2 => params[:address2],
      :zip => params[:zip],
      :state => params[:state],
      :city=> params[:city]
      )
      flash[:success] == "address updated"
      render :json => {
        status:"saved"
      }

    end
  end

  def set_default_address
    if params[:user_type] == "residence"
      row = AppUserAddress.find(params[:row_id])
      row.update_attributes(:is_default=>true)
      render :json => {
        status:"updated"
      }
    else
      row = BusinessAddress.find(params[:row_id])
      row.update_attributes(:is_default=>true)
      render :json => {
        status:"updated"
      }
    end

  end

  def delete_address
    if params[:user_type] == "residence"
      AppUserAddress.find(params[:row_id]).destroy
      render :json => {
        status:"updated"
      }
    else
      BusinessAddress.find(params[:row_id]).destroy
      render :json => {
        status:"updated"
      }
    end
  end

  private
  def app_user_params
    params.require(:app_user).permit!
  end

  # def order_params
  #   params.require(:order).permit(:order_id,:deal_id,:app_user_id,:status,:deal_price,:effective_price,:activation_date,:order_type,:order_number,:security_deposit,:primary_id,:secondary_id)
  # end

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


end
