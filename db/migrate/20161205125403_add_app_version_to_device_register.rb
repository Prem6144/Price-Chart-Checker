class AddAppVersionToDeviceRegister < ActiveRecord::Migration
  def change
  	add_column :device_registers, :version, :string
  end
end
