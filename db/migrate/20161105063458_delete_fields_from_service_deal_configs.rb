class DeleteFieldsFromServiceDealConfigs < ActiveRecord::Migration
  def change
    remove_column :service_deal_configs, :show_deal_name
    remove_column :service_deal_configs, :show_deals_logo
  end
end
