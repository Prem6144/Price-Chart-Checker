class CreateChannelPackages < ActiveRecord::Migration
  def change
    create_table :channel_packages do |t|
      t.string :package_name
      t.string :package_code
      t.integer :channel_count
      t.text :channel_ids
      t.text :description
      t.string :image
      t.boolean :status, :default => true
      t.timestamps null: false
    end
  end
end