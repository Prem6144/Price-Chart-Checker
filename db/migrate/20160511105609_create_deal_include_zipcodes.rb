class CreateDealIncludeZipcodes < ActiveRecord::Migration
  def change
    create_table :deal_include_zipcodes do |t|
      t.integer :deal_id, null: false
      t.integer :zipcode_id, null: false
      t.timestamps null: false
    end
  end
end
