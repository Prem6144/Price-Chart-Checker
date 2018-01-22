class Api::V1::SessionsController < Devise::SessionsController
  skip_before_filter :verify_authenticity_token, :except => [:create]
                     #:if => Proc.new { |c| c.request.format == 'application/json' }
  respond_to :json

  def create
    @app_user = AppUser.find_by_email(params[:email])
    if @app_user.present?
      if params[:gcm_id].present? && params[:device_flag].present?
        @app_user.device_flag = params[:device_flag]
        @app_user.gcm_id = params[:gcm_id]
        @app_user.save
      end
      if @app_user.active == true
        #warden.authenticate!(:scope => resource_name, :recall => "#{controller_path}#failure")
        if @app_user.unhashed_password == params[:password]
          if @app_user.service_preferences.present?
            @user_preference = true
          else
            @user_preference = false
          end
          render :status => 200,
               :json => { :success => true,
                          :info => "Logged in",
                          :data => @app_user.as_json(:except => [:created_at, :updated_at, :avatar], :methods => [:avatar_url]),
                          :user_preference => @user_preference
                        }
        else
          render :status => 401,
               :json => { :success => false,
                          :info => "Sorry, this account has been deactivated.",
                          :data => {} 
                        }
        end            
      else  
        render :status => 401,
               :json => { :success => false,
                          :info => "Sorry, this account has been deactivated.",
                          :data => {} 
                        }
      end  
    else
      render :status => 401,
             :json => { :success => false }
    end            
  end

  def destroy
    warden.authenticate!(:scope => resource_name, :recall => "#{controller_path}#failure")
    current_user.update_column(nil)
    render :status => 200,
           :json => { :success => true,
                      :info => "Logged out",
                      :data => {} 
                    }
  end

  def failure
    render :status => 401,
           :json => { :success => false,
                      :info => "Login Failed",
                      :data => {} }
  end

  private
  def app_user_params
    params.permit(:first_name, :last_name, :email, :state, :city, :zip, :password, :unhashed_password, :address, :active, :avatar, :gcm_id, :device_flag)
  end

  #def create
  #  app_user = AppUser.authenticate(params[:email], params[:password])
  #  if app_user
  #    session[:app_user_id] = app_user.id
  #    render :status => 200,
  #         :json => { :success => true,
  #                    :info => "Logged in",
  #                    :data => {} 
  #                  }
  #  else
  #    render :status => 401,
  #         :json => { :success => false,
  #                    :info => "Login Failed",
  #                    :data => {} 
  #                  }
  #  end
  #end

end