#env :PATH, ENV['PATH']
# Use this file to easily define all of your cron jobs.
#
# It's helpful, but not entirely necessary to understand cron before proceeding.
# http://en.wikipedia.org/wiki/Cron

# Example:
#
# set :output, "/path/to/my/cron_log.log"
#set :output, 'log/cron.log'
set :environment, "production"

set :output, {:error => "#{path}/log/cron_error_log.log", :standard => "#{path}/log/cron_log.log"}

# every 4.hours do
	# rake "send_trending_deals:email_trending_deals"
# end

every :day, at: '10pm' do
	rake "send_trending_deals_daily:email_trending_deals_daily"
end

every Date.today.tuesday?, :at => "11:00pm" do
	rake "send_trending_deals_weekly:email_trending_deals_weekly"
end

every (Date.today.tuesday?) || (Date.today.thursday?), :at => "11:00pm" do
	rake "send_trending_deals_weekly:email_trending_deals_bi_weekly"
end

every 4.hours do
	rake "reminder_notification:send_notification"
end	

#every 5.minutes do
#	rake "send_trending_deals:test_task"
#end	
#every 5.minutes do
#	command 'echo "hello"'
#end	
#
# every 2.hours do
#   command "/usr/bin/some_great_command"
#   runner "MyModel.some_method"
#   rake "some:great:rake:task"
# end
#
# every 4.days do
#   runner "AnotherModel.prune_old_records"
# end

# Learn more: 

#every :day, :at => '3pm' do
#	rake "reminder_notification:send_notification"
#end	

#every :day, :at => '7:30pm' do

