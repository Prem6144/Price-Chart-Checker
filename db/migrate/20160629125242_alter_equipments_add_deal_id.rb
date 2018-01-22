class AlterEquipmentsAddDealId < ActiveRecord::Migration
  def change
    add_column :internet_equipments, :deal_id, :integer
    add_column :cable_equipments, :deal_id, :integer
    add_column :cellphone_equipments, :deal_id, :integer
    add_column :bundle_equipments, :deal_id, :integer
    add_column :telephone_equipments, :deal_id, :integer
  end
end
