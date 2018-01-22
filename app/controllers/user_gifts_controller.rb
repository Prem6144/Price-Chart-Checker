class UserGiftsController < ApplicationController
	def index
    @user_gifts = UserGift.paginate(:page => params[:page], :per_page => 20).order("id ASC")
	end
	def new
    @user_gift = UserGift.new
	end
	def edit
     @user_gift = UserGift.find(params[:id])
	end
	def create
     @user_gift = UserGift.new(user_gift_params)    
    respond_to do |format|
      if @user_gift.save
        format.html { redirect_to user_gifts_path, :notice => 'You have successfully created a user gift' }
        format.xml  { render :xml => @user_gift, :status => :created, :user_gift => @user_gift }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @user_gift.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @user_gift = UserGift.find(params[:id])
    respond_to do |format|
      if @user_gift.update(user_gift_params)
        format.html { redirect_to user_gifts_path, notice: 'You have successfully updated a User Gift.' }
        format.xml  { render :xml => @user_gift, :status => :created, :user_gift => @user_gift }
      else
        format.html { render :edit }
        format.json { render json: @user_gift.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @user_gift = UserGift.find(params[:id])
    respond_to do |format|
      if @user_gift.destroy
        format.html { redirect_to user_gifts_path, :notice => 'You have successfully removed a user gift' }
        format.xml  { render :xml => @user_gift, :status => :created, :user_gift=> @user_gift }
      end
    end
    	
   end

	private
  def user_gift_params
   params.require(:user_gift).permit(:app_user_id,:gift_id,:order_id,:status)
  end
end
