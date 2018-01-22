module DashboardsHelper

	def get_dashboard_deals(app_user_id,zip_code,deal_type,options ={} )
		if app_user_id.present?
			app_user = AppUser.find_by_id(app_user_id)
			zip_code = decode_api_data(app_user.zip)
			if app_user.present? && zip_code.present?
				deal_type=app_user.user_type
				excluded_categories="'Gas','Electricity','Home Security'"
				service_preferences = app_user.service_preferences.order("created_at DESC")
				servicelist = service_preferences.map do |sp|
					if !((sp.service_category.id == 4) && (deal_type == "residence"))
						app_user_current_plan = sp.price
						if sp.service_category.name == 'Internet'
							excluded_categories+=",'Internet'"
						elsif sp.service_category.name == 'Telephone'
							excluded_categories+=",'Telephone'"
						elsif sp.service_category.name == 'Cable'
							excluded_categories+=",'Cable'"
						elsif sp.service_category.name == 'Cellphone'
							excluded_categories+=",'Cellphone'"
						elsif sp.service_category.name == 'Bundle'
							excluded_categories+=",'Bundle'"
						end
						advertisement = sp.service_category.advertisements.order("created_at DESC").first
						if advertisement.blank?
							advertisement=nil
						end
						allowed_trending_deal = category_trending_deal(deal_type,sp.service_category_id,zip_code)
						allowed_order_deal=category_order_deal(app_user_id,sp.service_category_id,false)
						allowed_best_deal=category_best_deal(deal_type,sp,zip_code,1,false,options)
						if allowed_best_deal.present?
							if allowed_best_deal.effective_price.to_f>0
								you_save = 12*(app_user_current_plan - allowed_best_deal.effective_price.to_i)
							else
								you_save = 12*(app_user_current_plan - allowed_best_deal.price)
							end
						else
							you_save = ""
						end
						if allowed_best_deal.present? && allowed_order_deal.present? && allowed_best_deal.id==allowed_order_deal.id
							allowed_order_deal=allowed_order_deal
						end
						if allowed_trending_deal.present? && allowed_order_deal.present? && allowed_trending_deal.id==allowed_order_deal.id
							allowed_order_deal=allowed_order_deal
						end
					end
				end
				service_preferences = app_user.service_preferences.order("created_at DESC")
				servicelist = service_preferences.map do |sp|
					if !((sp.service_category.id == 4) && (deal_type == "residence"))
					app_user_current_plan = sp.price
						if sp.service_category.name == 'Internet'
							excluded_categories+=",'Internet'"
						elsif sp.service_category.name == 'Telephone'
							excluded_categories+=",'Telephone'"
						elsif sp.service_category.name == 'Cable'
							excluded_categories+=",'Cable'"
						elsif sp.service_category.name == 'Cellphone'
							excluded_categories+=",'Cellphone'"
						elsif sp.service_category.name == 'Bundle'
							excluded_categories+=",'Bundle'"
						end
						advertisement = sp.service_category.advertisements.order("created_at DESC").first
						if advertisement.blank?
							advertisement=nil
						end
						allowed_trending_deal = category_trending_deal(deal_type,sp.service_category_id,zip_code)
						allowed_order_deal=category_order_deal(app_user_id,sp.service_category_id,false)
						allowed_best_deal=category_best_deal(deal_type,sp,zip_code,1,false)
						if allowed_best_deal.present?
							if allowed_best_deal.effective_price.to_f>0
								you_save =  12*(app_user_current_plan - allowed_best_deal.effective_price.to_f)
								# puts ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
								# puts "yousave value=#{you_save}"
								if you_save!=0
									yousaveprecision=you_save.round(1).to_s.split(".")[1].to_i
									if yousaveprecision > 5
										you_save=you_save.ceil.to_i
									elsif yousaveprecision < 5
										you_save=you_save.floor.to_i
									elsif yousaveprecision == 5
										you_save=you_save.floor.to_i
									end
								else
									you_save=0
								end
							else
								you_save = 12*(app_user_current_plan - allowed_best_deal.price)
								yousaveprecision=you_save.round(1).to_s.split(".")[1].to_i
								if yousaveprecision > 5
									you_save=you_save.ceil.to_i
								elsif yousaveprecision < 5
									you_save=you_save.floor.to_i
								elsif yousaveprecision == 5
									you_save=you_save.floor.to_i
								end
							end
						else
							you_save = ""
						end
						best_deal_flag = true

						if allowed_best_deal.present? && allowed_order_deal.present? && allowed_best_deal.id==allowed_order_deal.id
							allowed_order_deal=allowed_order_deal
						end
						if allowed_trending_deal.present? && allowed_order_deal.present? && allowed_trending_deal.id==allowed_order_deal.id
							allowed_order_deal=allowed_order_deal
						end
						provider_name = sp.service_provider.present? ? sp.service_provider.name : ''
						{:best_deal_flag => best_deal_flag,:you_save_text => you_save, :contract_fee => sp.price, :service_provider_name =>  provider_name, :service_category_id => sp.service_category.id, :service_category_name => sp.service_category.name, :advertisement => advertisement.as_json(:except => [:created_at, :updated_at, :image], :methods => [:advertisement_image_url]), :trending_deal => allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:deal_image_url, :average_rating, :rating_count, :deal_price]), :best_deal => allowed_best_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:deal_image_url, :average_rating, :rating_count, :deal_price]),:order_deal => allowed_order_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:order_status,:deal_image_url, :average_rating, :rating_count, :deal_price])}
					end
				end
				# Show trending deals for unsubscribed services
				service_categories = ServiceCategory.where("name not in ("+excluded_categories+")")
				categoryList = service_categories.map do |sc|
					allowed_trending_deal = category_trending_deal(deal_type,sc.id,zip_code)
					allowed_order_deal=category_order_deal(app_user_id,sc.id,false)
					if !((sc.id == 4) && (deal_type == "residence"))
						if allowed_trending_deal.present?
							service_provider_name=allowed_trending_deal.service_provider_name
						else
							service_provider_name=""
						end
						{:best_deal_flag => false,:you_save_text => "", :contract_fee => "", :service_provider_name => service_provider_name, :service_category_id => sc.id, :service_category_name => sc.name,:advertisement =>nil,:trending_deal => allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:deal_image_url, :average_rating, :rating_count, :deal_price]),:best_deal =>nil,:order_deal => allowed_order_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:order_status,:deal_image_url, :average_rating, :rating_count, :deal_price])}
					end
				end
				return (servicelist + categoryList)
			else
				return false
			end
			else
				service_categories = ServiceCategory.where("name in ('Internet','Telephone','Cellphone','Cable','Bundle')")
				categoryList = service_categories.map do |sc|
