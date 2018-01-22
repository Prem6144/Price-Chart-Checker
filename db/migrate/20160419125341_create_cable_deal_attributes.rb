class CreateCableDealAttributes < ActiveRecord::Migration
  def change
    create_table :cable_deal_attributes do |t|
        t.integer   :deal_id
        t.integer   :free_channels
      	t.text 		  :free_channels_list
      	t.integer   :premium_channels
      	t.text 		  :premium_channels_list
      	t.integer   :hd_channels
      	t.text 		  :hd_channels_list
      	t.timestamps null: false
    end
    add_index :cable_deal_attributes, :deal_id
  end
end
