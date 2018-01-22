class AddEffectivePrice < ActiveRecord::Migration
  def change
    add_column :deals, :effective_price,:decimal, precision: 5, scale: 2
  end
end
