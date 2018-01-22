# This file should contain all the record creation needed to seed the database with its default values.
# The data can then be loaded with the rake db:seed (or created alongside the db with db:setup).
#
# Examples:
#
#   cities = City.create([{ name: 'Chicago' }, { name: 'Copenhagen' }])
#   Mayor.create(name: 'Emanuel', city: cities.first)

user = User.create(:email=>'jay@sp-assurance.com',:password=>'111111',:password_confirmation=>'111111',:name=>'Jay Pandey')
user.role = 'super_admin'
user.save
user = User.create(:email=>'admin@example.com',:password=>'111111',:password_confirmation=>'111111',:name=>'Admin User')
user.role = 'super_admin'
user.save
user = User.create(:email=>'service@example.com',:password=>'111111',:password_confirmation=>'111111',:name=>'Service Provider')
user.role = 'service_provider'
user.save
BandwidthCalculatorSetting.create!(:email=>300,:web_page=>1000, :video_calling=>2048, :audio_calling=>512, :photo_upload_download=>250,  :video_streaming=>1024)
ServiceDealConfig.create!(:config_key=>"show_deals_logo", :config_value=>false)
ServiceDealConfig.create!(:config_key=>"show_deals_name", :config_value=>false)
ServiceDealConfig.create(:config_key=>"show_website_filter",:config_value=>false)