class CreateAdvertisements < ActiveRecord::Migration
	def change
		create_table :advertisements do |t|
			t.belongs_to :service_category, index: true
			t.string :service_category_name
			t.string :name
			t.string :url
			t.string :image
			t.boolean :status, default: true
			t.datetime :start_date
			t.datetime :end_date
			t.timestamps null: false
		end
		add_foreign_key :advertisements, :service_categories
	end
end

