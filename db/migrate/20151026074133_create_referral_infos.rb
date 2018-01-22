class CreateReferralInfos < ActiveRecord::Migration
  def change
    create_table :referral_infos do |t|
    	t.string :first_referring_identity
    	t.string :referred_user
    	t.string :event
    	t.integer :referring_user_coins
    	t.integer :referred_user_coins
      t.timestamps null: false
    end
  end
end
