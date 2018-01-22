class AlterChannelPackageAndAttributeTable < ActiveRecord::Migration
  def change
    # add_column :channel_packages, :service_provider_id, :integer
    add_column :channel_packages, :price, :decimal , precision: 5, scale: 2
    add_column :cellphone_deal_attributes, :title, :string
    add_column :cable_deal_attributes, :title, :string
  end
end
