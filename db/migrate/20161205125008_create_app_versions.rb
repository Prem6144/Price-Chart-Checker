class CreateAppVersions < ActiveRecord::Migration
  def change
    create_table :app_versions do |t|
      t.string :versn_num
      t.string :device_type
      t.boolean :is_force_upgrade, default: false
      t.boolean :is_normal_upgrade, default: false
      t.string :app_url

      t.timestamps null: false
    end
  end
end
