class AddCityToAddress < ActiveRecord::Migration
  def change
    add_column :app_user_addresses, :city, :string
    add_column :business_addresses , :city, :string
    add_column :order_addresses , :city, :string
    
    
  end
end
