class CreateServiceProviderChecklists < ActiveRecord::Migration
  def self.up
    create_table :service_provider_checklists do |t|
      t.integer :checklist_id
      t.integer :service_provider_id
      t.integer :service_category_id
      t.boolean :is_mandatory
      t.integer :status
    end
  end

  def self.down
    drop_table :provider_checklists
  end
end
