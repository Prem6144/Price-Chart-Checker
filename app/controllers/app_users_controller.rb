class AppUsersController < ApplicationController
	def index
		@app_users = AppUser.paginate(:page => params[:page], :per_page => 20).order("id DESC")
		respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv <<
          [	"ID",
          	"First Name",
          	"Last Name",
          	"Email",
          	"Address",
          	"State",
          	"City",
          	"Zip",
          	"Avatar",
          	"Device Flag",
          	"Sign in Count",
          	"Encrypted Password",
          	"Password",
          	"Device Id",
          	"ResetPassword Token",
          	"ResetPasswordSent At",
          	"RememberCreated At",
          	"CurrentSignIn At",
          	"LastSignIn At",
          	"CurrentSignIn IP",
          	"LastSignIn IP",
          	"Created At",
          	"Updated At",
          ]

          # data rows
          AppUser.all.order("id ASC").each do |app_user|
            csv <<
            [ app_user.id,
              app_user.first_name,
              app_user.last_name,
              app_user.email,
              app_user.address,
              app_user.state,
              app_user.city,
              app_user.zip,
              app_user.avatar_url,
              app_user.device_flag,
              app_user.sign_in_count,
              app_user.encrypted_password,
              app_user.unhashed_password,
              app_user.gcm_id,
              app_user.reset_password_token,
              app_user.reset_password_sent_at,
              app_user.remember_created_at,
              app_user.current_sign_in_at,
              app_user.last_sign_in_at,
              app_user.current_sign_in_ip,
              app_user.last_sign_in_ip,
              app_user.created_at,
              app_user.updated_at
            ]
          end
        end
        # send it to the browser
        send_data csv_string,
          :type => 'text/csv; charset=iso-8859-1; header=present',
          :disposition => "attachment; filename=app_users.csv"
      }
    end
	end
	def new
		@app_user = AppUser.new
	end
	def edit
		@app_user = AppUser.find(params[:id])
	end
	def create
		 
		@app_user = AppUser.new(app_user_params)
		respond_to do |format|
			if @app_user.save
				format.html { redirect_to app_users_path, :notice => 'Successfully created.' }
				format.xml { render :xml => app_user, :status => :created, :app_user => @app_user }
				format.html { render :action => "new" }
				format.xml { render :xml => app_user.errors, :status => :unprocessable_entity }
			end
		end
	end

	def update
		@app_user = AppUser.find(params[:id])
    	respond_to do |format|
      		if @app_user.update_attributes(app_user_params)
        		format.html { redirect_to app_users_path, notice: 'You have successfully updated a user.' }
        		format.xml  { render :xml => @app_user, :status => :created, :user => @app_user }
      		else
        		format.html { render :edit }
        		format.json { render json: @app_user.errors, status: :unprocessable_entity }
      		end
    	end
  	end
  	def destroy
    	@app_user = AppUser.find(params[:id])
    	respond_to do |format|
      		if @app_user.destroy
        		format.html { redirect_to app_users_path, :notice => 'You have successfully removed a user' }
        		format.xml  { render :xml => @app_user, :status => :created, :app_user => @app_user }
      		end
    	end
   end

	private
	def app_user_params
		params.require(:app_user).permit(:first_name, :last_name, :avatar, :email, :state, :city, :zip, :password, :address, :unhashed_password, :active, :gcm_id, :device_flag,:refer_status)
	end
end
