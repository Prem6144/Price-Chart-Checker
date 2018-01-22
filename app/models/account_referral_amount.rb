class AccountReferralAmount < ActiveRecord::Base
	
  belongs_to :account_referral
	belongs_to :referral_gift_amount
end
