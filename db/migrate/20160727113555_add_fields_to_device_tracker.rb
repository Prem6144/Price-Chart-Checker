class AddFieldsToDeviceTracker < ActiveRecord::Migration
  def change
    add_column :device_trackers, :dual_sim, :boolean
    add_column :device_trackers, :country, :text
    add_column :device_trackers, :sim_operator, :text
    add_column :device_trackers, :sim_serial_number, :text
    add_column :device_trackers, :subscriber_id, :text
    add_column :device_trackers, :voice_mail_number, :text
    add_column :device_trackers, :location, :text
    add_column :device_trackers, :device_type, :text
    add_column :device_trackers, :provider_type, :text
    add_column :device_trackers, :roaming, :boolean
  end
end
