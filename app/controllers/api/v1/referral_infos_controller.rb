class Api::V1::ReferralInfosController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def create
		@referral_info = ReferralInfo.new(referral_info_params)
		if params[:event].present? && params[:first_referring_identity].present?
			@referral_info.event = params[:event]
			@referral_info.first_referring_identity = params[:first_referring_identity]
			@referral_info.save!
			render :status => 200,
               :json => { :success => true }
		end
	end

	private
	def referral_info_params
		params.permit(:first_referring_identity, :referred_user, :event, :referring_user_coins, :referred_user_coins)
	end
end	