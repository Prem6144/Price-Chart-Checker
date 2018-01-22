class UsersController < ApplicationController
  skip_before_filter :authenticate_user!, :only => [:reset_password, :set_reset_password]
  #before_action :user_params, :only => [:create]
  load_and_authorize_resource

  def index
    @users = User.all
  end
  
  def new
    @user = User.new
  end

  def create
    @user = User.new(user_params)
    
    respond_to do |format|
      if @user.save
        format.html { redirect_to users_path, :notice => 'You have successfully created a user' }
        format.xml  { render :xml => @user, :status => :created, :user => @user }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @user.errors, :status => :unprocessable_entity }
      end
    end
  end


  def update
    respond_to do |format|
      if @user.update(user_params)
        format.html { redirect_to users_path, notice: 'You have successfully updated a user.' }
        format.xml  { render :xml => @user, :status => :created, :user => @user }
      else
        format.html { render :edit }
        format.json { render json: @user.errors, status: :unprocessable_entity }
      end
    end
  end

  def edit_password
    @user = User.find(params[:id])
  end
  
  def reset_password
  
  end
  
  def set_reset_password
    email = params[:email]
    reset_token = params[:reset_token]
    password = params[:password]
    con_password = params[:confirm_password]
    if email.blank? || reset_token.blank?
      flash[:alert] = 'Please make sure to enter correct email and reset token.'
      render :reset_password
    else
      user = User.find_by_email_and_reset_password_token(email, reset_token)
      if user.present?
        if User.is_valid_password?(password, con_password)
            user.password_updated_at = Date.today 
            #user.audit_comment = "Password for the User with email #{user.email} has been reset." 
            random_identifier = SecureRandom.urlsafe_base64(5) #secure
            if user.update_attributes({:password => password, :confirm_password => con_password, :reset_password_token => random_identifier})
              flash[:notice] = 'Password reset successfully.'
              redirect_to new_user_session_path
            else
              flash[:alert] = 'Due to some issue, we could not able to process your password reset request.'
              render :reset_password
            end     
        else
            flash[:alert] = 'Password must be at least 6 characters and include one number and one letter'
            render :reset_password
        end
      else
        flash[:alert] = 'Email and reset token combination is not correct.'
        render :reset_password  
      end
    end
  end

  def update_password
    @user = User.find(params[:id])
    
    params[:user][:password_updated_at] = Date.today
    #params[:user][:audit_comment] = "Password for the User with email #{@user.email} has been updated." 
    if @user.update_attributes(params[:user])
      #sign_in(@user, :bypass => true)
      if @user.id == current_user.id
        sign_out(current_user) # log them out and force them to use the reset token to create a new password
        redirect_to new_user_session_path
      else
        redirect_to users_path, :notice => "The Password of #{@user.email} has been updated!"
      end
    else
      #raise @user.errors.full_messages.inspect
      render :edit,:locals => { :resource => @user, :resource_name => "user" }
    end
  end


  def destroy
    @user = User.find(params[:id])
    
    respond_to do |format|
      if @user.destroy
        format.html { redirect_to users_path, :notice => 'You have successfully removed a user' }
        format.xml  { render :xml => @user, :status => :created, :user => @user }
      end
    end
  end


  private

  def user_params
    params.require(:user).permit(:name,:email,:password,:password_confirmation,:role,:password_updated_at,:enabled)
  end
  
end
