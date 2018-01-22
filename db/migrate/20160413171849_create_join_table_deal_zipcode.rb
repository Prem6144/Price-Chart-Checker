class CreateJoinTableDealZipcode < ActiveRecord::Migration
  def change
    create_join_table :deals, :zipcodes do |t|
      # t.index [:deal_id, :zipcode_id]
      # t.index [:zipcode_id, :deal_id]
    end
  end
end