if !((sc.id == 4) && (deal_type == "residence"))
				allowed_trending_deal = category_trending_deal(deal_type,sc.id,zip_code)
				allowed_order_deal=category_order_deal(app_user_id,sc.id,false)

				if allowed_trending_deal.present?
					service_provider_name=allowed_trending_deal.service_provider_name
				else
					service_provider_name=""
				end

				{:you_save_text => "", :contract_fee => "", :service_provider_name => service_provider_name, :service_category_id => sc.id, :service_category_name => sc.name,:trending_deal => allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:deal_image_url, :average_rating, :rating_count, :deal_price]),:order_deal => allowed_order_deal.as_json(:except => [:created_at, :updated_at, :price, :image], :methods => [:order_status,:deal_image_url, :average_rating, :rating_count, :deal_price]) }
			end
		end
			return categoryList
		end
	end
	def category_order_deal(app_user_id,category_id,return_attributes)

		order_deals=Deal.joins("inner join order_items on order_items.deal_id = deals.id inner join orders on orders.id = order_items.order_id").select("deals.id").where("deals.service_category_id = ? AND orders.app_user_id = ?",category_id,app_user_id)

		if return_attributes==true
			select_fields_internet="deals.*,internet_deal_attributes.download as download_speed,internet_deal_attributes.upload as upload_speed"
			select_fields_telephone="deals.*,telephone_deal_attributes.domestic_call_minutes,telephone_deal_attributes.international_call_minutes,telephone_deal_attributes.countries,telephone_deal_attributes.features"
			select_fields_cable="deals.*,cable_deal_attributes.free_channels,cable_deal_attributes.premium_channels,cable_deal_attributes.free_channels_list,cable_deal_attributes.premium_channels_list"
			select_fields_cellphone="deals.*,cellphone_deal_attributes.no_of_lines,cellphone_deal_attributes.price_per_line,cellphone_deal_attributes.domestic_call_minutes,cellphone_deal_attributes.international_call_minutes,cellphone_deal_attributes.domestic_text,cellphone_deal_attributes.international_text,cellphone_deal_attributes.data_plan,cellphone_deal_attributes.additional_data,cellphone_deal_attributes.rollover_data"
			select_fields_bundle="deals.*,bundle_deal_attributes.free_channels,bundle_deal_attributes.premium_channels,bundle_deal_attributes.free_channels_list,bundle_deal_attributes.premium_channels_list,bundle_deal_attributes.domestic_call_minutes,bundle_deal_attributes.international_call_minutes,bundle_deal_attributes.download as download_speed,bundle_deal_attributes.upload as upload_speed"
		else
			select_fields_internet="deals.*,order_items.order_id"
			select_fields_telephone="deals.*,order_items.order_id"
			select_fields_cable="deals.*,order_items.order_id"
			select_fields_cellphone="deals.*,order_items.order_id"
			select_fields_bundle="deals.*,order_items.order_id"
		end
		# app_user = AppUser.find(app_user_id)
		# app_user.service_preferences
		# last_order_deal_id = app_user.orders.last.deal_id rescue nil
		# .order("deals.price ASC").where(id: last_order_deal_id).first
		category_name = ServiceCategory.get_category_name_by_id(category_id)
		select_data = eval("select_fields_#{category_name}")
		if category_name.present?
			order_deal = Deal.joins("#{category_name}_deal_attributes".to_sym).joins(:order_items).select(select_data).where("deals.id in (?)",order_deals).to_a.last
		end

		if order_deal.present?
			return order_deal
		else
			return nil
		end
	end

	def filtered_deals(app_user_id,category_id,zip_code,deal_type,sorting_key)
		if app_user_id.present?
			app_user = AppUser.find_by_id(app_user_id)
			zip_code = app_user.zip
			deal_type=app_user.user_type
		end

		category_name = ServiceCategory.get_category_name_by_id(category_id)
		restricted_deals=Deal.joins(:deals_zipcodes).joins(:zipcodes).select("deals.id").where("zipcodes.code= ? ",zip_code)
		deal_validation_conditions="deals.is_active=true AND deals.deal_type='"+deal_type+"' AND deals.service_category_id="+category_id+" "

		select_fields_internet="deals.*,internet_deal_attributes.download as download_speed,internet_deal_attributes.upload as upload_speed"
		select_fields_telephone="deals.*,telephone_deal_attributes.domestic_call_minutes,telephone_deal_attributes.international_call_minutes,telephone_deal_attributes.countries,telephone_deal_attributes.features"
		select_fields_cable="deals.*,cable_deal_attributes.free_channels,cable_deal_attributes.premium_channels,cable_deal_attributes.free_channels_list,cable_deal_attributes.premium_channels_list"
		select_fields_cellphone="deals.*,cellphone_deal_attributes.effective_price,cellphone_deal_attributes.no_of_lines,cellphone_deal_attributes.price_per_line,cellphone_deal_attributes.domestic_call_minutes,cellphone_deal_attributes.international_call_minutes,cellphone_deal_attributes.domestic_text,cellphone_deal_attributes.international_text,cellphone_deal_attributes.data_plan,cellphone_deal_attributes.additional_data,cellphone_deal_attributes.rollover_data"
		select_fields_bundle="deals.*,bundle_deal_attributes.free_channels,bundle_deal_attributes.premium_channels,bundle_deal_attributes.free_channels_list,bundle_deal_attributes.premium_channels_list,bundle_deal_attributes.domestic_call_minutes,bundle_deal_attributes.international_call_minutes,bundle_deal_attributes.download as download_speed,bundle_deal_attributes.upload as upload_speed"

		select_data = eval("select_fields_#{category_name}")
		if sorting_key == 'download_speed' #For Internet and bundle
			if [ServiceCategory::INTERNET_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("#{category_name}_deal_attributes.download DESC,deals.price ASC").group('deals.id')
			end
		elsif sorting_key == 'price' #For all on the basis of Price
			if ServiceCategory::CATEGORIES.include?(category_name)
				deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("deals.price ASC").group('deals.id')
			else
				deals = Deal.where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("price ASC").group('deals.id')
			end
		elsif sorting_key == 'free_channels' #For Cable
			if [ServiceCategory::CABLE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("#{category_name}_deal_attributes.free_channels DESC,deals.price ASC").group('deals.id')
			end
		elsif sorting_key == 'call_minutes' #For CellPhone, Telephone & Bundle
			if [ServiceCategory::CELLPHONE_CATEGORY,ServiceCategory::TELEPHONE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("#{category_name}_deal_attributes.domestic_call_minutes DESC,deals.price ASC").group('deals.id')
			end
		else
			deals = Deal.where(deal_validation_conditions).order("price ASC").group('deals.id')
		end

		return deals
	end

	def category_trending_deal(deal_type,category_id,zip_code)

		restricted_deals=Deal.joins(:deals_zipcodes).joins(:zipcodes).select("deals.id").where("zipcodes.code= ? ",zip_code)
		# allowed_trending_deal=Deal.joins(:trending_deals).where("deals.id not in (?) AND deals.is_active = ? AND deals.deal_type = ? AND deals.service_category_id = ?",restricted_deals,true,deal_type,category_id).order("trending_deals.subscription_count DESC").first
		# if not allowed_trending_deal.present?
		# 	allowed_trending_deal=Deal.where("deals.id not in (?) AND deals.is_active = ? AND deals.deal_type = ? AND deals.service_category_id = ?",restricted_deals,true,deal_type,category_id).order("price ASC").first
		# end
		category_name= ServiceCategory.find(category_id).name.downcase
		allowed_trending_deal = Deal.joins("#{category_name}_deal_attributes".to_sym).where("deals.is_active = ? AND deals.deal_type = ?  AND deals.service_category_id = ? AND deals.is_best_value =? AND deals.id not in (?)",true,deal_type,category_id,true,restricted_deals).order("price ASC").first
		if not allowed_trending_deal.present?
			allowed_trending_deal=Deal.joins("#{category_name}_deal_attributes".to_sym).where("deals.is_active = ? AND deals.deal_type = ? AND deals.service_category_id = ? AND deals.id not in (?)",true,deal_type,category_id,restricted_deals).order("price ASC").first
		end


		return allowed_trending_deal
	end

	def category_best_deal(deal_type,sp,zip_code,return_count,return_attributes,options = {})
		# byebug
		# raise options.to_s
		restricted_deals=Deal.joins(:deals_zipcodes).joins(:zipcodes).select("deals.id").where("zipcodes.code= ? ",zip_code)
		filter_by_provider = options['provider_ids'].present? ? "deals.service_provider_id in (#{options['provider_ids']}) AND " : ''
		deal_validation_conditions=filter_by_provider+"deals.is_active=true AND deals.deal_type='"+deal_type+"' AND deals.service_category_id="+sp.service_category_id.to_s+" "
		sort_by = options['sort_by'].present? ? options['sort_by'] : 'price ASC'

		if return_attributes==true
			select_fields_internet="deals.*,internet_deal_attributes.download as download_speed,internet_deal_attributes.upload as upload_speed"
			select_fields_telephone="deals.*,telephone_deal_attributes.domestic_call_minutes,telephone_deal_attributes.international_call_minutes,telephone_deal_attributes.countries,telephone_deal_attributes.features"
			select_fields_cable="deals.*,cable_deal_attributes.free_channels,cable_deal_attributes.premium_channels,cable_deal_attributes.free_channels_list,cable_deal_attributes.premium_channels_list"
			select_fields_cellphone="deals.*,cellphone_deal_attributes.effective_price,cellphone_deal_attributes.no_of_lines,cellphone_deal_attributes.price_per_line,cellphone_deal_attributes.domestic_call_minutes,cellphone_deal_attributes.international_call_minutes,cellphone_deal_attributes.domestic_text,cellphone_deal_attributes.international_text,cellphone_deal_attributes.data_plan,cellphone_deal_attributes.additional_data,cellphone_deal_attributes.rollover_data"
			select_fields_bundle="deals.*,bundle_deal_attributes.free_channels,bundle_deal_attributes.premium_channels,bundle_deal_attributes.free_channels_list,bundle_deal_attributes.premium_channels_list,bundle_deal_attributes.domestic_call_minutes,bundle_deal_attributes.international_call_minutes,bundle_deal_attributes.download as download_speed,bundle_deal_attributes.upload as upload_speed"
		# else
			# select_fields_internet="deals.*,internet_deal_attributes.download as download_speed,internet_deal_attributes.upload as upload_speed"
			# select_fields_telephone="deals.*,telephone_deal_attributes.domestic_call_minutes,telephone_deal_attributes.international_call_minutes,telephone_deal_attributes.countries,telephone_deal_attributes.features"
			# select_fields_cable="deals.*,cable_deal_attributes.free_channels,cable_deal_attributes.premium_channels,cable_deal_attributes.free_channels_list,cable_deal_attributes.premium_channels_list"
			# select_fields_cellphone="deals.*,cellphone_deal_attributes.effective_price,cellphone_deal_attributes.no_of_lines,cellphone_deal_attributes.price_per_line,cellphone_deal_attributes.domestic_call_minutes,cellphone_deal_attributes.international_call_minutes,cellphone_deal_attributes.domestic_text,cellphone_deal_attributes.international_text,cellphone_deal_attributes.data_plan,cellphone_deal_attributes.additional_data,cellphone_deal_attributes.rollover_data"
			# select_fields_bundle="deals.*,bundle_deal_attributes.free_channels,bundle_deal_attributes.premium_channels,bundle_deal_attributes.free_channels_list,bundle_deal_attributes.premium_channels_list,bundle_deal_attributes.domestic_call_minutes,bundle_deal_attributes.international_call_minutes,bundle_deal_attributes.download as download_speed,bundle_deal_attributes.upload as upload_speed"
		else
			select_fields_internet="deals.*"
			select_fields_telephone="deals.*"
			select_fields_cable="deals.*"
			select_fields_cellphone="deals.*,cellphone_deal_attributes.effective_price"
			select_fields_bundle="deals.*"
		end
		if sp.service_category.name == 'Internet'
			app_user_download_speed = sp.internet_service_preference.download_speed
			best_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download > ? AND price < ? AND deals.id not in (?)", app_user_download_speed,sp.price,restricted_deals)
			if best_deals.blank?
				best_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download = ? AND price < ? AND deals.id not in (?)", app_user_download_speed,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download > ? AND price = ? AND deals.id not in (?)", app_user_download_speed,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download = ? AND price <= ? AND deals.id not in (?)", app_user_download_speed,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download < ? AND price <= ? AND deals.id not in (?)", app_user_download_speed,sp.price,restricted_deals).order("download DESC")
			end
			if best_deals.blank?
				best_deals=Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order(sort_by)
			end
		elsif sp.service_category.name == 'Telephone'
			if sp.telephone_service_preference.domestic_call_unlimited == true
				best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes='Unlimited' AND price < ? AND deals.id not in (?)", sp.price, restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes='Unlimited' AND price = ? AND deals.id not in (?)", sp.price, restricted_deals)
				end
				if best_deals.blank?
					best_deals=Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions + " AND telephone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?)",restricted_deals)
				end
			else
				app_user_call_minutes = sp.telephone_service_preference.domestic_call_minutes
				best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND telephone_deal_attributes.domestic_call_minutes > ? AND price < ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND telephone_deal_attributes.domestic_call_minutes = ? AND price < ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND telephone_deal_attributes.domestic_call_minutes > ? AND price = ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND telephone_deal_attributes.domestic_call_minutes = ? AND price <= ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND telephone_deal_attributes.domestic_call_minutes < ? AND price <= ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("domestic_call_minutes DESC").order(sort_by)
				end
				if best_deals.blank?
					best_deals=Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions + " AND telephone_deal_attributes.domestic_call_minutes!='Unlimited' AND deals.id not in (?)",restricted_deals).order(sort_by)
				end
			end
		elsif sp.service_category.name == 'Cable'
			# best_deals = CableServicePreference.cable_best_deal(sp,select_fields_cable,deal_validation_conditions,restricted_deals,options)
			# raise sort_by.to_s
			app_user_free_channels = sp.cable_service_preference.free_channels
			best_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels > ? AND price < ? AND deals.id not in (?)", app_user_free_channels,sp.price,restricted_deals)
			if best_deals.blank?
				best_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels = ? AND price < ? AND deals.id not in (?)", app_user_free_channels,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels > ? AND price = ? AND deals.id not in (?)", app_user_free_channels,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels = ? AND price <= ? AND deals.id not in (?)", app_user_free_channels,sp.price,restricted_deals)
			end
			if best_deals.blank?
				best_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels < ? AND price <= ? AND deals.id not in (?)", app_user_free_channels,sp.price,restricted_deals).order("free_channels DESC")
			end
			if best_deals.blank?
				best_deals=Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals)
			end
		elsif sp.service_category.name == 'Cellphone'
			best_deals = CellphoneServicePreference.cellphone_best_deal(sp,select_fields_cellphone,deal_validation_conditions,restricted_deals)
