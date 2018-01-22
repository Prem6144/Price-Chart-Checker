class CreateDeviceRegisters < ActiveRecord::Migration
  def change
    create_table :device_registers do |t|
      t.text :imei
      t.text :device_id
      t.text :service_provider

      t.timestamps null: false
    end
  end
end
