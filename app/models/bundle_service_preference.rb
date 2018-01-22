class BundleServicePreference < ActiveRecord::Base
	belongs_to :service_preference
	def as_json(opts={})
    	json = super(opts)
    	Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  	end
end
