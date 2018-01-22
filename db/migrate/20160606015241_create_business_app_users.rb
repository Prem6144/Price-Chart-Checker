class CreateBusinessAppUsers < ActiveRecord::Migration
  def change
    create_table :business_app_users do |t|
      t.integer :business_id
      t.integer :app_user_id
      t.string :role
    end
  end
end