class CreateJoinTableAdditionalOfferZipcode < ActiveRecord::Migration
  def change
    create_join_table :additional_offers, :zipcodes do |t|
      # t.index [:additional_offer_id, :zipcode_id]
      # t.index [:zipcode_id, :additional_offer_id]
    end
  end
end
