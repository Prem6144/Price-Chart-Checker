class EquipmentColor < ActiveRecord::Base
	mount_uploader :image, ImageUploader
end
