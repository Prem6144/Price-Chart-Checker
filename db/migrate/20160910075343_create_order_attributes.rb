class CreateOrderAttributes < ActiveRecord::Migration
  def change
    create_table :order_attributes do |t|
      t.integer :order_id
      t.integer :ref_id
      t.string :ref_type
      t.decimal :price, precision: 5, scale: 2

      t.timestamps null: false
    end
  end
end
