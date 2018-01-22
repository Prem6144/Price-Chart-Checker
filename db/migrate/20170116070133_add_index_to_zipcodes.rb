class AddIndexToZipcodes < ActiveRecord::Migration
  def change
  	add_index :zipcodes, :code
  end
end
