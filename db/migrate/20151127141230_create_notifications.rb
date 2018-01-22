class CreateNotifications < ActiveRecord::Migration
  def change
    create_table :notifications do |t|
	  t.belongs_to :app_user, index: true
      t.boolean :recieve_notification
      t.integer :day
      t.boolean :recieve_trending_deals, default: true
      t.timestamps null: false
    end
    add_foreign_key :notifications, :app_users
  end
end
