class AddServiceProviderIdToChannelPackages < ActiveRecord::Migration
  def change
	add_column :channel_packages, :service_provider_id, :integer	
  end
end
