class CreateBulkNotifications < ActiveRecord::Migration
  def change
    create_table :bulk_notifications do |t|
      t.string :state
      t.string :city
      t.string :zip
      t.text :message
      t.string :category
      t.timestamps null: false
    end
  end
end
