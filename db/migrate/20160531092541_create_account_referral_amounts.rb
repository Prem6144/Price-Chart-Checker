class CreateAccountReferralAmounts < ActiveRecord::Migration
  def change
    create_table :account_referral_amounts do |t|
      t.integer :account_referral_id
      t.integer :referral_gift_amount_id

      t.timestamps null: false
    end
  end
end
