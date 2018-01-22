class CellphoneServicePreference < ActiveRecord::Base
  belongs_to :service_preference
  def as_json(opts={})
    json = super(opts)
    Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  end

  def self.cellphone_best_deal(service_preference,select_data,deal_validation_conditions,restricted_deals,options= {})
    # raise restricted_deals.to_yaml
    best_deals = nil
    existing_domestic_call_unlimited = service_preference.cellphone_service_preference.domestic_call_unlimited
    existing_domestic_call_minutes = (existing_domestic_call_unlimited == true) ? 0 : service_preference.cellphone_service_preference.domestic_call_minutes
    existing_data_plan = service_preference.cellphone_service_preference.data_plan
    existing_no_of_lines = (service_preference.cellphone_service_preference.no_of_lines.present? and service_preference.cellphone_service_preference.no_of_lines > 0) ? service_preference.cellphone_service_preference.no_of_lines : 1
    #no_of_lines_condition = " AND cellphone_deal_attributes.no_of_lines = #{existing_no_of_lines}"
    no_of_lines_condition= ''
    sort_by = options['sort_by'].present? ? options['sort_by'] : 'price ASC'
    best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.data_plan > ? AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND cellphone_deal_attributes.effective_price <= ? AND deals.id not in (?) #{no_of_lines_condition}", existing_data_plan,service_preference.price,restricted_deals)
    if best_deals.blank?
      best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.data_plan > ? AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes > ? AND cellphone_deal_attributes.effective_price <= ? AND deals.id not in (?) #{no_of_lines_condition}", existing_data_plan,existing_domestic_call_minutes,service_preference.price,restricted_deals)
    end
    if best_deals.blank?
      best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.data_plan = ? AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND cellphone_deal_attributes.effective_price <= ? AND deals.id not in (?) #{no_of_lines_condition}", existing_data_plan,service_preference.price,restricted_deals)
    end
    if best_deals.blank?
      best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.data_plan = ? AND cellphone_deal_attributes.domestic_call_minutes!='Unlimited' AND cellphone_deal_attributes.domestic_call_minutes > ? AND cellphone_deal_attributes.effective_price <= ? AND deals.id not in (?) #{no_of_lines_condition}", existing_data_plan,existing_domestic_call_minutes,service_preference.price,restricted_deals)
    end
    if best_deals.blank?
      best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.data_plan > ? AND cellphone_deal_attributes.effective_price <= ? AND deals.id not in (?) #{no_of_lines_condition}", existing_data_plan,service_preference.price,restricted_deals)
    end
    if best_deals.blank?
      best_deals = Deal.joins(:cellphone_deal_attributes).select(select_data).where(deal_validation_conditions+" AND cellphone_deal_attributes.domestic_call_minutes='Unlimited' AND cellphone_deal_attributes.effective_price < ? AND deals.id not in (?) #{no_of_lines_condition}",service_preference.price,restricted_deals)
    end
    best_deals = best_deals.order(sort_by)
  end

end
