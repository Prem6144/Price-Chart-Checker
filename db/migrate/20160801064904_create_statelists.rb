class CreateStatelists < ActiveRecord::Migration
  def change
    create_table :statelists do |t|
      t.text :state
      t.text :city
      t.text :county

      t.timestamps null: false
    end
  end
end
