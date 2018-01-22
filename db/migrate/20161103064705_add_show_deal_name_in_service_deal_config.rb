class AddShowDealNameInServiceDealConfig < ActiveRecord::Migration
  def change
    add_column :service_deal_configs, :show_deal_name, :boolean
  end
end
