class ReferralGiftAmount < ActiveRecord::Base
	mount_uploader :referrer_gift_image, ReferrerGiftImageUploader
	mount_uploader :referral_gift_image, ReferralGiftImageUploader

	has_many :account_referral_amounts,:dependent => :destroy
  	has_many :account_referrals, through: :account_referral_amounts

  	def referrer_gift_image_url
  		referrer_gift_image.url
  	end

  	def referral_gift_image_url
  		referral_gift_image.url
  	end
end
# 