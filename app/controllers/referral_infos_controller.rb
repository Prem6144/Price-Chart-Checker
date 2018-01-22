class ReferralInfosController < ApplicationController
	def index
		@referral_infos = ReferralInfo.all
	end
end