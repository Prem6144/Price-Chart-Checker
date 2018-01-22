class CommentRatingsController < ApplicationController
	def index
		@comment_ratings = CommentRating.all
	end
	def edit
		@comment_rating = CommentRating.find(params[:id])
	end

	def update
		@comment_rating = CommentRating.find(params[:id])
    respond_to do |format|
    	if @comment_rating.update(comment_rating_params)
        format.html { redirect_to comment_ratings_path, notice: 'Successfully updated.' }
        format.xml  { render :xml => @comment_rating, :status => :created, :comment_rating => @comment_rating }
      else
        format.html { render :edit }
        format.json { render json: @comment_rating.errors, status: :unprocessable_entity }
      end
    end
	end
	def destroy
		@comment_rating = CommentRating.find(params[:id])
    respond_to do |format|
      if @comment_rating.destroy
        format.html { redirect_to comment_ratings_path, :notice => 'Successfully removed.' }
        format.xml  { render :xml => @comment_rating, :status => :created, :comment_rating => @comment_rating }
      end
    end
	end

	private
	def comment_rating_params
		params.require(:comment_rating).permit(:app_user_id, :deal_id, :rating_point, :status, :comment_title, :comment_text)
	end
end	
