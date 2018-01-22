class AddIsSponsoredToDeal < ActiveRecord::Migration
  def change
    add_column :deals, :is_sponsored, :boolean, default: false
  end
end
