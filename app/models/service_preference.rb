class ServicePreference < ActiveRecord::Base
	belongs_to :app_user
	belongs_to :service_category
	belongs_to :service_provider
	has_one :internet_service_preference, :dependent => :destroy
	has_one :cable_service_preference, :dependent => :destroy
	has_one :telephone_service_preference, :dependent => :destroy
	has_one :bundle_service_preference, :dependent => :destroy
	has_one :cellphone_service_preference, :dependent => :destroy
  	#accepts_nested_attributes_for :internet_service_preference

	def as_json(opts={})
    	json = super(opts)
    	Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  	end
  	
end
