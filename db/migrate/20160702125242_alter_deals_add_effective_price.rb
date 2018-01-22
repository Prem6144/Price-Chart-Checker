class AlterDealsAddEffectivePrice < ActiveRecord::Migration
  def change
    add_column :deals, :effective_price, :decimal
    add_column :app_users, :primary_id, :string
    add_column :app_users, :secondary_id, :string
  end
end
