class CreateCableServicePreferences < ActiveRecord::Migration
  def change
    create_table :cable_service_preferences do |t|
    	t.belongs_to :service_preference, index: true
    	t.integer :free_channels
      t.integer :premium_channels
      t.timestamps null: false
    end
    add_foreign_key :cable_service_preferences, :service_preferences
  end
end
