class AddIsCustomizableInDeals < ActiveRecord::Migration
  def change
    add_column :deals, :is_customisable, :boolean, default: false
  end
end
