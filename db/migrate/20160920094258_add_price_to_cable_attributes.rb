class AddPriceToCableAttributes < ActiveRecord::Migration
  def change
	 add_column :cable_deal_attributes, :price,:decimal, precision: 5, scale: 2
  end
end
