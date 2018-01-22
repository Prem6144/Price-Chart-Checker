class RemovePriceFromCableDealAttributes < ActiveRecord::Migration
  def change
    remove_column :cable_deal_attributes, :price
  end
end
