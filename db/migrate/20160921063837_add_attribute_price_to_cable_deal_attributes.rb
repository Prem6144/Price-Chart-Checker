class AddAttributePriceToCableDealAttributes < ActiveRecord::Migration
  def change
  add_column  :cable_deal_attributes, :amount,:decimal, precision: 5, scale: 2
  end
end
