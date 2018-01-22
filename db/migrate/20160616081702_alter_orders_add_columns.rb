class AlterOrdersAddColumns < ActiveRecord::Migration
  def change
  	add_column :orders, :primary_id, :string
  	add_column :orders, :secondary_id, :string
  	add_column :orders, :is_shipping_address_same, :integer
  	add_column :businesses, :manager_name, :string
  	add_column :businesses, :manager_contact, :string
  end
end