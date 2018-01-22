class CreateOrderItems < ActiveRecord::Migration
  def change
    create_table :order_items do |t|
      t.integer :order_id
      t.string :ref_number
      t.integer :deal_id
      t.float :deal_price
      t.float :effective_price
      t.float :discount_price
      t.date :activation_date
      t.string :status
      
      t.timestamps null: false
    end
  end
end