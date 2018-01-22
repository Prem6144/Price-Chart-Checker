class AddStateToOrderAddresses < ActiveRecord::Migration
  def change
    add_column :order_addresses, :state, :text
  end
end
