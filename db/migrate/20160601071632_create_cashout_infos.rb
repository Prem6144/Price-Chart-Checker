class CreateCashoutInfos < ActiveRecord::Migration
  def change
    create_table :cashout_infos do |t|
      t.integer :app_user_id
      t.float :reedeem_amount
      t.string :email_id
      t.boolean :is_cash
      t.string :gift_card_type

      t.timestamps null: false
    end
  end
end
