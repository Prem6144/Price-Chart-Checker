class CreateAccountReferrals < ActiveRecord::Migration
  def change
    create_table :account_referrals do |t|
    t.integer :referral_id
    t.integer :referrer_id
    t.float :referral_gift_coins
    t.float :referrer_gift_coins

    t.timestamps null: false
    end
  end
end
