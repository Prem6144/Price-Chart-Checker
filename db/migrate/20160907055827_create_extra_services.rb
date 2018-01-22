class CreateExtraServices < ActiveRecord::Migration
  def change
    create_table :extra_services do |t|
      t.string :service_name
      t.integer :service_category_id
      t.boolean :status
      t.text :service_description

      t.timestamps null: false
    end
  end
end
