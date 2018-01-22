class CreateOrderEquipments < ActiveRecord::Migration
  def change
    create_table :order_equipments do |t|
      t.integer :order_id
      t.integer :equipment_id
      t.decimal :equipment_price, precision: 5, scale: 2

      t.timestamps null: false
    end
  end
end
