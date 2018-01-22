class AddOfferContractToAdditionalOffer < ActiveRecord::Migration
  def change
  	add_column :additional_offers, :contract_period, :string
  end
end
