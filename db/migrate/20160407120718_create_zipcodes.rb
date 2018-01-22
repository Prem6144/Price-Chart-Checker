class CreateZipcodes < ActiveRecord::Migration
  def change
    create_table :zipcodes do |t|
    	t.string :code
    	t.string :area
    	t.string :city
    	t.string :state
    	t.string :country
      	t.timestamps null: false
    end
  end
end
