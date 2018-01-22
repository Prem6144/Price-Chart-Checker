class CreateServicePreferences < ActiveRecord::Migration
  def change
    create_table :service_preferences do |t|
      t.belongs_to :app_user, index: true
      t.belongs_to :service_category, index: true
      t.belongs_to :service_provider, index: true
      t.string :service_category_name
      t.string :service_provider_name
      t.float  :price
      t.boolean :is_contract
      t.datetime :start_date
      t.datetime :end_date
      t.string :plan_name
      
      
      t.timestamps null: false
    end
    add_foreign_key :service_preferences, :app_users
    add_foreign_key :service_preferences, :service_categories
  end
end