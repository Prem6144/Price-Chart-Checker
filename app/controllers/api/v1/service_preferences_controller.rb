class Api::V1::ServicePreferencesController < ApplicationController
	include DashboardsHelper

	skip_before_filter :verify_authenticity_token
	respond_to :json
	before_filter :cors_set_access_control_headers
	def index
 		@service_preferences = ServicePreference.all
 	end

	def send_ios_notification
		@app_users=AppUser.all 

		@app_users.each do |app_user|
			if app_user.device_flag=="iphone"
				pusher = Grocer.pusher(
		        	certificate: "#{Rails.root}/public/certificates/servicedealzProduction.pem",      	# required
		        	passphrase:  "abc123",                       																	# optional
		        	gateway:     "gateway.sandbox.push.apple.com",                      		# optional; See note below.
		        	port:        2195,                       																		# optional
		        	retries:     3                           																		# optional
		      	)
		      	notification = Grocer::Notification.new(
		        	device_token:      "#{app_user.gcm_id}",
		        	alert:             "This is a test notification.",
		        	badge:             42
		        	#category:          "a category",         																	# optional; used for custom notification actions
		        	#sound:             "siren.aiff",         																	# optional
		        	#expiry:            Time.now + 60*60,     																	# optional; 0 is default, meaning the message is not stored
		        	#identifier:        1234,                 																	# optional; must be an integer
		        	#content_available: true                  																	# optional; any truthy value will set 'content-available' to 1
		      	)
		      	pusher.push(notification)
		    end
      	end
		render :status => 200,:json => { :success => true }

	end

	def new
		@service_preference = ServicePreference.new
		#@service_preference.build_internet_service_preference
	end

	def create
		@service_preference = ServicePreference.where("app_user_id = ? AND service_category_id = ?", params[:app_user_id], params[:service_category_id]).take
		if @service_preference.present?
			if params[:service_category_id] == '1'
 				@service_preference.internet_service_preference.update_attributes(:upload_speed=>params[:upload_speed],:download_speed => params[:download_speed])
			elsif params[:service_category_id] == '2'

				if params[:from_site]
					if !params[:domestic_call_unlimited].present?
						params[:domestic_call_unlimited] = false
				  end
					if !params[:international_call_unlimited].present?
						params[:international_call_unlimited] = false
				  end
			 	end
				@service_preference.telephone_service_preference.update(telephone_service_preference_params)
			elsif params[:service_category_id] == '3'
				@service_preference.cable_service_preference.update(:free_channels=>params[:free_channels],:premium_channels => params[:premium_channels])
			elsif params[:service_category_id] == '4'
				if params[:from_site]
					if !params[:domestic_call_unlimited].present?
						params[:domestic_call_unlimited] = false
					end
					if !params[:international_call_unlimited].present?
						params[:international_call_unlimited] = false
					end
			 	end
				@service_preference.cellphone_service_preference.update(cellphone_service_preference_params)
			elsif params[:service_category_id] == '5'
				@service_preference.bundle_service_preference.update(bundle_service_preference_params)
			elsif params[:service_category_id] == '8'
				@service_preference.update(service_preference_params)
			end
      if @service_preference.update(service_preference_params)
      	@app_user = AppUser.find_by_id(params[:app_user_id])
				@deal_type=@app_user.user_type
				best_deal=category_best_deal(@deal_type,@service_preference,@app_user.zip,1,true)
				if best_deal.present?
					DealNotifier.send_best_deal(@app_user,best_deal).deliver_now
				end
      	if params[:is_contract] == "true"
       	send_notification_no_contract
        elsif params[:is_contract] == "false"
       		send_notification_is_contract
	   	end
				if params[:from_site].present?
					redirect_to :back
				else
					render :status => 200,
								 :json => { :success => true }
				end
      else
        	render :status => 401,
           		:json => { :success => false }
      end
		else
			@service_preference = ServicePreference.new(service_preference_params)
			@service_preference.save!
			if params[:service_category_id] == '1'
				@internet_service_preference = InternetServicePreference.new(internet_service_preference_params)
				@internet_service_preference.service_preference_id = @service_preference.id
				@internet_service_preference.save!
			elsif params[:service_category_id] == '2'
				@internet_service_preference = TelephoneServicePreference.new(telephone_service_preference_params)
				@internet_service_preference.service_preference_id = @service_preference.id
				@internet_service_preference.save!
			elsif params[:service_category_id] == '3'
				@internet_service_preference = CableServicePreference.new(cable_service_preference_params)
				@internet_service_preference.service_preference_id = @service_preference.id
				@internet_service_preference.save!
			elsif params[:service_category_id] == '4'
				@internet_service_preference = CellphoneServicePreference.new(cellphone_service_preference_params)
				@internet_service_preference.service_preference_id = @service_preference.id
				@internet_service_preference.save!
			elsif params[:service_category_id] == '5'
				@internet_service_preference = BundleServicePreference.new(bundle_service_preference_params)
				@internet_service_preference.service_preference_id = @service_preference.id
				@internet_service_preference.save!
			elsif params[:service_category_id] == '8'
				@internet_service_preference = @service_preference
				@internet_service_preference.save!
				#@internet_service_preference = BundleServicePreference.new(bundle_service_preference_params)
				#@internet_service_preference.service_preference_id = @service_preference.id
				#@internet_service_preference.save!
			end
			if params[:app_user_id].present?
				id=params[:app_user_id]
			elsif session[:user_id].present?
				id=session[:user_id]
			end

       if @service_preference.save && @internet_service_preference.save #|| (@service_preference.save && @cable_service_preference.save)
       	@app_user = AppUser.find_by_id(params[:app_user_id])
				@deal_type=@app_user.user_type
				@user_preference = @app_user.service_preferences.where("service_category_id = ?",params[:service_category_id]).first
				best_deal=category_best_deal(@deal_type,@service_preference,@app_user.zip,1,true)
				if best_deal.present?
					DealNotifier.send_best_deal(@app_user,best_deal).deliver_now
				end
        	if params[:is_contract] == "true"
						@app_user=AppUser.find(id)
       		send_notification_no_contract
        	elsif params[:is_contract] == "false"
						@app_user=AppUser.find(id)
       		send_notification_is_contract
					end
					if params[:from_site].present?
						redirect_to :back
					else
						render :status => 200,
	              	 :json => { :success => true }
					end
      else
        	render :status => 401,
           		:json => { :success => false }
      end
    end
	end

	def get_service_preferences
		@service_preferences = ServicePreference.where(app_user_id: params[:app_user_id]).order("created_at DESC")
		if @service_preferences.present?
			render :status => 200,:json => {
				:success => true,
				:service_preferences => @service_preferences.as_json(:except => [:service_category_name, :service_provider_name, :created_at, :updated_at])
			}
		else
			render :json => { :success => false }
		end
	end

	def fetch_service_preferences
		@service_preference = ServicePreference.where("app_user_id = ?", params[:app_user_id]).where("service_category_id = ?",params[:category]).take
		user_type = AppUser.find(params[:app_user_id]).user_type
		if @service_preference.present?
			if params[:category] == '1'
				@internet_preference = InternetServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			elsif params[:category] == '2'
				@internet_preference = TelephoneServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			elsif params[:category] == '3'
				@internet_preference = CableServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			elsif params[:category] == '4'
				@internet_preference = CellphoneServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			elsif params[:category] == '5'
				@internet_preference = BundleServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			elsif params[:category] == '8'
				@internet_preference = @service_preference #ServicePreference.where("service_preference_id = ?", @service_preference.id ).take
			end
		end
		if @service_preference.present? && @internet_preference.present?
			json_1 = @service_preference.as_json(:except => [:service_category_name, :service_provider_name, :created_at, :updated_at])
			json_2 = @internet_preference.as_json(:except => [:service_preference_id, :created_at, :updated_at])
			@user_preference = json_1.reverse_merge!(json_2)
			render :status => 200,:json => {
				:success => true,
				#:service_preference => @service_preference.as_json(:except => [:service_category_name, :service_provider_name, :created_at, :updated_at])
				:service_preference => @user_preference
			}
		else
			if user_type == AppUser::BUSINESS
				if params[:category] == '1'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 1
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 120
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['upload_speed'] = ""
					service_preference_hash['download_speed'] = 20.00
					service_preference_hash['online_storage'] = ""
					service_preference_hash['wifi_hotspot'] = ""

				elsif  params[:category] == '2'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 2
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 35
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['domestic_call_minutes'] = ""
					service_preference_hash['international_call_minutes'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""

				elsif  params[:category] == '3'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 3
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 75
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['free_channels'] = 150
					service_preference_hash['premium_channels'] = ""

				elsif  params[:category] == '4'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 4
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 120
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['data_plan']=2.0
					service_preference_hash['plan_name'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""
					service_preference_hash['no_of_lines'] = 1
					service_preference_hash['cellphone_detail_id'] = 1
				elsif params[:category] == '5'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 5
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 100
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['upload_speed'] = ""
					service_preference_hash['download_speed'] = 50
					service_preference_hash['data'] = ""
					service_preference_hash['free_channels'] = ""
					service_preference_hash['premium_channels'] = ""
					service_preference_hash['domestic_call_minutes'] = ""
					service_preference_hash['international_call_minutes'] = ""
					service_preference_hash['data_plan'] = ""
					service_preference_hash['data_speed'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""
					service_preference_hash['bundle_combo'] = ""
				end
			else
				if params[:category] == '1'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 1
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 70
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['upload_speed'] = ""
					service_preference_hash['download_speed'] = 30.00
					service_preference_hash['online_storage'] = ""
					service_preference_hash['wifi_hotspot'] = ""

				elsif  params[:category] == '2'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 2
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 40
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['domestic_call_minutes'] = ""
					service_preference_hash['international_call_minutes'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""

				elsif  params[:category] == '3'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 3
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 50
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['free_channels'] = 100
					service_preference_hash['premium_channels'] = ""

				elsif  params[:category] == '4'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 4
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 120
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
					service_preference_hash['plan_name'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""
					service_preference_hash['no_of_lines'] = 1
					service_preference_hash['cellphone_detail_id'] = 1
				elsif params[:category] == '5'
					service_preference_hash = {}
					service_preference_hash['id'] = 0
					service_preference_hash['app_user_id'] = params[:app_user_id]
					service_preference_hash['service_category_id'] = 5
					service_preference_hash['service_provider_id'] = ""
					service_preference_hash['price'] = 60
					service_preference_hash['is_contract'] = true
					service_preference_hash['start_date'] = ""
					service_preference_hash['end_date'] = ""
service_preference_hash['data_plan']=2.0

					service_preference_hash['plan_name'] = ""
					service_preference_hash['upload_speed'] = ""
					service_preference_hash['download_speed'] = 30
					service_preference_hash['data'] = ""
					service_preference_hash['free_channels'] = 60
					service_preference_hash['premium_channels'] = ""
					service_preference_hash['domestic_call_minutes'] = ""
					service_preference_hash['international_call_minutes'] = ""
					service_preference_hash['data_plan'] = ""
					service_preference_hash['data_speed'] = ""
					service_preference_hash['domestic_call_unlimited'] = true
					service_preference_hash['international_call_unlimited'] = ""
					service_preference_hash['bundle_combo'] = ""
				end
			end
				render :status => 200,:json => {
					:success => true,
					:service_preference => service_preference_hash
				}
		end
	end

	def deselect_service_preference
		@service_preference = ServicePreference.where("app_user_id = ? AND service_category_id = ?", params[:app_user_id], params[:category]).first
		if @service_preference.present?
			@service_preference.destroy
			render :status => 200, :json => { :success => true }
		else
			render :status => 401, :json => { :success => false }
		end
	end

	def edit
		@service_preference = ServicePreference.find_by_app_user_id(params[:app_user_id])
	end

	#def update
	#	@service_preference = ServicePreference.find_by_app_user_id(params[:app_user_id])
	#	respond_to do |format|
    #    		if @service_preference.update(service_preference_params)
    #      		format.json { head :no_content, status: :true }
    #    		else
    #      		format.json { render json: @service_preference.errors, status: :false }
    #    		end
    #  	end
	#end

	private
	def send_notification_is_contract
		if params[:app_user_id].present?
			id=params[:app_user_id]
		elsif session[:user_id].present?
			id=session[:user_id]
		end
		# raise AppUser.find_by_id(id).to_yaml
		@app_user = AppUser.find_by_id(id)
		@deal_type=@app_user.user_type
		@app_user_device = @app_user.device_flag
		if @app_user.notification.present?
			@user_notification_day = @app_user.notification.day
		end
		@user_contract_end_date = params[:end_date]
		@user_preference = @app_user.service_preferences.where("service_category_id = ?",params[:service_category_id]).first

		if @user_contract_end_date.present? && @user_notification_day.present?

			best_deal=category_best_deal(@deal_type,@user_preference,@app_user.zip,1,true)
			if best_deal.present?
		      	@remaining_days = (@user_contract_end_date.to_datetime - DateTime.now).to_i
		      	if @remaining_days < @user_notification_day
		      		# DealNotifier.delay.send_best_deal(@app_user,best_deal)
		      		if @app_user_device == "android"
		      			gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
						registration_id = ["#{@app_user.gcm_id}"]
		      			gcm.send(registration_id, {data: {message: "Price : "+"#{best_deal.price}" + "\n" + "Short Description : "+"#{Rack::Utils.escape_html(best_deal.short_description)}"}})
		      		elsif @app_user_device == "iphone"
		      			pusher = Grocer.pusher(
		        			certificate: "#{Rails.root}/public/certificates/servicedealzProduction.pem",      	# required
		        			passphrase:  "abc123",                       																	# optional
		        			gateway:     "gateway.sandbox.push.apple.com",                      				# optional; See note below.
		        			port:        2195,                       																		# optional
		        			retries:     3                           																		# optional
		      			)
		      			notification = Grocer::Notification.new(
		        			device_token:      "#{@app_user.gcm_id}",
		        			alert:             "Price : "+"#{best_deal.price}" + "\n" + "Short Description : "+"#{Rack::Utils.escape_html(best_deal.short_description)}",
		        			badge:             42
		        			#category:          "a category",         																	# optional; used for custom notification actions
		        			#sound:             "siren.aiff",         																	# optional
		        			#expiry:            Time.now + 60*60,     																	# optional; 0 is default, meaning the message is not stored
		        			#identifier:        1234,                 																	# optional; must be an integer
		        			#content_available: true                  																	# optional; any truthy value will set 'content-available' to 1
		      			)
		      			pusher.push(notification)
		      		end
				end
		    end
    	end
	end
	def send_notification_no_contract
		@app_user = AppUser.find_by_id(params[:app_user_id])
		@deal_type=@app_user.user_type
		@app_user_device = @app_user.device_flag
		@user_preference = @app_user.service_preferences.where("service_category_id = ?",params[:service_category_id]).first

		best_deal=category_best_deal(@deal_type,@user_preference,@app_user.zip,1,true)
		if best_deal.present?
			# DealNotifier.delay.send_best_deal(@app_user,best_deal)
			if @app_user_device == "android"
				gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
    			registration_id = ["#{@app_user.gcm_id}"]
    			gcm.send(registration_id, {data: {message: "Price : "+"#{best_deal.price}" + "\n" + "Short Description : "+"#{Rack::Utils.escape_html(best_deal.short_description)}"}})
			elsif @app_user_device == "iphone"
				pusher = Grocer.pusher(
		        	certificate: "#{Rails.root}/public/certificates/servicedealzProduction.pem",      	# required
		        	passphrase:  "abc123",                       																	# optional
		        	gateway:     "gateway.sandbox.push.apple.com",                      		# optional; See note below.
		        	port:        2195,                       																		# optional
		        	retries:     3                           																		# optional
		      	)
		      	notification = Grocer::Notification.new(
		        	device_token:      "#{@app_user.gcm_id}",
		        	alert:             "Price : "+"#{best_deal.price}" + "\n" + "Short Description : "+"#{Rack::Utils.escape_html(best_deal.short_description)}",
		        	badge:             42
		        	#category:          "a category",         																	# optional; used for custom notification actions
		        	#sound:             "siren.aiff",         																	# optional
		        	#expiry:            Time.now + 60*60,     																	# optional; 0 is default, meaning the message is not stored
		        	#identifier:        1234,                 																	# optional; must be an integer
		        	#content_available: true                  																	# optional; any truthy value will set 'content-available' to 1
		      	)
		      	pusher.push(notification)
			end
		end
  	end
	def service_preference_params
		if params[:start_date].present? && params[:end_date].present?
			begin
				params[:start_date] = Date.strptime(params[:start_date].to_s.strip, "%m/%d/%Y").strftime("%d/%m/%Y")
				params[:end_date] = Date.strptime(params[:end_date].to_s.strip, "%m/%d/%Y").strftime("%d/%m/%Y")
			rescue
				if params[:from_site]
					params[:start_date]=params[:start_date].to_date
					params[:end_date]=params[:end_date].to_date
				else
					params[:start_date] = Date.strptime(params[:start_date].to_s.strip, "%d/%m/%Y").strftime("%d/%m/%Y")
					params[:end_date] = Date.strptime(params[:end_date].to_s.strip, "%d/%m/%Y").strftime("%d/%m/%Y")
				end

			end
		end
		params.permit(:app_user_id, :service_category_id, :service_provider_id, :service_category_name, :service_provider_name, :is_contract, :start_date, :end_date, :price, :plan_name)
	end
	def internet_service_preference_params
		params.permit(:service_preference_id, :upload_speed, :download_speed, :data, :email, :online_storage, :wifi_hotspot)
	end
	def cable_service_preference_params
		params.permit(:service_preference_id, :free_channels, :premium_channels)
	end
	def telephone_service_preference_params
    params.permit(:service_preference_id, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited)
  end
  def cellphone_service_preference_params
    params.permit(:service_preference_id, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited, :data_plan, :data_speed,:no_of_lines,:cellphone_detail_id)
  end
  def bundle_service_preference_params
    params.permit(:service_preference_id, :upload_speed, :download_speed, :data, :free_channels, :premium_channels, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited, :data_plan, :data_speed, :bundle_combo)
  end

end
