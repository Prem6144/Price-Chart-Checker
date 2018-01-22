class DealNotifier < ApplicationMailer
  default from: "abc@abc.com"
  include DashboardsHelper

	def send_trending_deal(app_user)
		recipient = app_user.email
    @app_user = app_user
    @deal_type=@app_user.user_type

    @service_preferences = app_user.service_preferences.to_a
    @service_preferences.each do |sp|
      if sp.service_category_id == 1
       	@internet_deal = category_trending_deal(@deal_type,sp.service_category_id,@app_user.zip)
      elsif sp.service_category_id == 2
      	@telephone_deal = category_trending_deal(@deal_type,sp.service_category_id,@app_user.zip)
      elsif sp.service_category_id == 3
      	@cable_deal = category_trending_deal(@deal_type,sp.service_category_id,@app_user.zip)
      elsif sp.service_category_id == 4
      	@cellphone_deal = category_trending_deal(@deal_type,sp.service_category_id,@app_user.zip)
      elsif sp.service_category_id == 5
        @bundle_deal = category_trending_deal(@deal_type,sp.service_category_id,@app_user.zip)
      end
    end
    mail(to: recipient, subject: "Switch to the trending deals in your area and save $100s") rescue nil
	end

  def send_best_deal(app_user,best_deal)
    recipient = app_user.email
    @app_user = app_user
    @best_deal=best_deal
    if @best_deal.present?
      mail(to: recipient, subject: "Best Deals for you") rescue nil
    end
  end

  def send_best_deal_contract(app_user,service_preference)
    recipient = app_user.email
    @app_user = app_user
    @deal_type=@app_user.user_type

    @service_preference=service_preference

    @best_deal=category_best_deal(@deal_type,@service_preference,@app_user.zip,1,true)

    if @best_deal.present?
      mail(to: recipient, subject: "Your #{service_preference.service_category.name} service is expiring on #{service_preference.end_date.to_date}") rescue nil
    end
  end
end
