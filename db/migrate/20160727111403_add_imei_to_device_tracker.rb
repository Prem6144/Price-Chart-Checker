class AddImeiToDeviceTracker < ActiveRecord::Migration
  def change
    add_column :device_trackers, :imei, :text
  end
end
