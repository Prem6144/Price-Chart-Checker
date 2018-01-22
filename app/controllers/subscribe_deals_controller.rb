class SubscribeDealsController < ApplicationController
	def index
		@subscribed_deals = SubscribeDeal.all
	end
	def destroy
		@subscribe_deal = SubscribeDeal.find(params[:id])
    	respond_to do |format|
          if @subscribe_deal.destroy
            format.html { redirect_to subscribe_deals_path, :notice => 'Successfully removed' }
            format.xml  { render :xml => @subscribe_deal, :status => :created, :subscribe_deal => @subscribe_deal }
          end
    	end	
	end
	private
	def subscribe_deal_params
  	params.permit(:app_user_id, :deal_id, :active_status, :category_id)
  end
end	