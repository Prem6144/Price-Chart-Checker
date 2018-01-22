class AlterCellphoneAttributesAddEffectivePrice < ActiveRecord::Migration
  def change
    add_column :cellphone_deal_attributes, :effective_price, :decimal,:default => 0.00
  end
end
