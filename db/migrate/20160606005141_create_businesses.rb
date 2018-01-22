class CreateBusinesses < ActiveRecord::Migration
  def change
    create_table :businesses do |t|
      t.string :business_name
      t.integer :business_type #sole properitory or registered
      t.string :business_status
      t.string :business_dba
      t.string :federal_number #federal tax id number
      t.string :db_number # dun and brad street number
      t.date :dob
      t.string :ssn
      t.string :contact_number
      t.string :status
      t.timestamps null: false
    end
  end
end