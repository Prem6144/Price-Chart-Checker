class Advertisement < ActiveRecord::Base
	belongs_to :service_category

	mount_uploader :image, ImageUploader

	def advertisement_image_url
  		image.url
  	end

end
