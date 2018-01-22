class CreateOrders < ActiveRecord::Migration
  def change
    create_table :orders do |t|
      t.string :order_id, null: false, default: ""
      t.integer :deal_id
      t.integer :app_user_id
      t.string :status, null: false, default: "In-progress"
      t.float :deal_price
      t.float :effective_price
      t.datetime :activation_date

      t.timestamps null: false
    end
  end
end
