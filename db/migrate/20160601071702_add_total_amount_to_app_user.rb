class AddTotalAmountToAppUser < ActiveRecord::Migration
  def change
  	add_column :app_users, :total_amount, :float, :default => 0.0
  end
end
