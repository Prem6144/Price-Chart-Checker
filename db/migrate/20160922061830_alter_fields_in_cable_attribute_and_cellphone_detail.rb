class AlterFieldsInCableAttributeAndCellphoneDetail < ActiveRecord::Migration
  def change
  	 add_column  :cable_equipments, :description,:text
  	 add_column  :cellphone_details, :image,:string
  	 add_column  :equipment_colors, :color_code,:string
  	 add_column  :equipment_colors, :image,:string
  end
end
