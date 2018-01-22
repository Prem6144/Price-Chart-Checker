class AddDeviceTypeToDeviceRegister < ActiveRecord::Migration
  def change
  	add_column :device_registers, :device_type, :string
  end
end
