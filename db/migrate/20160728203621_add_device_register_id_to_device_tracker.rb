class AddDeviceRegisterIdToDeviceTracker < ActiveRecord::Migration
  def change
    add_column :device_trackers, :device_register_id, :integer
  end
end
