class AddPrimarySecondaryIdNumber < ActiveRecord::Migration
  def change
    add_column :app_users, :primary_id_number, :string
    add_column :orders, :primary_id_number, :string
    add_column :app_users, :secondary_id_number, :string
    add_column :orders, :secondary_id_number, :string
  end
end
