class CreateGifts < ActiveRecord::Migration
  def change
    create_table :gifts do |t|
      t.string :name
      t.string :description
      t.float :amount
      t.integer :activation_count_condition, default: 0
      t.boolean :is_active, default: true
      
      t.timestamps null: false
    end
  end
end
