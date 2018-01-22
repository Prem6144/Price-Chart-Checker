class AlterAppUserAndBusiness < ActiveRecord::Migration
  def change
    remove_column :orders, :is_service_address_same
    remove_column :orders, :is_shipping_address_same
    add_column :app_users, :is_service_address_same, :integer
    add_column :app_users, :is_shipping_address_same, :integer
    add_column :businesses, :is_service_address_same, :integer
    add_column :businesses, :is_shipping_address_same, :integer
  end
end
