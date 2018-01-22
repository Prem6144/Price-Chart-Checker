class Api::V1::AccountReferralsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
	end

	def create
		# raise params.to_s
		if params[:referral_code].present? && params[:referrer_id].present?
			referral_code = params[:referral_code].upcase
			app_user_id = AppUser.find_by_referral_code(referral_code).try(:id)
			if app_user_id.present? && app_user_id != params[:referrer_id].to_i
				params[:referral_id] = app_user_id
				account_referral = AccountReferral.new(account_referral_params)
				referral_gift_amount_id = ReferralGiftAmount.where(is_active: true).first.try(:id)
				if account_referral.save
					if referral_gift_amount_id.present?
						account_referral_amount = AccountReferralAmount.create(account_referral_id: account_referral.id, referral_gift_amount_id: referral_gift_amount_id)
						gift_received = account_referral.referral_gift_amount.referrer_amount
						referral_gift = account_referral.referral_gift_amount.referral_amount
						referral = AppUser.find_by_id(app_user_id)
						referral.total_amount = referral.total_amount + referral_gift
						referral.save
						app_user = AppUser.find_by_id(params[:referrer_id])
						app_user.refer_status = true
						app_user.total_amount = app_user.total_amount + gift_received
						app_user.save
						message = "Congratulations!! You have earned $#{gift_received}"
						if params[:from_site]
							flash[:success]=message
							redirect_to "/website/app_users/profile?goto=earnings"
						else
							render :status => 200,
				        	   :json => {
				                     	:success => true,
				                     	:gift => gift_received,
				                     	:message => message
				                     }
						end
					else
						render :json => { :success => false, :message => "Sorry! No Gifts Available for now." }
					end
				else
					if account_referral.errors.full_messages[0] == "Referrer has already been taken"
						message = "Referral Id is already used."
						render :json => { :success => false, :message => message }
					else
						render :json => { :success => false }
					end
				end
			else
			if params[:from_site]
				flash[:success]="Wrong Code!"
				redirect_to "/website/app_users/profile?goto=earnings&error=wrong_code"
			else
				render :json => { :success => false, message: "Invalid Code" }
			end
			end
		end

	end

	private
	def account_referral_params
		params.permit(:referral_id,:referrer_id,:referral_gift_coins,:referrer_gift_coins)
	end

end
