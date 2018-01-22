class CreateDealExtraServices < ActiveRecord::Migration
  def change
    create_table :deal_extra_services do |t|
      t.integer :extra_service_id
      t.integer :deal_id
      t.decimal :price, precision: 5, scale: 2
      t.boolean :status
      t.integer :service_term

      t.timestamps null: false
    end
  end
end
