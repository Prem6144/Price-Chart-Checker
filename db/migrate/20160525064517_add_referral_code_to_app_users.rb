class AddReferralCodeToAppUsers < ActiveRecord::Migration
  def change
  	add_column :app_users, :referral_code, :string, default: ""
  end
end
