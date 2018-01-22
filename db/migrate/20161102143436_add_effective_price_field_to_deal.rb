class AddEffectivePriceFieldToDeal < ActiveRecord::Migration
  def change
  	 add_column :deals, :effective_price,:decimal, precision: 10, scale: 2
  end
end
