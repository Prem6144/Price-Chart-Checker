class CableServicePreference < ActiveRecord::Base
	belongs_to :service_preference
	def as_json(opts={})
		json = super(opts)
		Hash[*json.map{|k, v| [k, v || ""]}.flatten]
	end

	def self.cable_best_deal(service_preference,select_data,deal_validation_conditions,restricted_deals)
		app_user_free_channels = service_preference.cable_service_preference.free_channels
		best_deals = Deal.joins(:cable_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cable_deal_attributes.channel_count >= ? AND price <= ? AND deals.id not in (?)", app_user_free_channels,service_preference.price,restricted_deals).order("price ASC")

		if best_deals.blank?
			best_deals=Deal.joins(:cable_deal_attributes).select(select_data).where(deal_validation_conditions + " AND deals.id not in (?)",restricted_deals).order("price ASC")
    end
    best_deals
	end
end
