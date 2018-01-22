class CreateUserGifts < ActiveRecord::Migration
  def change
    create_table :user_gifts do |t|
      t.integer :app_user_id
      t.integer :gift_id
      t.integer :order_id
      t.string :status

      t.timestamps null: false
    end
  end
end
