class CreateChannels < ActiveRecord::Migration
  def change
    create_table :channels do |t|
      t.string :category_name
      t.string :channel_name
      t.string :channel_code
      t.string :channel_type, :default => 'normal'
      t.text :description
      t.boolean :is_hd, :default => false
      t.string :image
      t.boolean :status, :default => true
      t.timestamps null: false
    end
  end
end