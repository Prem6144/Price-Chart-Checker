class CreateReferContactDetails < ActiveRecord::Migration
  def change
    create_table :refer_contact_details do |t|
      t.integer :app_user_id
      t.string :email_id
      t.string :mobile_no
      t.string :name
      
      t.timestamps null: false
    end
  end
end
