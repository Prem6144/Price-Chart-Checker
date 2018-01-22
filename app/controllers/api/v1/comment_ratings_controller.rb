class Api::V1::CommentRatingsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
		if params[:deal_id].present? && params[:app_user_id].present?
			@user_comment = CommentRating.where("status = ? AND deal_id = ? AND app_user_id = ?", true, params[:deal_id], params[:app_user_id]).first
			@comment_ratings = CommentRating.where("status = ? AND deal_id = ? AND app_user_id != ?", true, params[:deal_id], params[:app_user_id])
			@total_ratings = CommentRating.where("status = ? AND deal_id = ?", true, params[:deal_id])
			@average_rating = @total_ratings.average(:rating_point)
			@comment_count = CommentRating.where("status = ? AND deal_id = ?", true, params[:deal_id]).count
			if @comment_ratings.present? || @user_comment.present?
				render :status => 200,
							 :json => {
						 							:success => true,
						 							:user_comment => @user_comment.as_json(:except => [:created_at, :updated_at], :methods => [:app_user_name, :app_user_image_url, :comment_date]),
													:comment => @comment_ratings.as_json(:except => [:created_at, :updated_at], :methods => [:app_user_name, :app_user_image_url, :comment_date]),
													:average_rating => @average_rating,
													:comment_count => @comment_count
												}
			else
				render :json => {
													:success => false
												}
			end
		elsif params[:deal_id].present? && params[:app_user_id].blank?
			@comment_ratings = CommentRating.where("status = ? AND deal_id = ?", true, params[:deal_id])
			@average_rating = @comment_ratings.average(:rating_point)
			@comment_count = CommentRating.where("status = ? AND deal_id = ?", true, params[:deal_id]).count
			if @comment_ratings.present?
				render :status => 200,
							 :json => {
						 							:success => true,
													:comment => @comment_ratings.as_json(:except => [:created_at, :updated_at], :methods => [:app_user_name, :app_user_image_url, :comment_date]),
 													:average_rating => @average_rating,
													:comment_count => @comment_count
												}
			else
				render :json => {
													:success => false
												}
			end
		else
			render :json => { :success => false }
		end
	end

	def create
 		if params[:deal_id].present? && params[:app_user_id].present?
			@comment_rating = CommentRating.find_by_deal_id_and_app_user_id(params[:deal_id], params[:app_user_id])
			if @comment_rating.present?
				@comment_rating.update(comment_rating_params)
				render :status => 200,
           			:json => { :success => true }
			else
				@comment_rating = CommentRating.new(comment_rating_params)
				if @comment_rating.save
        		render :status => 200,
           		:json => { :success => true}
      	else
        		render :json => { :success => false }
      	end
			end
		else
			render :json => { :success => false }
		end
	end

	private
	def comment_rating_params
		params.permit(:app_user_id, :deal_id, :rating_point, :status, :comment_title, :comment_text)
	end
end
