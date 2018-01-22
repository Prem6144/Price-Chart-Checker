class Api::V1::RatingsController < ApplicationController

	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
		@ratings = Rating.where("deal_id = ?", params[:deal_id])
		@average_rating = @ratings.average(:rating_point)
		if @ratings.present?
			render :status => 200,
						 :json => { :success => true,
												:average_rating => @average_rating
											}
		else
			render :status => 401,
							:json => { :success => false }
		end	
	end

	def create
		@rating = Rating.where("app_user_id = ? AND deal_id = ?", params[:app_user_id],params[:deal_id]).take
		if @rating.present?
      #@rating.update(rating_params)
      render :json => { :success => false }
		else
			@rating = Rating.new(rating_params) 
      if @rating.save
        render :status => 200,
           		 :json => { :success => true }
      else
        render :status => 401,
           		 :json => { :success => false }
      end
		end
	end

	private
	def rating_params
		params.permit(:app_user_id, :deal_id, :rating_point)
	end

end	
