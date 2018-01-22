class Gift < ActiveRecord::Base
	has_many :user_gifts,:dependent => :destroy
  	has_many :orders, through: :user_gifts

  	validates_presence_of :name,:amount, :is_active, :activation_count_condition
  	validates_uniqueness_of :activation_count_condition

  	mount_uploader :image, ImageUploader

  	def gift_image_url
		image.url
	end
end
