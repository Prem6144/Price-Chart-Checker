class CreateAppUserAddresses < ActiveRecord::Migration
  def change
    create_table :app_user_addresses do |t|
      t.integer :app_user_id
      t.string :address_name
      t.string :zip
      t.string :address1
      t.string :address2
      t.integer :address_type #shipping or billing
      t.string :contact_number
      t.timestamps null: false
    end
  end
end