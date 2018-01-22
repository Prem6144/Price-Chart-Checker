class CreateRewards < ActiveRecord::Migration
  def change
    create_table :rewards do |t|
      t.string :reward_name
      t.integer :reward_value
      t.string :image
      t.boolean :is_active, default: false
      t.string :device_platform
      t.string :reward_display_type
      t.string :reward_display_on

      t.timestamps null: false
    end
  end
end
