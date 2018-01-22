class CreateDeviceTrackers < ActiveRecord::Migration
  def change
    create_table :device_trackers do |t|
      t.text :device_id
      t.text :service_provider

      t.timestamps null: false
    end
  end
end
