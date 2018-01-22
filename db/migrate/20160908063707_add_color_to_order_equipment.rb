class AddColorToOrderEquipment < ActiveRecord::Migration
  def change
    add_column :order_equipments, :color, :text
  end
end
