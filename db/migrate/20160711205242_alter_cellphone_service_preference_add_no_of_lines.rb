class AlterCellphoneServicePreferenceAddNoOfLines < ActiveRecord::Migration
  def change
    add_column :cellphone_service_preferences, :no_of_lines, :integer
  end
end
