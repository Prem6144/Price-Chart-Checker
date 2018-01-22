class Api::V1::OrdersController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def create
		user_type = params[:app_user][:user_type].present? ? params[:app_user][:user_type] : nil
		if [AppUser::RESIDENCE,AppUser::BUSINESS].include?(user_type)
			order = Order.new(order_params)
			order.order_id=rand(36**8).to_s(36).upcase
			if order.save
				order_items = OrderItem.create_order_items(params,order.id)
				if  order_items.first.deal.is_customisable == true
					order_equipment = OrderAttribute.create_order_attributes(params,order.id)
					if order_items.first.deal.service_category_id == Deal::CELLPHONE_CATEGORY
						order_extra_services = OrderExtraService.create_order_extra_services(params,order.id)
					end
					order_equipment = OrderEquipment.create_order_equipments(params,order.id)
        end
				app_user = AppUser.update_app_user(params,order.app_user_id,order)
				order_addresses = OrderAddress.create_order_addresses(params,order.id)
				if user_type == AppUser::BUSINESS
					business = Business.create_business(params)
					params[:business][:business_name]= encode_api_data(params[:business][:business_name]) if  params[:business][:business_name].present?
					params[:business][:ssn]= encode_api_data(params[:business][:ssn]) if params[:business][:ssn].present?
					params[:business][:federal_number]= encode_api_data(params[:business][:federal_number]) if  params[:business][:federal_number].present?
					params[:business][:business_name]= encode_api_data(params[:business][:business_name]) if  params[:business][:business_name].present?

					if business.present?
						business_addresses = BusinessAddress.create_business_addresses(params,business.id)
						business_user = BusinessAppUser.create_business_app_user(business.id,app_user.id)
					end
					OrderMailer.delay.order_confirmation(app_user,order)
					UserGift.delay.create_user_gift(order.id)
					gifts = Gift.all
					render :status => 200,:json => {:success => true,:order => order.as_json,:order_items => order_items.as_json,:app_user => app_user.as_json,:business => business.as_json,:business_addresses => business_addresses.as_json,:gifts => gifts.as_json(:methods => :gift_image_url, :except => :image)}
				else
					app_user_addresses = AppUserAddress.create_app_user_addresses(params,app_user.id)
					OrderMailer.delay.order_confirmation(app_user,order)
					UserGift.delay.create_user_gift(order.id)
					gifts = Gift.all
					render :status => 200,:json => {:success => true,:order => order.as_json,:order_items => order_items.as_json,:app_user => app_user.as_json,:app_user_addresses => app_user_addresses.as_json,:gifts => gifts.as_json(:methods => :gift_image_url, :except => :image)}
				end
			else
				render :status => 401,
							 :json => { :success => false ,:message => order.errors.full_messages}
			end
		else
			render :status => 401,
						 :json => { :success => false ,:message => 'Invalid Parameters'}
		end
	end

	def update
		user_type = params[:app_user][:user_type].present? ? params[:app_user][:user_type] : nil
		if [AppUser::RESIDENCE,AppUser::BUSINESS].include?(user_type)
			order = Order.where(:id => params[:order][:id],:app_user_id => params[:order][:app_user_id]).first
			if order.present?
				if order.update_attributes(order_params)
					order_items = OrderItem.update_order_items(params)
					app_user = AppUser.update_app_user(params,order.app_user_id,order)
					order_addresses = OrderAddress.update_order_addresses(params)
					if user_type == AppUser::BUSINESS
						business = Business.get_business_by_user(app_user.id)
						OrderMailer.delay.order_confirmation(app_user,order)
						render :status => 200,:json => {:success => true,:order => order.as_json,:order_items => order_items.as_json,:app_user => app_user.as_json,:business => business.as_json,:business_addresses => business.business_addresses.as_json}
					else
						OrderMailer.delay.order_confirmation(app_user,order)
						render :status => 200,:json => {:success => true,:order => order.as_json,:order_items => order_items.as_json,:app_user => app_user.as_json,:app_user_addresses => app_user.app_user_addresses.as_json}
					end
				else
					render :status => 401,
								 :json => { :success => false ,:message => order.errors.full_messages}
				end
			else
				render :status => 401,
							 :json => { :success => false ,:message => 'No Order Found'}
			end
		else
			render :status => 401,
						 :json => { :success => false ,:message => 'Invalid Parameters'}
		end
	end

	def create_bck
		@order = Order.new(order_params)
		@order.order_id=rand(36**8).to_s(36).upcase
		@order.activation_date = Time.now
		if @order.save
			@app_user= @order.app_user
			@orders_count = @app_user.orders.count
			@gift = Gift.find_by_activation_count_condition(@orders_count)
			@gift_id = @gift.try(:id)
			if @gift_id.present? && @order.present?
				@user_gifts = @order.user_gifts.create(gift_id: @gift_id, app_user_id: params[:app_user_id])
				@gift_amount = @gift.amount
				@app_user.total_amount = @app_user.total_amount + @gift_amount
				@app_user.save
				@message = "You have won $#{@gift_amount} gift card."
				@message_status = true
			else
				@last_gift_activation_count = @app_user.gifts.last.try(:activation_count_condition)
				@next_gift_activation_count = Gift.all.where("activation_count_condition > ?", @last_gift_activation_count ).limit(1).first.activation_count_condition rescue nil
				if @next_gift_activation_count.present?
					@message_count = @next_gift_activation_count - @orders_count
					@message = "Please include #{@message_count} more service in your order"
					@message_status = true
				else
					@message = "There are no Gifts"
					@message_status = false
				end
			end
			@order_message = "for switching #{@orders_count} service using ServiceDeals"
			@gifts = Gift.all
			render :status => 200,:json => {:success => true,:order_message => @order_message, :message => @message,:message_status => @message_status,:gifts => @gifts.as_json(:methods => :gift_image_url, :except => :image)}
		else
			render :status => 401,
						 :json => { :success => false }
		end
	end

	def get_orders
		@order = Order.where("id = ?", params[:id])
		if @order.present?
			render :status => 200,
						 :json => {
								 :success => true,
								 :orders => @order.as_json(:except => [:created_at, :updated_at])

						 }
		else
			render :json => { :success => false }
		end
	end

	def my_orders
		app_user = AppUser.where(:id => params[:app_user_id]).first
		if app_user.present? and app_user.user_type == AppUser::BUSINESS
			business = BusinessAppUser.where(:app_user_id => params[:app_user_id]).first
			app_user_id = business.present? ? BusinessAppUser.where(:business_id => business.business_id).pluck(:app_user_id) : []
		else
			app_user_id = [params[:app_user_id]]
		end
		@orders = Order.select("orders.*,order_items.deal_id,order_items.deal_price,order_items.effective_price").joins(:order_items).where(:app_user_id => app_user_id).order("id DESC")

		if @orders.present?
			render :status => 200,
						 :json => {
								 :success => true,
								 :order => @orders.as_json(:include => {:deal => {:methods => :deal_image_url}},:methods => :order_place_time)
						 }

		else
			render :json => { :success => false }
		end
	end

	def my_order_details
		if params[:order_id].present? and params[:app_user_id].present?
			order = Order.where(:id => params[:order_id]).first
			if order.present?
				if order.order_items.first.deal.is_customisable == true
					order_attributes = Order.attributes(order.id)
					if order.order_items.first.deal.service_category_id == Deal::CELLPHONE_CATEGORY 
						order_extra_services = Order.extra_services(order.id)
					elsif order.order_items.first.deal.service_category_id == Deal::CABLE_CATEGORY
						order_extra_services = Order.channel_packages(order.id,type="channel")
					end
					order_equipments = Order.equipments(order.id)
				end
				#order_items = order.order_items
				app_user = AppUser.where(:id => params[:app_user_id]).first
				#category = ServiceCategory.select(" distinct name").joins(:deals).where("deals.id = ?",order_items.first.deal_id).first.name.downcase
				if app_user.present? and app_user.user_type == AppUser::BUSINESS
					business = Business.get_business_by_user(app_user.id)

					render :status => 200,
								 :json => {
										 :success => true,
										 :order => order.as_json(:except => [:created_at, :updated_at]),
										 :order_addresses => order.order_addresses.as_json(:except => [:created_at, :updated_at]),
										 #:order_items => order_items.as_json(:include => {:deal => {:methods => :deal_image_url,:include => ["#{category}_deal_attributes".to_sym => {:include => ["#{category}_equipments".to_sym]}]}},:methods => :order_place_time),
										 :order_items => Deal.build_custom_json(order.id).as_json,
										 :app_user => app_user,
										 :business => business.present? ? business.as_json(:except => [:created_at, :updated_at]) : {},
										 :order_attributes => order_attributes.present? ? order_attributes : {},
										 :order_extra_services => order_extra_services.present? ? order_extra_services : {},
										 :order_equipments => order_equipments.present? ? order_equipments : []
								 }
				else
					render :status => 200,
								 :json => {
										 :success => true,
										 :order => order.as_json(:except => [:created_at, :updated_at]),
										 :order_addresses => order.order_addresses.as_json(:except => [:created_at, :updated_at]),
										 #:order_items => order_items.as_json(:include => {:deal => {:methods => :deal_image_url,:include => ["#{category}_deal_attributes".to_sym => {:include => ["#{category}_equipments".to_sym]}]}},:methods => :order_place_time),
										 :order_items => Deal.build_custom_json(order.id).as_json,
										 :app_user => app_user,
										 :order_attributes => order_attributes.present? ? order_attributes : {},
										 :order_extra_services => order_extra_services.present? ? order_extra_services : {},
										 :order_equipments => order_equipments.present? ? order_equipments : []

								 }
				end

			else
				render :json => { :success => false,:message => 'No order found for the combination of order_id and app_user_id' }
			end
		else
			render :json => { :success => false,:message => 'please provide order_id & app_user_id in params and this is post request.' }
		end
	end

	def fetch_user_and_deal_details
		if params[:app_user_id].present? and params[:deal_ids].present?
			app_user = AppUser.where(:id => params[:app_user_id]).first
			deals = Deal.where(:id => params[:deal_ids].to_s.split(','))
			if app_user.user_type == AppUser::BUSINESS
				business = Business.select('businesses.*').joins(:business_app_users).where("business_app_users.app_user_id = ?",app_user.id).first
				render :status => 200,
							 :json => {
									 :success => true,
									 :app_users => app_user.as_json(:except => [:created_at, :updated_at]),
									 :business => business.present? ? business.as_json(:except => [:created_at, :updated_at]) : {},
									 :business_addresses => business.present? ? business.business_addresses.as_json(:except => [:created_at, :updated_at]) : [],
									 :deals => deals.as_json(:except => [:created_at, :updated_at],:methods => [:deal_image_url,:average_rating])
							 }
			else
				render :status => 200,
							 :json => {
									 :success => true,
									 :app_users => app_user.as_json(:except => [:created_at, :updated_at]),
									 :app_user_addresses => app_user.app_user_addresses.as_json(:except => [:created_at, :updated_at]),
									 :deals => deals.as_json(:except => [:created_at, :updated_at],:methods => [:deal_image_url,:average_rating])
							 }
			end
		else
			render :json => { :success => false,:message => 'please provide app_user_id and deal_ids in params' }
		end
	end

	def get_order_address
		if params[:order_id].present? and Order.find(params[:order_id]).order_addresses.present?
			order_addresses = OrderAddress.where(:order_id => params[:order_id])
			if order_addresses.present?
				render :status => 200,
							 :json => {
									 :success => true,
									 :order_addresses => order_addresses.as_json
							 }

			else
				render :json => { :success => false, :message => 'no address exists with this order, please try some different order.' }
			end
		else
			render :json => { :success => false,:message => 'please provide order_id in params' }
		end
	end

	def validate_business_name
		if params[:business_type].present? and params[:business_name].present? and (params[:ssn].present? || params[:federal_number].present?)
			if params[:business_type].to_i == Business::SOLE_PROPRIETOR
				#business = Business.where("business_name = ? or ssn = ?",params[:business_name],params[:ssn]).first
				business = Business.where("ssn = ?",params[:ssn]).first
				if business.present?
					render :json => { :success => false,:message => 'Business with this name or SSN already exists, Please enter valid information.' }
				else
					render :json => { :success => true }
				end
			elsif params[:business_type].to_i == Business::REGISTERED
				#business = Business.where("business_name = ? or federal_number = ?",params[:business_name],params[:federal_number]).first
				business = Business.where("federal_number = ?",params[:federal_number]).first
				if business.present?
					render :json => { :success => false,:business => business,:message => "We found Business #{params[:business_name]} with Federal Tax No: #{params[:federal_number]} is already registered with us, do you want to add yourself in this business." }
				else
					render :json => { :success => true }
				end
			end
		else
			render :json => { :success => false,:message => 'Insufficient Parameters' }
		end
	end

	def get_states
	states=Statelist.all.order('state ASC').pluck(:state).uniq
  states.unshift "Select State"
	render :json=>{
	 :states=>states
   }
	end

	def get_cities
		cities=Statelist.where(:state=>params[:state]).pluck(:city)
		render :json=>{
		 :cities=>cities
	   }
	end

	private
	def order_params
		# raise params[:order].to_yaml
		params.require(:order).permit(:id,:order_id,:deal_id,:app_user_id,:status,:deal_price,:effective_price,:activation_date,:order_type,:primary_id,:secondary_id,:is_shipping_address_same,:primary_id_number,:secondary_id_number,:free_text)
	end

end
