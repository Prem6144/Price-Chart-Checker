class Notification < ActiveRecord::Base
	belongs_to :app_user

	# def as_json(opts={})
 #    	json = super(opts)
 #    	Hash[*json.map{|k, v| [k, v || ""]}.flatten]
 #  	end

  def self.create_notification(params,user_id)
		
    if params[:notification].present?
      notification = self.where(app_user_id: user_id).first
      unless notification.present?
        notification = self.new
      else
        notification = notification
      end
      params[:notification].each do |key,value|
        notification[key] = value
      end
      notification.app_user_id = user_id
      if notification.save!
        notification
      end
    end
  end

end
