class CellphoneDetail < ActiveRecord::Base
	mount_uploader :image, ImageUploader
end
