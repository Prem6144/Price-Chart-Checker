class Api::V1::NotificationsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def new
		@notification = Notification.new
	end

  def fetch_notification
    @notification = Notification.find_by_app_user_id(params[:app_user_id])
    if @notification.present?
      render :status => 200,
             :json => {
                        :success => true,
                        :notification => @notification.as_json(:except => [:created_at, :updated_at])
                      }
    else
      render :json => { :success => false }
    end  
  end

	def create
		@notification = Notification.find_by_app_user_id(params[:app_user_id])
		if @notification.present?
      if @notification.update(notification_params)
        render :status => 200,
           		 :json => { :success => true }
      else
        render :status => 401,
           		 :json => { :success => false }	
      end
		else
			@notification = Notification.new(notification_params) 
      if @notification.save
        render :status => 200,
           		 :json => { :success => true }
      else
        render :status => 401,
            	 :json => { :success => false }
      end
		end
	end

  def update_notification
    #@notification = 
  end

	private
	def notification_params
		params.permit(:app_user_id, :recieve_notification, :day, :recieve_trending_deals,:repeat_notification_frequency,:trending_deal_frequency,:receive_call,:min_service_provider_rating,:min_deal_rating,:receive_email, :receive_text)
	end

end	

  
  