=begin
				if sp.cellphone_service_preference.domestic_call_unlimited == true
				best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.price < ? AND deals.id not in (?)", sp.price,restricted_deals).order("price ASC")
				if best_deals.blank?
					best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.price = ? AND deals.id not in (?)", sp.price,restricted_deals).order("price ASC")
				end
				if best_deals.blank?
					best_deals=Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions + " AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?)",restricted_deals).order("price ASC")
				end
			else
				app_user_call_minutes = sp.cellphone_service_preference.domestic_call_minutes
				best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes > ? AND price < ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("price ASC")
				if best_deals.blank?
					best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes = ? AND price < ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("price ASC")
				end
				if best_deals.blank?
					best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes > ? AND price = ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("price ASC")
				end
				if best_deals.blank?
					best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes = ? AND price <= ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("price ASC")
				end
				if best_deals.blank?
					best_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes < ? AND price <= ? AND deals.id not in (?)", app_user_call_minutes,sp.price,restricted_deals).order("domestic_call_minutes DESC").order("price ASC")
				end
				if best_deals.blank?
					best_deals=Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions + " AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND deals.id not in (?)",restricted_deals).order("price ASC")
				end
			end
=end	
		elsif sp.service_category.name == 'Bundle'

			app_user_bundle_combo = sp.bundle_service_preference.bundle_combo
			if app_user_bundle_combo=="Internet,Telephone and Cable"
				app_user_download_speed = sp.bundle_service_preference.download_speed
				app_user_call_minutes = sp.bundle_service_preference.domestic_call_minutes
				app_user_free_channels = sp.bundle_service_preference.free_channels
				best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND bundle_deal_attributes.free_channels = ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND bundle_deal_attributes.free_channels = ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price <= ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,sp.price+10,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND deals.id not in (?)", app_user_bundle_combo,restricted_deals).order("price ASC")
				end
			elsif app_user_bundle_combo=="Internet and Telephone"
				app_user_download_speed = sp.bundle_service_preference.download_speed
				app_user_call_minutes = sp.bundle_service_preference.domestic_call_minutes
				best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,sp.price,restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND price <= ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,sp.price+10,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND deals.id not in (?)", app_user_bundle_combo,restricted_deals)
				end
			elsif app_user_bundle_combo=="Internet and Cable"
				app_user_download_speed = sp.bundle_service_preference.download_speed
				app_user_free_channels = sp.bundle_service_preference.free_channels
				best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.free_channels > ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,sp.price,restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.free_channels = ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.free_channels > ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download = ? AND bundle_deal_attributes.free_channels = ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.free_channels > ? AND price <= ? AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,sp.price+10,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND deals.id not in (?)", app_user_bundle_combo,restricted_deals).order(sort_by)
				end
			elsif app_user_bundle_combo=="Telephone and Cable"
				app_user_call_minutes = sp.bundle_service_preference.domestic_call_minutes
				app_user_free_channels = sp.bundle_service_preference.free_channels
				best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND bundle_deal_attributes.free_channels = ? AND price < ? AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes = ? AND bundle_deal_attributes.free_channels = ? AND price = ? AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,sp.price,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND price <= ? AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,sp.price+10,restricted_deals)
				end
				if best_deals.blank?
					best_deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND deals.id not in (?)", app_user_bundle_combo,restricted_deals).order(sort_by)
				end
			end
		else
			best_deals = Deal.where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals)
		end

		if best_deals.present?
			if return_count==1
				best_deals = best_deals.order("price ASC")
				return best_deals.first
			else
				return best_deals.order(sort_by)
			end
		else
			best_deals = nil
		end
	end

	def get_category_deals(app_user_id,category_id,zip_code,deal_type,options = {})
		category_id = category_id.present? ? category_id.to_i : 1
		select_fields_internet="deals.*,internet_deal_attributes.download as download_speed,internet_deal_attributes.upload as upload_speed"
		select_fields_telephone="deals.*,telephone_deal_attributes.domestic_call_minutes,telephone_deal_attributes.international_call_minutes,telephone_deal_attributes.countries,telephone_deal_attributes.features"
		select_fields_cable="deals.*,cable_deal_attributes.free_channels,cable_deal_attributes.premium_channels,cable_deal_attributes.free_channels_list,cable_deal_attributes.premium_channels_list"
		select_fields_cellphone="deals.*,cellphone_deal_attributes.effective_price,cellphone_deal_attributes.no_of_lines,cellphone_deal_attributes.price_per_line,cellphone_deal_attributes.domestic_call_minutes,cellphone_deal_attributes.international_call_minutes,cellphone_deal_attributes.domestic_text,cellphone_deal_attributes.international_text,cellphone_deal_attributes.data_plan,cellphone_deal_attributes.additional_data,cellphone_deal_attributes.rollover_data"
		select_fields_bundle="deals.*,bundle_deal_attributes.free_channels,bundle_deal_attributes.premium_channels,bundle_deal_attributes.free_channels_list,bundle_deal_attributes.premium_channels_list,bundle_deal_attributes.domestic_call_minutes,bundle_deal_attributes.international_call_minutes,bundle_deal_attributes.download as download_speed,bundle_deal_attributes.upload as upload_speed"
		category_name = ServiceCategory.find_by_id(category_id).name.downcase
		select_data = eval("select_fields_#{category_name}")
		sort_by = options['sort_by'].present? ? options['sort_by'] : 'price ASC'
		filter_by_provider = options['provider_ids'].present? ? "deals.service_provider_id in (#{options['provider_ids']}) AND " : ''
		if app_user_id.present?

			app_user = AppUser.find_by_id(app_user_id)
			zip_code =  decode_api_data(app_user.zip)
			deal_type=app_user.user_type
			restricted_deals=Deal.joins(:deals_zipcodes).joins(:zipcodes).select("deals.id").where("zipcodes.code= ? ",zip_code)
			deal_validation_conditions="#{filter_by_provider} deals.is_active=true AND deals.deal_type='"+deal_type+"' AND deals.service_category_id=#{category_id}"+" "
			user_preference = app_user.service_preferences.where("service_category_id = ?",category_id).first
			matched_deal = []
			if category_id == Deal::INTERNET_CATEGORY
				allowed_trending_deal = category_trending_deal(deal_type,Deal::INTERNET_CATEGORY,zip_code)
				if user_preference.present?
					current_download_speed = user_preference.internet_service_preference.download_speed
					allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true)
					best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,{"sort_by"=>"price ASC"}) 
					greater_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download > ? AND deals.id not in (?) AND deals.id not in (?)", current_download_speed,restricted_deals,best_deals.ids)
					smaller_deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+" AND internet_deal_attributes.download < ? AND deals.id not in (?) AND deals.id not in (?)", current_download_speed,restricted_deals,best_deals.ids)
				else
					if sort_by =='download_speed'
						deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+ " AND deals.id not in (?)",restricted_deals).order("#{category_name}_deal_attributes.download DESC,deals.price ASC").group('deals.id')
					elsif sort_by =='price' || sort_by == 'price DESC'
						deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+ " AND deals.id not in (?)",restricted_deals).order("deals.effective_price DESC").group('deals.id')
					else
						deals = Deal.joins(:internet_deal_attributes).select(select_fields_internet).where(deal_validation_conditions+ " AND deals.id not in (?)",restricted_deals).order("deals.effective_price ASC").group('deals.id')
					end
					if deals.present?
						json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
					end
					if json.present? && allowed_trending_deal.present?
						trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
						matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
					end
				end
	
				elsif category_id == Deal::TELEPHONE_CATEGORY
					allowed_trending_deal = category_trending_deal(deal_type,Deal::TELEPHONE_CATEGORY,zip_code)
					if user_preference.present?
						current_plan_price = user_preference.price
						current_t_plan = user_preference.telephone_service_preference.domestic_call_unlimited
						if current_t_plan == true
							allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true)
							best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,{"sort_by"=>"price ASC"})
							greater_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND deals.price > ? AND telephone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?) AND deals.id not in (?)",current_plan_price,restricted_deals,best_deals.ids)
							smaller_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND deals.price < ? AND telephone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?) AND deals.id not in (?)",current_plan_price,restricted_deals,best_deals.ids)
						else
							current_call_minutes = user_preference.telephone_service_preference.domestic_call_minutes
							best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true)
							if best_deals.present?
								greater_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes > ? AND deals.id not in (?) AND deals.id not in (?)", current_call_minutes,restricted_deals,best_deals.ids)
								smaller_deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions+" AND telephone_deal_attributes.domestic_call_minutes < ? AND deals.id not in (?) AND deals.id not in (?)", current_call_minutes,restricted_deals,best_deals.ids)
							end
						end
					else
						if sort_by == 'call_minutes' 
							deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("telephone_deal_attributes.domestic_call_minutes DESC,deals.price ASC").group('deals.id')
						elsif sort_by =='price' || sort_by == 'price DESC'
							deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("deals.effective_price DESC").group('deals.id')
						else
							deals = Deal.joins(:telephone_deal_attributes).select(select_fields_telephone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("deals.effective_price ASC").group('deals.id')
						end
						if deals.present?
							json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
						end
						if json.present? && allowed_trending_deal.present?
							trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
							matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
					end
				end

				elsif category_id == Deal::CABLE_CATEGORY
					allowed_trending_deal = category_trending_deal(deal_type,Deal::CABLE_CATEGORY,zip_code)
					if user_preference.present?
						current_plan_price = user_preference.price
						current_free_channels = user_preference.cable_service_preference.free_channels
						allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true)
						best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,{"sort_by"=>"price ASC"})
						if best_deals.present?
							greater_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels > ? AND deals.id not in (?) AND deals.id not in (?)", current_free_channels,restricted_deals,best_deals.ids)
							smaller_deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions+" AND cable_deal_attributes.free_channels < ? AND deals.id not in (?) AND deals.id not in (?)", current_free_channels,restricted_deals,best_deals.ids)
						end
					else
						if sort_by == 'free_channels'
							deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("#{category_name}_deal_attributes.free_channels DESC,deals.price ASC").group('deals.id')
						elsif sort_by =='price' || sort_by == 'price DESC'
							deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("deals.effective_price DESC").group('deals.id')
						else
							deals = Deal.joins(:cable_deal_attributes).select(select_fields_cable).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("deals.effective_price ASC").group('deals.id')
						end
						if deals.present?
							json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
						end

					if json.present? && allowed_trending_deal.present?
						trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
						matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
					end
				end



				elsif category_id == Deal::CELLPHONE_CATEGORY

					allowed_trending_deal = category_trending_deal(deal_type,Deal::CELLPHONE_CATEGORY,zip_code)
					if user_preference.present?
						current_plan_price = user_preference.price
						current_t_plan = user_preference.cellphone_service_preference.domestic_call_unlimited
						if current_t_plan == true
							allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true)
							best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,{"sort_by"=>"price ASC"})
							if best_deals.present?
								greater_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND deals.price > ? AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?) AND deals.id not in (?)", current_plan_price,restricted_deals,best_deals.ids)
								smaller_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND deals.price < ? AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND deals.id not in (?) AND deals.id not in (?)", current_plan_price,restricted_deals,best_deals.ids)
							end
						else
							current_call_minutes = user_preference.cellphone_service_preference.domestic_call_minutes
							best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,options)
							allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true,{"sort_by"=>"price ASC"})
							if best_deals.present?
								greater_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes > ? AND deals.id not in (?) AND deals.id not in (?)", current_call_minutes,restricted_deals,best_deals.ids)
								smaller_deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes < ? AND deals.id not in (?) AND deals.id not in (?)", current_call_minutes,restricted_deals,best_deals.ids)
							end
						end
					else
						if sort_by == 'call_minutes' 
							deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("cellphone_deal_attributes.domestic_call_minutes DESC,deals.price ASC")
						elsif sort_by == 'price' || sort_by == 'price DESC'
							deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("cellphone_deal_attributes.effective_price DESC")
						else
							deals = Deal.joins(:cellphone_deal_attributes).select(select_fields_cellphone).where(deal_validation_conditions + " AND deals.id not in (?)", restricted_deals).order("cellphone_deal_attributes.effective_price ASC")
						end

					if deals.present?
						json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
					end

					if json.present? && allowed_trending_deal.present?
						trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
				 	matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
					
					end
				end

			

				elsif category_id == Deal::BUNDLE_CATEGORY
				allowed_trending_deal = category_trending_deal(deal_type,Deal::BUNDLE_CATEGORY,zip_code)
				if user_preference.present?
					app_user_bundle_combo = user_preference.bundle_service_preference.bundle_combo
					allowed_best_deal=category_best_deal(deal_type,user_preference,zip_code,1,true)
					best_deals = category_best_deal(deal_type,user_preference,zip_code,nil,true,{"sort_by"=>"price ASC"})
					# total_deals =Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals)
					if best_deals.present?
						if app_user_bundle_combo=="Internet,Telephone and Cable"
							app_user_download_speed = user_preference.bundle_service_preference.download_speed
							app_user_call_minutes = user_preference.bundle_service_preference.domestic_call_minutes
							app_user_free_channels = user_preference.bundle_service_preference.free_channels
							greater_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,restricted_deals,best_deals.ids)
							smaller_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download < ? AND bundle_deal_attributes.domestic_call_minutes < ? AND bundle_deal_attributes.free_channels < ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,app_user_free_channels,restricted_deals,best_deals.ids)
						elsif app_user_bundle_combo=="Internet and Telephone"
							app_user_download_speed = user_preference.bundle_service_preference.download_speed
							app_user_call_minutes = user_preference.bundle_service_preference.domestic_call_minutes
							greater_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.domestic_call_minutes > ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,restricted_deals,best_deals.ids)
							smaller_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download < ? AND bundle_deal_attributes.domestic_call_minutes < ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_call_minutes,restricted_deals,best_deals.ids)
						elsif app_user_bundle_combo=="Internet and Cable"
							app_user_download_speed = user_preference.bundle_service_preference.download_speed
							app_user_free_channels = user_preference.bundle_service_preference.free_channels
							# raise user_preference.bundle_service_preference.to_yaml
							greater_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download > ? AND bundle_deal_attributes.free_channels > ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,restricted_deals,best_deals.ids)
							smaller_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.download < ? AND bundle_deal_attributes.free_channels < ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_download_speed,app_user_free_channels,restricted_deals,best_deals.ids)
						elsif app_user_bundle_combo=="Telephone and Cable"
							app_user_call_minutes = user_preference.bundle_service_preference.domestic_call_minutes
							app_user_free_channels = user_preference.bundle_service_preference.free_channels
							greater_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes > ? AND bundle_deal_attributes.free_channels > ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,restricted_deals,best_deals.ids)
							greater_deals = Deal.joins(:bundle_deal_attributes).where(deal_validation_conditions+" AND bundle_deal_attributes.bundle_combo = ? AND bundle_deal_attributes.domestic_call_minutes < ? AND bundle_deal_attributes.free_channels < ? AND deals.id not in (?) AND deals.id not in (?)", app_user_bundle_combo,app_user_call_minutes,app_user_free_channels,restricted_deals,best_deals.ids)
						end
					else
						trending_deal = allowed_trending_deal.id
						# a= Deal.where(id: a)
						deals =Deal.where(id: trending_deal).joins("bundle_deal_attributes".to_sym).select(select_data)
						json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
						matched_deal = ( json.each {|h|  h[:is_deal]="trending"}  )
					end
				else
					if sort_by =='download_speed'
						deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("bundle_deal_attributes.download DESC,deals.price ASC").group('deals.id')
					elsif sort_by == 'price' || sort_by == 'price DESC'
						deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("deals.effective_price DESC").group('deals.id')
					elsif sort_by == 'free_channels'
						deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("bundle_deal_attributes.free_channels DESC,deals.price ASC").group('deals.id')
					elsif sort_by == 'call_minutes' 
						deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("bundle_deal_attributes.domestic_call_minutes DESC,deals.price ASC").group('deals.id')
					else
						deals = Deal.joins(:bundle_deal_attributes).select(select_fields_bundle).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("deals.effective_price ASC").group('deals.id')
					end

					if deals.present?
						json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
					end

					if json.present? && allowed_trending_deal.present?
						trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
					matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
					
					end
				end
			end

			if best_deals.present? || greater_deals.present? || smaller_deals.present? || allowed_trending_deal.present?
				best_deals = best_deals.present? ? best_deals : []
				greater_deals = greater_deals.present? ? greater_deals : []
				smaller_deals = smaller_deals.present? ? smaller_deals : []
				allowed_trending_deal = allowed_trending_deal.present? ? allowed_trending_deal : []
				combined_deals = (greater_deals + best_deals + smaller_deals + [allowed_trending_deal])
				deal_ids = combined_deals.map{ |deal| deal.id}
	
		if sort_by =='download_speed'
			if [ServiceCategory::INTERNET_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("#{category_name}_deal_attributes.download DESC,deals.price ASC").group('deals.id')
			end
		elsif sort_by == 'price' || sort_by == 'price DESC'
			if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
					merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("cellphone_deal_attributes.effective_price ASC")
				else 
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("deals.effective_price DESC").group('deals.id')
			# if ServiceCategory::CATEGORIES.include?(category_name)
			# 	merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("deals.effective_price DESC").group('deals.id')
			end
		elsif sort_by == 'free_channels'
			if  [ServiceCategory::CABLE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("#{category_name}_deal_attributes.free_channels DESC,deals.price ASC").group('deals.id')
			end
		elsif sort_by == 'call_minutes' 
			if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("#{category_name}_deal_attributes.domestic_call_minutes DESC,deals.price ASC")
			elsif [ServiceCategory::TELEPHONE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("#{category_name}_deal_attributes.domestic_call_minutes DESC,deals.price ASC").group('deals.id')
			end
		else
			if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("deals.effective_price ASC")
			else
				merged_deals = Deal.where(id: deal_ids).joins("#{category_name}_deal_attributes".to_sym).select(select_data).order("deals.effective_price ASC").group('deals.id')
			end
		end
			if merged_deals.present?
				json_1 = merged_deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
			end
		
			if allowed_best_deal.present?
				best_deal = allowed_best_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
				json_3 = [best_deal]
			end
		
			if allowed_trending_deal.present?
				trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
				json_4 = [trending_deal]
			end

			
			if (!(category_id == Deal::CELLPHONE_CATEGORY) && (user_preference.present?))
				if json_1.present? && json_3.present? && json_4.present? 
						matched_deal =json_3.each {|h| h[:is_deal]="best"} + ( json_1.reject { |h| [best_deal['id']  ].include? h['id'] }.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
				elsif json_1.present? && json_3.blank? && json_4.present? 
						matched_deal = ( json_1.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
				else 
					matched_deal = []
				end
			elsif (category_id == Deal::CELLPHONE_CATEGORY) && (user_preference.present?)	
				if json_1.present? && json_3.present? && json_4.present? 
				 matched_deal = json_3.each {|h| h[:is_deal]="best"} + json_1.delete_if { |h| h["id"] == best_deal['id'] and h["effective_price"] == best_deal['effective_price']}.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"} 
				 elsif json_1.present? && json_3.blank? && json_4.present? 
				 matched_deal = json_1.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"} 
				else
					matched_deal = []
				end
		
		
			end
			end

		else
			restricted_deals=Deal.joins(:deals_zipcodes).joins(:zipcodes).select("deals.id").where("zipcodes.code= ? ",zip_code)
			deal_validation_conditions="#{filter_by_provider} deals.is_active=true AND deals.deal_type='"+deal_type+"' AND deals.service_category_id=#{category_id}"+" "

		
			allowed_trending_deal = category_trending_deal(deal_type,category_id,zip_code)
			if sort_by =='download_speed'
				if [ServiceCategory::INTERNET_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("#{category_name}_deal_attributes.download DESC,deals.price ASC").group('deals.id')
				end
			elsif sort_by == 'price' || sort_by == 'price DESC'
					if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("cellphone_deal_attributes.effective_price DESC")
				else 
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("deals.effective_price DESC").group('deals.id')
				end
			elsif sort_by == 'free_channels'
				if  [ServiceCategory::CABLE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("#{category_name}_deal_attributes.free_channels DESC,deals.price ASC").group('deals.id')
				end
			elsif sort_by == 'call_minutes' 
				if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("#{category_name}_deal_attributes.domestic_call_minutes DESC,deals.price ASC")
				elsif [ServiceCategory::TELEPHONE_CATEGORY,ServiceCategory::BUNDLE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("#{category_name}_deal_attributes.domestic_call_minutes DESC,deals.price ASC").group('deals.id')
				end
			else
				if [ServiceCategory::CELLPHONE_CATEGORY].include?(category_name)
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("cellphone_deal_attributes.effective_price ASC")
				else 
					deals = Deal.joins("#{category_name}_deal_attributes".to_sym).select(select_data).where(deal_validation_conditions + "AND deals.id not in (?)", restricted_deals).order("deals.effective_price ASC").group('deals.id')
				end
			end

			if deals.present?
				
				json = deals.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
			end

			if json.present? && allowed_trending_deal.present?
						trending_deal = allowed_trending_deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price, :service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])
					
					matched_deal = ( json.each {|h|   h['id']!=trending_deal['id'] ?  h[:is_deal]="normal" :  h[:is_deal]="trending"}  )
						

					end
		end
		return matched_deal
	end

end
