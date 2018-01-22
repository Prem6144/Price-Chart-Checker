class Api::V1::SubscribeDealsController < ApplicationController
  skip_before_filter :verify_authenticity_token, :except => [:create]
                     #:if => Proc.new { |c| c.request.format == 'application/json' }
  respond_to :json

  def subscription_info
    if params[:app_user_id].present? && params[:deal_id].present?
      @subscribe_deal = SubscribeDeal.find_by_app_user_id_and_deal_id(params[:app_user_id],params[:deal_id])    
      @deal = Deal.find_by_id(params[:deal_id])
      if @subscribe_deal.present?
        if @subscribe_deal.active_status == true
          render :status => 200,
                 :json => {
                            :success => true
                          } 
        elsif @subscribe_deal.active_status == false
          @subscribe_deal.active_status = true
          @subscribe_deal.save!
          @trending_deal = TrendingDeal.where("deal_id =?", params[:deal_id]).take
          if @trending_deal.present?
            @trending_deal.subscription_count = @trending_deal.subscription_count + 1
            @trending_deal.save!
          else
            @trending_deal = TrendingDeal.new(trending_deal_params)
            @trending_deal.category_id = @deal.service_category_id
            @trending_deal.subscription_count = 1
            @trending_deal.save!
          end
        end
      else
        @subscribe_deal = SubscribeDeal.new(subscribe_deal_params)
        @subscribe_deal.active_status = true
        @subscribe_deal.category_id = @deal.service_category_id
        @subscribe_deal.save!
        @trending_deal = TrendingDeal.where("deal_id =?", params[:deal_id]).take
        if @trending_deal.present?
          @trending_deal.subscription_count = @trending_deal.subscription_count + 1
          @trending_deal.save!
        else
          @trending_deal = TrendingDeal.new(trending_deal_params)
          @trending_deal.category_id = @deal.service_category_id
          @trending_deal.subscription_count = 1
          @trending_deal.save!
        end
        render :status => 200,
                 :json => {
                            :success => true
                          }
      end
    else
      render :status => 401,
             :json => {
                        :success => false
                      }     
    end  
  end

  

  private
	def subscribe_deal_params
  	params.permit(:app_user_id, :deal_id, :active_status)
  end
  def trending_deal_params
  	params.permit(:deal_id, :subscription_count)
  end
end  