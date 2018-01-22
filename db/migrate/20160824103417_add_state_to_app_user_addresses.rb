class AddStateToAppUserAddresses < ActiveRecord::Migration
  def change
    add_column :app_user_addresses, :state, :text
  end
end
