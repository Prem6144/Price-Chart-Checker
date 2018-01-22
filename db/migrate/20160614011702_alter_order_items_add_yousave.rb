class AlterOrderItemsAddYousave < ActiveRecord::Migration
  def change
  	add_column :order_items, :you_save, :float
  end
end