class AddCellphoneDetailIdInCellphoneEquipment < ActiveRecord::Migration
  def change
    add_column :cellphone_equipments, :cellphone_detail_id, :integer
  end
end
