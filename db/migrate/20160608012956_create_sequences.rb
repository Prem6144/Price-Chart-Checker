class CreateSequences < ActiveRecord::Migration
  def self.up
    create_table :sequences do |t|
      t.string :seq_type
      t.integer :seq_number
    end
  end

  def self.down
    drop_table :sequences
  end
end
