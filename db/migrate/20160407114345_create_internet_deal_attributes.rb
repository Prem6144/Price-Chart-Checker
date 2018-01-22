class CreateInternetDealAttributes < ActiveRecord::Migration
  def change
    create_table :internet_deal_attributes do |t|
    	t.integer   :deal_id
        t.float	    :download
    	t.float 	:upload
    	t.float 	:data
    	t.string 	:email
    	t.string 	:online_storage
    	t.text 		:wifi_hotspot_networks
        t.boolean   :static_ip
    	t.timestamps null: false
    end
    add_index :internet_deal_attributes, :deal_id
  end
end
