class CreateServiceDealConfigs < ActiveRecord::Migration
  def change
    create_table :service_deal_configs do |t|
      t.boolean :show_deals_logo

      t.timestamps null: false
    end
  end
end
