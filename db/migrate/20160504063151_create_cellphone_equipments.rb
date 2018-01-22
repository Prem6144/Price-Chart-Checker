class CreateCellphoneEquipments < ActiveRecord::Migration
  def change
    create_table :cellphone_equipments do |t|
    	t.integer :cellphone_deal_attribute_id
      t.string  :model
      t.string  :make
      t.integer :memory
      t.decimal :price, precision: 30, scale: 2,default: "0"
      t.text    :installation
      t.string  :activation
      t.string  :offer
      t.boolean :is_active
      
      t.timestamps null: false
    end
  end
end
