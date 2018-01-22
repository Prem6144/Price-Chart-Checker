class CreateOrderExtraServices < ActiveRecord::Migration
  def change
    create_table :order_extra_services do |t|
      t.integer :order_id
      t.integer :deal_extra_service_id
      t.string :service_name
      t.decimal :price, precision: 5, scale: 2

      t.timestamps null: false
    end
  end
end
