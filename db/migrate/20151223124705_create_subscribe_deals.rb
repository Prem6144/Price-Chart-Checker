class CreateSubscribeDeals < ActiveRecord::Migration
	def change
    create_table :subscribe_deals do |t|
    	t.belongs_to :app_user, index: true
    	t.belongs_to :deal, index: true
    	t.boolean :active_status, default: false
      t.integer :category_id
      t.timestamps null: false
    end
    add_foreign_key :subscribe_deals, :app_users
    add_foreign_key :subscribe_deals, :deals
  end
end
