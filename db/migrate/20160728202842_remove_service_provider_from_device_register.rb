class RemoveServiceProviderFromDeviceRegister < ActiveRecord::Migration
  def change
    remove_column :device_registers, :service_provider
  end
end
