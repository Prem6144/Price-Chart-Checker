class AddCellphoneDetailIdToCellphoneServicePreference < ActiveRecord::Migration
  def change
  	add_column :cellphone_service_preferences, :cellphone_detail_id, :integer
  end
end
