class ReferContactDetail < ActiveRecord::Base
  belongs_to :app_user

  def self.create_referred_users(params)
    params.each_with_index do |value, index|
      referred_contacts = self.find_or_create_by(email_id: params[index][:email_id], mobile_no: params[index][:mobile_no])
   end
  end
end
