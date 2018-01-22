class CreateServiceProviders < ActiveRecord::Migration
  def change
    create_table :service_providers do |t|
      t.string  :name
      t.belongs_to :service_category, index:true
      t.string  :address
      t.string  :state
      t.string  :city
      t.string  :zip
      t.string  :email
      t.string  :telephone
      t.string  :logo
      t.boolean :is_preferred, default: false
      t.boolean :is_active, default: true
      t.timestamps null: false
    end
    add_foreign_key :service_providers, :service_categories
  end
end
