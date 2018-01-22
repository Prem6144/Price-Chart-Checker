class AccountReferral < ActiveRecord::Base
	belongs_to :referral, class_name: "AppUser"
	belongs_to :referrer, class_name: "AppUser"

	has_one :account_referral_amount,:dependent => :destroy
  	has_one :referral_gift_amount, through: :account_referral_amount

	
 
	validates_uniqueness_of :referrer_id
	
	def as_json(options = {})
    json = super(options)
    json['refer_message'] = refer_message(options[:param_for_message])
    json['refer_image'] = refer_image(options[:param_for_image])

    json
	end

	def refer_message(user_id)
		if self.referrer_id == user_id.to_i
			return "You have used your one time referral."
		elsif self.referral_id == user_id.to_i
			referrer = AppUser.find_by_id(self.referrer_id)
			referrer_name = Base64.decode64(referrer.first_name)
			return "for referring #{referrer_name.capitalize}."
			# You earned $10 as #{referrer.first_name.capitalize} joins ServiceDeals.
		end
	end

	def refer_image(user_id)
		if self.referrer_id == user_id.to_i
			referImage = self.referral_gift_amount.referrer_gift_image.url
			return referImage
		elsif self.referral_id == user_id.to_i
			# referrer = AppUser.find_by_id(self.referrer_id)
			referImage = self.referral_gift_amount.referral_gift_image.url
			return referImage
			# You earned $10 as #{referrer.first_name.capitalize} joins ServiceDeals.
		end

	end
end
