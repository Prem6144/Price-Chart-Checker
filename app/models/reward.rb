class Reward < ActiveRecord::Base

	REWARD_DISPLAY_TYPES = {'Popup Image' => 'popup_image', 'Top Banner' => 'top_banner'}
	DEVICE_PLATFORM = {'IOS' => 'ios', 'Android' => 'android', 'Web' => 'web'}
	IS_ACTIVE = {'Yes' => true, 'NO' => false}

	def self.search(params)
  	conditions = []
  	conditions << "reward_name like '%#{params[:reward_name]}%'" if params[:reward_name].present?
  	conditions << "reward_value = '#{params[:reward_value]}'" if params[:reward_value].present?
  	condition = conditions.join(' and ')
  	self.where(condition).order("ID DESC")
  end
end
