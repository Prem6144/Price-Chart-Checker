class Api::V1::DealsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json


	def index
		if params[:zip_code].present? && params[:category].blank?
			@deals = Deal.where("is_active = ?", true).where(zip: params[:zip_code]).order("price DESC")
		elsif params[:category].present? && params[:zip_code].blank?
			@deals = Deal.where("is_active = ?", true).where(service_category_id: params[:category]).order("price DESC")
		elsif params[:zip_code].present? && params[:category].present?
			@deals = Deal.where("is_active = ?", true).where(zip: params[:zip_code]).where(service_category_id: params[:category]).order("price ASC")
		end
		if @deals.present?
			#@deals.each do |deal|
			render :json => { :deals   => @deals.as_json(:include => :ratings),}
			#end
		else
			render :status => 401,:json => { :success => false }
		end
	end

	def compare_deals

		if params[:app_user_id].present? && params[:service_category_id].present?
			category = ServiceCategory.where(id: params[:service_category_id]).first.name
			app_user = AppUser.find_by_id(params[:app_user_id])
			user_preference_price = app_user.service_preferences.where(service_category_id: params[:service_category_id]).first.price rescue 0
		end

		if params[:service_category_id].present?
			category = ServiceCategory.where(id: params[:service_category_id]).first.name
		end

		if params[:deal_id_first].present? && params[:deal_id_second].present? && params[:effective_price_1].present? && params[:effective_price_2].present?
			deal_1 = Deal.find_by_id(params[:deal_id_first])
			deal_2 = Deal.find_by_id(params[:deal_id_second])
			if deal_1.present? && deal_2.present? 
				if user_preference_price.present?
					saving_1_value = "%.2f" % (user_preference_price - params[:effective_price_1].to_f rescue 0)
					saving_2_value = "%.2f" % (user_preference_price - params[:effective_price_2].to_f rescue 0)
					saving_1 = saving_1_value.to_f > 0 ? saving_1_value.to_f : 0.00
					saving_2 = saving_2_value.to_f > 0 ? saving_2_value.to_f : 0.00
				else
					saving_1 = "%.2f" % (0.to_f)
					saving_2 = "%.2f" % (0.to_f)
				end
				if params[:service_category_id].to_i == Deal::BUNDLE_CATEGORY 
					category_combo_1 = (deal_1.deal_attributes.first.bundle_combo.sub! 'and', ',').delete(' ')
					category_combo_2 = (deal_2.deal_attributes.first.bundle_combo.sub! 'and', ',').delete(' ')
				else
					category_combo_1 = category
					category_combo_2 = category
				end

				deal_1 = deal_1.as_json(:except => [:created_at, :updated_at, :image, :price, :effective_price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments,:deal_attributes]).merge(:effective_price => params[:effective_price_1],:saving => saving_1,category_image_icon: category_combo_1)
				deal_2 = deal_2.as_json(:except => [:created_at, :updated_at, :image, :price, :effective_price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments,:deal_attributes]).merge(:effective_price => params[:effective_price_2], :saving=> saving_2,category_image_icon: category_combo_2)
				render :status => 200,:json => { :success => true, deal_1: deal_1, deal_2: deal_2 }
			else
				render :status => 400,:json => { :success => false, message: 'Please select valid deal.' }
			end
		elsif params[:deal_id_first].present? && params[:deal_id_second].present? && params[:effective_price_1].blank? && params[:effective_price_2].blank?
			deal_1 = Deal.find_by_id(params[:deal_id_first])
			deal_2 = Deal.find_by_id(params[:deal_id_second])
			if deal_1.present? && deal_2.present?  
				if user_preference_price != 0
				saving_1_value = "%.2f" % (user_preference_price - deal_1.try(:effective_price) rescue 0)
				saving_2_value = "%.2f" % (user_preference_price - deal_2.try(:effective_price) rescue 0)
				saving_1 = saving_1_value.to_f > 0 ? saving_1_value : 0.00
				saving_2 = saving_2_value.to_f > 0 ? saving_2_value : 0.00
				else
					saving_1 = "%.2f" % (0.to_f)
					saving_2 = "%.2f" % (0.to_f)
				end
				if params[:service_category_id].to_i == Deal::BUNDLE_CATEGORY 
					category_combo_1 = (deal_1.deal_attributes.first.bundle_combo.sub! 'and', ',').delete(' ')
					category_combo_2 = (deal_2.deal_attributes.first.bundle_combo.sub! 'and', ',').delete(' ')
				else
					category_combo_1 = category
					category_combo_2 = category
				end

				deal_1 = deal_1.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments,:deal_attributes]).merge(:saving => saving_1,category_image_icon: category_combo_1)
				deal_2 = deal_2.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments,:deal_attributes]).merge(:saving => saving_2,category_image_icon: category_combo_2)
				render :status => 200,:json => { :success => true, deal_1: deal_1, deal_2: deal_2 }
			else
				render :status => 400,:json => { :success => false, message: 'Please select valid deal.' }
			end
		else
			render :status => 400,:json => { :success => false, message: 'Please select valid deal.' }
		end
	end

	def service_deal_config
		key = ServiceDealConfig.first.show_deal_name
		render :status => 200,:json => { :success => true, show_deal_name: key }
	end

	def password_complicated_setting
		key = ServiceDealConfig::where(config_key: "password_complicated").first.config_value
		if key.present?
		render :status => 200,:json => { :success => true, key: key }
		else
			render :status => 401,:json => { :success => false }
		end
	end

	def get_deal_channels
		deal_id = params[:deal_id]
		cable_deal_attribute = CableDealAttribute.where(:deal_id => deal_id).last
		channel_ids = cable_deal_attribute.channel_ids.present? ? eval(cable_deal_attribute.channel_ids) : []
		channels_list_record = Channel.where(id: channel_ids)
		channel_categories = channels_list_record.pluck(:category_name).uniq
		channels= []
		channel_categories.each do |category_name|
			channels_list = []
			channels_hash = {}
			channels_hash['category_name'] = category_name
			category_channels = channels_list_record.where(category_name: category_name)
			channels_hash['channel'] = category_channels
			channels << channels_hash
		end
		render :json => { :channels   => channels.as_json}
	end

	def get_channel_details
		channel_id = params[:channel_id]
		channel = Channel.find_by_id(channel_id)
		render :json => { :channel => channel.as_json}
	end

	def get_estimated_bandwidth
		bandwidth_in_mb,bandwidth_in_gb = BandwidthCalculatorSetting.calculate_bandwidth(params)
		matching_deal_id=InternetDealAttribute.where(:download=>(bandwidth_in_mb)/100).pluck(:deal_id).first
		upper_range=(bandwidth_in_mb)/1000
		lower_range=((bandwidth_in_mb)/1000)/2
		puts upper_range
		puts lower_range
			deal_name =[]
		deals=InternetDealAttribute.where("download > ? and download < ?",lower_range,upper_range).pluck(:deal_id)
		deals.each do |d|
			deal_name << Deal.find(d).title
		end
		render :json => { :bandwidth_in_mb => bandwidth_in_mb.to_s,:bandwidth_in_gb => bandwidth_in_gb.to_f.round, :matching_deal_id=>deals, :deal_name=>deal_name}
	end

	def fetch_deal_details
		if params[:deal_id].present?
			deal = Deal.find(params[:deal_id])
			     render :status => 200,
							 :json => {
									 :success => true,
									 :deals => deal.as_json(:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments],:include => ['bundle_deal_attributes','bundle_equipments'])
							 }
		else
			render :json => { :success => false}
		end
	end

	def cellphone_equipments
		app_user = AppUser.find_by_id(params[:app_user_id])
		deal = Deal.find_by_id(params[:deal_id])
		if app_user.present? && deal.present? && deal.service_category_id == Deal::CELLPHONE_CATEGORY
      if deal.cellphone_equipments.present?
        equipments =deal.cellphone_equipments.select('id,model, price')
       	render :json => { :success => true, equipments: equipments.as_json(:except=>[:id])}
      else
        render :json => { :success => false}
      end
    else
    	render :json => { :success => false}
    end
	end

	def customisable_deals
		deals = Deal.where('is_customisable =? ', true)
		render :json => { :success => true, deals: deals.as_json(:methods => [:service_category_name, :service_provider_name])}
	end

	def cellphone_details
		cellphone_detail=CellphoneDetail.select('id,cellphone_name')

	render :json=>{
	 :cellphone_details=>cellphone_detail
   }
	end

	def channel_customisable_deals
		deals = Deal.where('is_customisable =? AND service_category_id=?', true,Deal::CABLE_CATEGORY)
		# deals = Deal.find(171)
		if deals.present?
		render :json => { :success => true, deals: deals.as_json(:include =>{:channel_packages => {:methods=>[:channel_name]},:deal_attributes => {:methods => [:channel_name]},:deal_extra_services => {:methods => [:service_name,:service_description]}},:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])}
		else
			render :json => { :success => false}
		end
	end

	def customisable_deal_deatail

if params[:deal_id].present?
			deal = Deal.find(params[:deal_id])
			if deal.service_category_id == Deal::CABLE_CATEGORY
				render :json => { :success => true, deals: deal.as_json(:include =>{:channel_packages => {:methods=>[:channel_name]},:deal_attributes => {:methods => [:channel_name]},:deal_extra_services => {:methods => [:service_name,:service_description]}},:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_equipments])}
			elsif deal.service_category_id == Deal::CELLPHONE_CATEGORY

			 render :json => { :success => true, deals: deal.as_json(:include =>{:deal_equipments =>{:except=>[:available_colors],:methods => [:available_color,:cellphone_name,:brand,:description,:image]},:deal_extra_services => {:methods => [:service_name,:service_description]} },:except => [:created_at, :updated_at, :image, :price],:methods => [:deal_image_url, :average_rating, :rating_count, :deal_price,:service_category_name, :service_provider_name,:deal_additional_offers,:deal_attributes])}
			else
				render :json => { :success => false}
			end
		else
			render :json => { :success => false}
end






end
end
