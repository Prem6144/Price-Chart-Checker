class AlterCableDealAttributesAddChannelIds < ActiveRecord::Migration
  def change
    add_column :cable_deal_attributes, :channel_ids, :text
    add_column :cable_deal_attributes, :channel_package_ids, :text
    add_column :cable_deal_attributes, :channel_count, :integer
  end
end
