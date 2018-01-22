class AlterOrdersAddIsServiceAddressSame < ActiveRecord::Migration
  def change
  	add_column :orders, :is_service_address_same, :integer
  end
end