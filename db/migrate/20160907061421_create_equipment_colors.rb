class CreateEquipmentColors < ActiveRecord::Migration
  def change
    create_table :equipment_colors do |t|
      t.string :color_name
      t.boolean :status

      t.timestamps null: false
    end
  end
end
