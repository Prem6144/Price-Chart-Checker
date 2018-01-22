class Order < ActiveRecord::Base
	belongs_to :app_user
	belongs_to :deal
	has_many :order_items, :dependent => :destroy
	has_many :order_addresses, :dependent => :destroy
	has_many :order_equipments, :dependent => :destroy
	has_many :order_extra_services, :dependent => :destroy
	has_many :order_attributes, :dependent => :destroy
	validates_uniqueness_of :order_id

	has_many :user_gifts,:dependent => :destroy
	has_many :gifts, :through => :user_gifts
	before_create :update_sequences
	before_create :generate_order_number
	# validates_presence_of :deal_id, :app_user_id, :effective_price, :deal_price, :status
	SEQUENCE_TYPE = 'ORDER'##order
	TRANSACTIONAL_ORDER = 0
	NON_TRANSACTIONAL_ORDER = 1
	## ORDER WORKFLOW STATUSES
	NEW_ORDER = 'New Order'
	IN_PROGRESS = 'In Progress'
	VERIFICATION_PENDING = 'Verification Pending'
	ASSIGNED_TO_VENDOR = 'Assigned to Vendor'
	PROCESS_STARTED = 'Process Started'
	PAYMENT_PENDING = 'Payment Pending'
	SHIPMENT_DONE = 'Shipment Done'
	INSTALLATION_DONE = 'Installation Done'
	SERVICE_ACTIVATED = 'Service Activated'
	COMPLETED = 'Completed'
	CANCELLED = 'Cancelled'

	def order_place_time
		self.created_at.strftime("%d/%m/%Y %I:%M %p")
	end

	def generate_order_number
		sequence = Sequence.where(:seq_type => Order::SEQUENCE_TYPE).first
		last_transaction_number = sequence.present? ? (sequence.seq_number + 1) : 1
		deal = Deal.where(self.deal_id).first
		category = ServiceCategory.select('name').where(deal.service_category_id).first
		provider = ServiceProvider.select('name').where(deal.service_provider_id).first
		order_number = category.name[0..1].upcase + provider.name[0..1].upcase + deal.id.to_s + '#' + last_transaction_number.to_s.rjust(8,'0')
		self.order_number = order_number
		self.order_id = order_number
		self.activation_date = Time.now
		self.status = Order::NEW_ORDER
		#rand(1_00_000..10_00_000-1).to_s
	end

	def self.attributes(order_id)
		order = self.find(order_id)
		service_category_id = self.find(order_id).order_items.first.deal.service_category_id
		attribute = {}
	if order.order_attributes.present?
		if service_category_id == Deal::CELLPHONE_CATEGORY
			order_attribute = order.order_attributes.where(ref_type: "cellphone").first
attribute[:price]= order_attribute.price

			attribute[:title] = CellphoneDealAttribute.find(order_attribute.ref_id).title
		elsif service_category_id == Deal::CABLE_CATEGORY
		if order.order_attributes.where(ref_type: "cable").present?
			order_attribute = order.order_attributes.where(ref_type: "cable").first
			attribute[:title] = CableDealAttribute.find(order_attribute.ref_id).description
			attribute[:price]= order_attribute.price

		end
end

end
		attribute
  	end

  	def self.extra_services(order_id)
		order = self.find(order_id)
		order_extra_service = order.order_extra_services.first
		extra_service = {}
	if order.order_extra_services.present?
		deal_extra_service = DealExtraService.find(order.order_extra_services.first.deal_extra_service_id)
		extra_service [:price]= deal_extra_service.price
		extra_service [:title] = deal_extra_service.extra_service.service_name
end
		extra_service
  	end

  	def self.equipments(order_id)
		order = self.find(order_id)
equipments =[]
if order.order_equipments.present?		
order_equipments = order.order_equipments
		
		if self.find(order_id).order_items.first.deal.service_category_id  == Deal::CELLPHONE_CATEGORY
			order_equipments.each do |equipment|
				order_equipment= {}
				order_equipment[:color_name] = equipment.color
				cellphone_detail = CellphoneEquipment.find(equipment.equipment_id).cellphone_detail
				order_equipment[:brand] = cellphone_detail.brand
				order_equipment[:name] = cellphone_detail.cellphone_name
				order_equipment[:price] = CellphoneEquipment.find(equipment.equipment_id).price
				order_equipment[:image] = cellphone_detail.image.url
				equipments << order_equipment
			end
		elsif self.find(order_id).order_items.first.deal.service_category_id  == Deal::CABLE_CATEGORY
			order_equipment= {}
			cable_equipment = CableEquipment.find(order.order_equipments.first.equipment_id)
			order_equipment[:name] = cable_equipment.name 
			order_equipment[:price] = cable_equipment.price
			equipments << order_equipment
		end
end
		equipments
  	end

  	def self.channel_packages(order_id,type)
  		order = self.find(order_id)
if order.order_attributes.where(ref_type: type).first.present?
  		order_attribute = order.order_attributes.where(ref_type: type).first
  		channel_package = ChannelPackage.find(order_attribute.ref_id)
			extra_service = {}
			extra_service [:title]= channel_package.package_name
			extra_service [:price]= channel_package.price
end

			extra_service
  	end

	private

	## sequence table is used to manage incremental order or invoice or other number by sequence type
	def update_sequences
		seq = Sequence.find_or_initialize_by(:seq_type => SEQUENCE_TYPE)
		seq_number = seq.seq_number.present? ? seq.seq_number : 0
		seq.update_attributes(:seq_number => seq_number + 1)
	end




end
