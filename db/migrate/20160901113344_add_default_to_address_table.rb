class AddDefaultToAddressTable < ActiveRecord::Migration
  def change
    add_column :app_user_addresses, :is_default, :boolean,default: false
    add_column :business_addresses , :is_default, :boolean, default: false
  end
end
