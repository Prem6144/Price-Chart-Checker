module Encrypt
  def encode_data(data_hash)
    data = data_hash['data']
    encoded_data = Base64.encode64(data)
    encoded_data
  end
end