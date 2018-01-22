class ApplicationController < ActionController::Base

	# Prevent CSRF attacks by raising an exception.
	# For APIs, you may want to use :null_session instead.
	protect_from_forgery with: :null_session

	helper_method :calculate_offer_price,:display_logo_permission,:decode_api_data,:get_providers_by_category, :get_zipcodes, :get_deal_rating, :get_category_name_by_category_id, :display_deal_name_permission

	before_filter do
		resource = controller_name.singularize.to_sym
		method = "#{resource}_params"
		params[resource] &&= send(method) if respond_to?(method, true)
		:cors_set_access_control_headers
	end

	def cors_set_access_control_headers
		headers['Access-Control-Allow-Origin'] = '*'
		headers['Access-Control-Allow-Methods'] = 'POST, PUT, DELETE, GET, OPTIONS'
		headers['Access-Control-Request-Method'] = '*'
		headers['Access-Control-Allow-Headers'] = 'Origin, X-Requested-With, Content-Type, Accept, Authorization'
	end
	def verify_token
		puts "params="+params.to_yaml
		if params[:device_id].present? and params[:token].present?
			saved_token=DeviceRegister.find_by_device_id(params[:device_id]).token
			mobile_token=params[:token]
			if mobile_token!=params[:token]
				render :json=>{
							 message:"invalid token"
				}
			end
		else
			render :json=>{
				status:"token not issued"
			}
		end
	end
	def decode_api_data(data)
		if data.present?
			return Base64.decode64(data)
		else
			''
		end
	end

	def encode_api_data(data)
		return Base64.encode64(data)
	end

	def calculate_offer_price(price_monthly,contract_period)
		offer_price = ( "%.2f" %  (price_monthly * contract_period)).to_s 
	end




	def display_logo_permission(provider_id,deal_type)
		if Socket.gethostname=="servicedlz-Virtual-Machine"
			if ServiceDealConfig::where(config_key: "show_deals_logo").first.config_value == ServiceDealConfig::SHOW_DEAL_LOGO
				if ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				elsif ([7, 24, 48, 107,2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1479289775/Spectrum_1_mpwznq.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1478696145/cox_w01xck.png"
				else
					false
				end
			else
				if ([7, 24, 48, 107].include? provider_id) && (deal_type == "business")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487589/coming-soon-charter_vy9ubd.png"
				elsif ([7, 24, 48, 107,2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1479289775/Spectrum_1_mpwznq.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1478696145/cox_w01xck.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487586/coming-soon-cox_jjm06i.png"
				elsif ([41].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487582/coming-soon-dish_r3zui8.png"
				elsif ([109, 110, 111].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487579/coming-soon-mediacom_ncyf1z.png"
				elsif ([2, 5, 15, 43].include? provider_id) && (deal_type == "business")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487576/coming-soon-twc_hw5dal.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "residence")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1482487573/coming-soonv-at_t_lkijzw.png"
				elsif ([18, 39, 40, 50, 90].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487571/coming-soon-verizon_j67ep2.png"
				elsif ([17].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487564/coming-soon-vonage_lyi1wo.png"
				elsif ([29, 49, 54].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487562/coming-soon-xfinity_kx74ld.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
		   		url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				else
		  		url = "http://res.cloudinary.com/servicedealz/image/upload/v1478159445/coming-soon_global_csi91h.png"
				end
	    end
	  elsif Socket.gethostname=="ip-172-31-0-47"
	    if ServiceDealConfig::where(config_key: "show_deals_logo").first.config_value == ServiceDealConfig::SHOW_DEAL_LOGO
				if ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				elsif ([7, 24, 48, 107,2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1479289775/Spectrum_1_mpwznq.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1478696145/cox_w01xck.png"
				else
					false
				end
			else
				if ([7, 24, 48, 107].include? provider_id) && (deal_type == "business")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487589/coming-soon-charter_vy9ubd.png"
				elsif ([7, 24, 48, 107,2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1479289775/Spectrum_1_mpwznq.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1478696145/cox_w01xck.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487586/coming-soon-cox_jjm06i.png"
				elsif ([41].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487582/coming-soon-dish_r3zui8.png"
				elsif ([109, 110, 111].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487579/coming-soon-mediacom_ncyf1z.png"
				elsif ([2, 5, 15, 43].include? provider_id) && (deal_type == "business")
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487576/coming-soon-twc_hw5dal.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "residence")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1482487573/coming-soonv-at_t_lkijzw.png"
				elsif ([18, 39, 40, 50, 90].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487571/coming-soon-verizon_j67ep2.png"
				elsif ([17].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487564/coming-soon-vonage_lyi1wo.png"
				elsif ([29, 49, 54].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487562/coming-soon-xfinity_kx74ld.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
		   		url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				else
		  		url = "http://res.cloudinary.com/servicedealz/image/upload/v1478159445/coming-soon_global_csi91h.png"
				end
	    end
		else
			if ServiceDealConfig::where(config_key: "show_deals_logo").first.config_value == ServiceDealConfig::SHOW_DEAL_LOGO
				if ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				else
					false
				end
			else
				if ([7, 24, 48, 107].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487589/coming-soon-charter_vy9ubd.png"
				elsif ([10, 47, 61, 112, 113].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487586/coming-soon-cox_jjm06i.png"
				elsif ([41].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487582/coming-soon-dish_r3zui8.png"
				elsif ([109, 110, 111].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487579/coming-soon-mediacom_ncyf1z.png"
				elsif ([2, 5, 15, 43].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487576/coming-soon-twc_hw5dal.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "residence")
					url ="http://res.cloudinary.com/servicedealz/image/upload/v1482487573/coming-soonv-at_t_lkijzw.png"
				elsif ([18, 39, 40, 50, 90].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487571/coming-soon-verizon_j67ep2.png"
				elsif ([17].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487564/coming-soon-vonage_lyi1wo.png"
				elsif ([29, 49, 54].include? provider_id)
					url = "http://res.cloudinary.com/servicedealz/image/upload/v1482487562/coming-soon-xfinity_kx74ld.png"
				elsif ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
		   		url ="http://res.cloudinary.com/servicedealz/image/upload/v1479289687/at_t_yltr81.png"
				else
		  		url = "http://res.cloudinary.com/servicedealz/image/upload/v1478159445/coming-soon_global_csi91h.png"
				end
			end
		end
	end

	def display_deal_name_permission(provider_id,deal_type,title)
		if ServiceDealConfig::where(config_key: "show_deals_name").first.config_value== ServiceDealConfig::SHOW_DEAL_NAME
				name = title
		elsif Socket.gethostname=="ip-172-31-0-47"
			if ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
				name = title
			elsif  ([7, 24, 48, 107,2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
				name = title
			elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
				name = title
			else 
				name = ''
			end
	   
		else 
			if ([1,3,6,12,30].include? provider_id) && (deal_type == "business")
				name = title
			# elsif ([2, 5, 15, 43].include? provider_id) && (deal_type == "residence")
			# 	name = title
			# 	[2, 5, 15, 43]
			# 	[7, 24, 48, 107]
			# 	[10, 47, 61, 112, 113]
			# elsif ([7, 24, 48, 107].include? provider_id) && (deal_type == "residence")
			# 	name = title
			# elsif ([10, 47, 61, 112, 113].include? provider_id) && (deal_type == "residence")
			# 	name = title
			else 
				name = ''
			end
		end
	end

	def get_providers_by_category(category_name)
		 	service_category = ServiceCategory.where("name LIKE '%#{category_name}%'").first
			providers = ServiceProvider.where(:service_category_id => service_category.id).pluck(:name, :id)
			providers
	end

	def get_zipcodes
		zipcodes = Rails.cache.fetch(:expire_in => 24.hours) do
			Zipcode.all.map { |r| [r.code+' - '+r.city, r.id] }
		end
		return zipcodes
	end
	

end
