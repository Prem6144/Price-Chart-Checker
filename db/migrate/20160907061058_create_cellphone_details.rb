class CreateCellphoneDetails < ActiveRecord::Migration
  def change
    create_table :cellphone_details do |t|
      t.string :cellphone_name
      t.string :brand
      t.text :description
      t.boolean :status

      t.timestamps null: false
    end
  end
end
