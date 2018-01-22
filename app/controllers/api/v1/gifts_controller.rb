class Api::V1::GiftsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
		@gifts = Gift.all
	end

	def create
	end

	def get_gifts
		if params[:app_user_id].present?
			@app_user = AppUser.find(params[:app_user_id])
			@gifts =@app_user.gifts.order("id DESC")
			if @gifts.present?
				@total_amount = @gifts.collect(&:amount).sum
				render 	:status => 200,
		        		:json => {
		                     	:success => true,
		                      :gifts => @gifts.as_json(:include => :orders) ,
		                      :total_amount =>  @total_amount                      
		                     }
			else
				render :json => { :success => false }
			end
		else
			render :json => { :success => false }
		end
	end

	def my_gifts
	end

	def get_rewards
		if params[:device_platform].present? && params[:reward_display_on].present?
			rewards = Reward.where(["device_platform = ? and reward_display_on = ?", params[:device_platform], params[:reward_display_on]]).last
			if rewards.present?
				render 	:status => 200,
		        		:json => {
		                     	:success => true,
		                      :rewards => rewards                      
		                     }
		  else
		    render :json => { :success => false }
		  end 	
		else
			render :json => { :success => false }
		end
	end

	private
	def order_params
		params.permit(:name, :description, :amount, :is_active, :activation_count_condition)
	end

end

 