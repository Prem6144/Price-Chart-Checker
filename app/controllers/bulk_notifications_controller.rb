class BulkNotificationsController < ApplicationController
	def index
		@bulk_notifications = BulkNotification.all
	end
	def new
		@bulk_notification = BulkNotification.new
	end
	def edit
		@bulk_notification = BulkNotification.find(params[:id])
	end
  def create
    @bulk_notification = BulkNotification.new(bulk_notification_params)
    if params[:bulk_notification][:state].present?
      @app_user = AppUser.where("state =?", params[:bulk_notification][:state])
    elsif params[:bulk_notification][:city].present?
      @app_user = AppUser.where("state =?", params[:bulk_notification][:city])
    elsif params[:bulk_notification][:zip].present?
      @app_user = AppUser.where("state =?", params[:bulk_notification][:zip])
    end
    @app_user.each do |app_user| 
      if app_user.device_flag == "android"
        gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
        registration_id = ["#{app_user.gcm_id}"]
        gcm.send(registration_id, {data: {message: "#{@bulk_notification.message}"}})
        puts "#{app_user.id} Notification sent"   
      elsif app_user.device_flag == "iphone"
        pusher = Grocer.pusher(
          certificate: "#{Rails.root}/public/certificates/dev_certificate.pem",      # required
          passphrase:  "abc123",                       # optional
          gateway:     "gateway.sandbox.push.apple.com",                      # optional; See note below.
          port:        2195,                       # optional
          retries:     3                           # optional
        )
        notification = Grocer::Notification.new(
          device_token:      "#{app_user.gcm_id}",
          alert:             "#{@bulk_notification.message}",
          badge:             42
          #category:          "a category",         # optional; used for custom notification actions
          #sound:             "siren.aiff",         # optional
          #expiry:            Time.now + 60*60,     # optional; 0 is default, meaning the message is not stored
          #identifier:        1234,                 # optional; must be an integer
          #content_available: true                  # optional; any truthy value will set 'content-available' to 1
        )
        pusher.push(notification)
      end
    end 
    respond_to do |format|
      if @bulk_notification.save
        format.html { redirect_to bulk_notifications_path, :notice => 'You have successfully sent a notification' }
        format.xml  { render :xml => @bulk_notification, :status => :created, :bulk_notification => @bulk_notification }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @bulk_notification.errors, :status => :unprocessable_entity }
      end
    end 
  end  
    
   
	def update
    @bulk_notification = BulkNotification.find(params[:id])
      respond_to do |format|
          if @bulk_notification.update(bulk_notification_params)
            format.html { redirect_to bulk_notifications_path, notice: 'Successfully Updated.' }
            format.xml  { render :xml => @bulk_notification, :status => :created, :bulk_notification => @Bulk_notification }
          else
            format.html { render :edit }
            format.json { render json: @bulk_notification.errors, status: :unprocessable_entity }
          end
      end
  end

	def destroy
    	@bulk_notification = BulkNotification.find(params[:id])
    	respond_to do |format|
          if @bulk_notification.destroy
            format.html { redirect_to bulk_notifications_path, :notice => 'Successfully Removed.' }
            format.xml  { render :xml => @bulk_notification, :status => :created, :bulk_notification => @Bulk_notification }
          end
    	end
  end

	def bulk_notification_params
		params.require(:bulk_notification).permit(:state, :city, :zip, :message, :category)
	end
end	