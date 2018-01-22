class CreateCellphoneDealAttributes < ActiveRecord::Migration
  def change
    create_table :cellphone_deal_attributes do |t|
      t.integer  :deal_id
      t.integer  :no_of_lines
      t.decimal  :price_per_line, null: false, precision: 5, scale: 2, default: 0
      t.string   :domestic_call_minutes
      t.string	 :domestic_text
      t.string   :international_call_minutes
      t.string   :international_text
      t.float	   :data_plan
      t.decimal  :data_plan_price, null: false, precision: 5, scale: 2, default: 0
      t.float    :additional_data
      t.decimal  :additional_data_price, null: false, precision: 5, scale: 2, default: 0
      t.boolean  :rollover_data, default: false
      
      t.timestamps null: false
    end
    add_index :cellphone_deal_attributes, :deal_id
  end
end