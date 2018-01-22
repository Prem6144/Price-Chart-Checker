class ChangePrecisionInDeals < ActiveRecord::Migration
  def change
    change_column :deals, :effective_price,:decimal, precision: 10, scale: 2
  end
end
