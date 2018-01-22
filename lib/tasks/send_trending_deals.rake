namespace :send_trending_deals_weekly do
  desc "TODO"
  task email_trending_deals_weekly: :environment do
    @app_users = AppUser.joins(:notification).where("recieve_trending_deals = ?", true).to_a
    @app_users.each do |app_user|
      if app_user.notification.trending_deal_frequency == "Weekly"
        DealNotifier.send_trending_deal(app_user).deliver_now
        puts "#{app_user.id} Email sent successfully"
      end
      #@app_user_device = app_user.device_flag
      #@state = app_user.state
      #@service_preferences = app_user.service_preferences.to_a

      #@service_preferences.each do |sp|
      #  byebug
      #  if sp.service_category_id == 1
      #    @internet_t_deal = TrendingDeal.where("category_id = ?", sp.service_category_id).order("subscription_count DESC").first
      #  elsif sp.service_category_id == 2
      #    @telephone_t_deal = TrendingDeal.where("category_id = ?", sp.service_category_id).order("subscription_count DESC").first
      #  end  
      #end  
    # end  
  end
 end

  desc "Test Task"
  task test_task: :environment do
    puts "Task Executed Successfully !!"
  end  
end  

namespace :send_trending_deals_bi_weekly do
  desc "TODO"
  task email_trending_deals_bi_weekly: :environment do
    @app_users = AppUser.joins(:notification).where("recieve_trending_deals = ?", true).to_a
    @app_users.each do |app_user|
      if app_user.notification.trending_deal_frequency == "Bi-Weekly"
        DealNotifier.send_trending_deal(app_user).deliver_now
        puts "#{app_user.id} Email sent successfully"
      end
    end
  end  
end

namespace :send_trending_deals_daily do
  desc "TODO"
  task email_trending_deals_daily: :environment do
    @app_users = AppUser.joins(:notification).where("recieve_trending_deals = ?", true).to_a
    @app_users.each do |app_user|
      if app_user.notification.trending_deal_frequency == "Daily"
        DealNotifier.send_trending_deal(app_user).deliver_now
        puts "#{app_user.id} Email sent successfully"
      end
    end
  end 
end 