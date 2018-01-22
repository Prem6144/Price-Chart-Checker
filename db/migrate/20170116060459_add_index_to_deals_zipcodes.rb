class AddIndexToDealsZipcodes < ActiveRecord::Migration
  def change
  	add_index :deals_zipcodes, :zipcode_id
  	add_index :deals_zipcodes, :deal_id
  end
end
