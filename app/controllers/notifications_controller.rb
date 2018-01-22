class NotificationsController < ApplicationController
	def index
		@notifications = Notification.all
	end
	def new
		@notification = Notification.new
	end
	def show
		@notification = Notification.find(params[:id])
	end
	def edit
		@notification = Notification.find(params[:id])
	end
	def create
		@notification = Notification.new(notification_params)
		respond_to do |format|
      		if @notification.save
        		format.html { redirect_to notifications_path, :notice => 'You have successfully created a notification' }
        		format.xml  { render :xml => @notification, :status => :created, :notification => @notification }
      		else
        		format.html { render :action => "new" }
        		format.xml  { render :xml => @notification.errors, :status => :unprocessable_entity }
      		end
    	end
	end
	def update
		@notification = Notification.find(params[:id])
    	respond_to do |format|
      		if @notification.update(notification_params)
        		format.html { redirect_to notifications_path, notice: 'You have successfully updated a Notification.' }
        		format.xml  { render :xml => @notification, :status => :created, :notification => @notification }
      		else
        		format.html { render :edit }
        		format.json { render json: @notification.errors, status: :unprocessable_entity }
      		end
    	end
	end
	def destroy
    @notification = Notification.find(params[:id])
    respond_to do |format|
          if @notification.destroy
            format.html { redirect_to notifications_path, :notice => 'Successfully removed.' }
            format.xml  { render :xml => @notification, :status => :created, :notification => @notification }
          end
    end
  end

	private
	def notification_params
		params.require(:notification).permit(:app_user_id, :service_notification, :day, :recieve_trending_deals)
	end
end	