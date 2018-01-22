class BulkNotification < ActiveRecord::Base
	def as_json(opts={})
    json = super(opts)
    Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  end
end
