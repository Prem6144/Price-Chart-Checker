class AddFreeTextToOrders < ActiveRecord::Migration
  def change
    add_column :orders, :free_text, :text
  end
end
