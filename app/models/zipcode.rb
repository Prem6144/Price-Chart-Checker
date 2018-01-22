class Zipcode < ActiveRecord::Base
	validates_uniqueness_of :code
	has_and_belongs_to_many :deals
	has_and_belongs_to_many :additional_offers

	def as_json(opts={})
    json = super(opts)
    Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  end

  def self.import(file)
    CSV.foreach(file.path, headers: true) do |row|
      zipcode_hash = row.to_hash # exclude the price field
      zipcode = Zipcode.where(id: zipcode_hash["id"])
      zipcode_code = Zipcode.where(code: zipcode_hash["code"])
      if zipcode.count == 1
        zipcode.first.update_attributes(zipcode_hash)
      elsif zipcode_code.first.present?
        zipcode_code.first.update_attributes(zipcode_hash)
      else
        Zipcode.create!(zipcode_hash)
      end # end if !zipcode.nil?
    end # end CSV.foreach
  end # end self.import(file)
end
