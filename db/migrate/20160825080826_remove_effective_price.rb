class RemoveEffectivePrice < ActiveRecord::Migration
  def change
    remove_column :deals, :effective_price
  end
end
