class Api::V1::CashoutInfosController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
	end

	def create
		if params[:app_user_id].present?
			app_user = AppUser.find_by_id(params[:app_user_id])
			if app_user.total_amount >= params[:reedeem_amount].to_f
				cashout = CashoutInfo.create(cashout_info_params)
				app_user.total_amount = app_user.total_amount - params[:reedeem_amount].to_f
				app_user.save
				AppUserMailer.cashout_email(app_user,cashout).deliver_now
				render :json => { :success => true, message: "Successfully deducted your amount" }
			else
				render :json => { :success => false, message: "You do not have enough amount in your wallet" }
			end
		else
				render :json => { :success => false }
		end
	end


	private
	def cashout_info_params
		params.permit(:app_user_id,:reedeem_amount,:email_id,:is_cash,:gift_card_type)
	end

end

