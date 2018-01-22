class AddServiceProviderIdToChannel < ActiveRecord::Migration
  def change
    add_column :channels, :service_provider_id, :integer
  end
end
