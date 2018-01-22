class CreateTrendingDeals < ActiveRecord::Migration
  def change
    create_table :trending_deals do |t|
    	t.belongs_to :deal, index: true
    	t.integer :subscription_count
    	t.integer :category_id
      t.timestamps null: false
    end
    add_foreign_key :trending_deals, :deals
  end
end
