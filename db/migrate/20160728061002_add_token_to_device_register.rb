class AddTokenToDeviceRegister < ActiveRecord::Migration
  def change
    add_column :device_registers, :token, :text
  end
end
