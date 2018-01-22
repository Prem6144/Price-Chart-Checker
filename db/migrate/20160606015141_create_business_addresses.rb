class CreateBusinessAddresses < ActiveRecord::Migration
  def change
    create_table :business_addresses do |t|
      t.integer :business_id
      t.integer :address_type #branch_address or billing or shipping
      t.string :address_name
      t.string :zip
      t.string :address1
      t.string :address2
      t.string :contact_number
      t.string :manager_name
      t.string :manager_contact
      t.timestamps null: false
    end
  end
end