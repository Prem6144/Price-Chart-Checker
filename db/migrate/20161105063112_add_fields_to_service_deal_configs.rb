class AddFieldsToServiceDealConfigs < ActiveRecord::Migration
  def change
    add_column :service_deal_configs, :config_key, :string
    add_column :service_deal_configs, :config_value, :string
  end
end
