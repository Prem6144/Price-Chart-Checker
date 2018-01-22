namespace :reminder_notification do
  desc "Send email to users where contract is going to expire"
  task send_notification: :environment do
    @app_users = AppUser.joins(:notification).where("recieve_notification = ?", true).to_a
    @app_users.each do |app_user|
      @app_user_notification_day = app_user.notification.day
      @set_preferences = app_user.service_preferences
      @set_preferences.each do |sp|
        @service_category = sp.service_category.name
        @app_user_contract_date = sp.end_date
        if @app_user_contract_date.present? && @app_user_notification_day.present?
          @remaining_days = (@app_user_contract_date.to_datetime - DateTime.now).to_i
          if @remaining_days < @app_user_notification_day
            DealNotifier.send_best_deal_contract(app_user,sp).deliver_now
            puts "#{app_user.id} Email sent successfully"

            # if app_user.device_flag == "android"
            #   gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
            #   registration_id = ["#{device_flag.gcm_id}"]
            #   gcm.send(registration_id, {data: {message: "Notification from Service Deals. #{@service_category} plan is going to expire soon. Please renew it on time."}})
            # elsif app_user.device_flag == "iphone"
            #   pusher = Grocer.pusher(
            #     certificate: "#{Rails.root}/public/certificates/dev_certificate.pem",       # required
            #     passphrase:  "1234",                                                        # optional
            #     gateway:     "gateway.sandbox.push.apple.com",                              # optional; See note below.
            #     port:        2195,                                                          # optional
            #     retries:     3                                                              # optional
            #   )
            #   notification = Grocer::Notification.new(
            #     device_token:      "#{app_user.gcm_id}",
            #     alert:             "Notification from Service Deals. #{@service_category} plan is going to expire soon. Please renew it on time.",
            #     badge:             42
            #     #category:          "a category",                                           # optional; used for custom notification actions
            #     #sound:             "siren.aiff",                                           # optional
            #     #expiry:            Time.now + 60*60,                                       # optional; 0 is default, meaning the message is not stored
            #     #identifier:        1234,                                                   # optional; must be an integer
            #     #content_available: true                                                    # optional; any truthy value will set 'content-available' to 1
            #   )
            #   pusher.push(notification)
            #end    
          end
        end
      end  
    end  
  end

  #desc "TODO"
  #task test_task: :environment do
  #	gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
  #	registration_id = ["APA91bEUvak1n5xH22h3l15BiqgyCHnHMehER7e1IyJ4R6sgkK-3ZKSfa7E9SRGVGWf6-dWbLKxOhXlHgWpeDGCGykbZ4T5SrLcsTI4AEgWDGbJwvDn0siTsuegi0nugkjpmfMF64ZBs"]
  #	gcm.send(registration_id, {data: {message: "Rake Task test notification"}})
  #	puts "OK"
  #end

end
