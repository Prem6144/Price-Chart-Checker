class CreateBundleServicePreferences < ActiveRecord::Migration
  def change
    create_table :bundle_service_preferences do |t|
      t.belongs_to :service_preference, index: true
      t.string :upload_speed
      t.string :download_speed
      t.string :data
      t.integer :free_channels
      t.integer :premium_channels
      t.integer :domestic_call_minutes
      t.integer :international_call_minutes
      t.float :data_plan
      t.float :data_speed
      t.boolean :domestic_call_unlimited, default: false
      t.boolean :international_call_unlimited, default: false
      t.float :upload_speed
      t.float :download_speed
      t.float :data
      t.string :bundle_combo
      t.timestamps null: false
    end
    add_foreign_key :bundle_service_preferences, :service_preferences
  end
end
