class AddChannelIdsToBundleDealAttribute < ActiveRecord::Migration
  def change
    add_column :bundle_deal_attributes, :channel_ids, :text
    
    add_column :bundle_deal_attributes, :channel_package_ids, :text
    
    add_column :bundle_deal_attributes, :channel_count, :integer
   
  end
end
