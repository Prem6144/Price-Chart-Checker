class AlterDealAttributes < ActiveRecord::Migration
  def change
    add_column :cellphone_equipments, :available_colors, :text
    add_column :cable_deal_attributes, :description, :text
    add_column :bundle_deal_attributes, :description, :text
    add_column :cellphone_deal_attributes, :description, :text
    add_column :internet_deal_attributes, :description, :text
    add_column :telephone_deal_attributes, :description, :text
  end
end
