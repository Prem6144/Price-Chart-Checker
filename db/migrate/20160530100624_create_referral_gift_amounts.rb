class CreateReferralGiftAmounts < ActiveRecord::Migration
  def change
    create_table :referral_gift_amounts do |t|
      t.float :referrer_amount
      t.float :referral_amount
      t.string :referrer_gift_image
      t.string :referral_gift_image
      t.boolean :is_active, default: false

      t.timestamps null: false
    end
  end
end
