class CreateBundleDealAttributes < ActiveRecord::Migration
  def change
    create_table :bundle_deal_attributes do |t|
      	t.integer   :deal_id
        t.string    :bundle_combo
      	t.float	    :download
      	t.float 	  :upload
        t.float     :data
      	t.boolean   :static_ip
      	t.string    :domestic_call_minutes
      	t.string    :international_call_minutes
      	t.integer   :free_channels
      	t.text 		  :free_channels_list
      	t.integer   :premium_channels
      	t.text 		  :premium_channels_list
      	t.integer   :hd_channels
      	t.text 		  :hd_channels_list
      	t.timestamps null: false
    end
  end
end
