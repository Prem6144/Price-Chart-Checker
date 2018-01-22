class DeviceRegister < ActiveRecord::Base
   validates :device_id, uniqueness: true
   has_many :device_trackers
end
