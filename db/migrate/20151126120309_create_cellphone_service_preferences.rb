class CreateCellphoneServicePreferences < ActiveRecord::Migration
  def change
    create_table :cellphone_service_preferences do |t|
      t.belongs_to :service_preference, index: true
      t.integer :domestic_call_minutes
      t.integer :international_call_minutes
      t.float :data_plan
      t.float :data_speed
      t.boolean :domestic_call_unlimited, default: false
      t.boolean :international_call_unlimited, default: false
      t.timestamps null: false
    end
    add_foreign_key :cellphone_service_preferences, :service_preferences
  end
end
