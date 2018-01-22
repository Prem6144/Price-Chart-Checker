class AddFieldsToOrder < ActiveRecord::Migration
  def change
  	add_column :orders, :provider_status, :string, default: "In-progress"
  	add_column :orders, :provider_order_number, :string
  end
end
