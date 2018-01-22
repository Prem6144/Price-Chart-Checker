class AddStateToBusinessAddress < ActiveRecord::Migration
  def change
    add_column :business_addresses, :state, :text
  end
end
