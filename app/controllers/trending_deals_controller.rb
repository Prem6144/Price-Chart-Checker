class TrendingDealsController < ApplicationController
	def index
		@trending_deals = TrendingDeal.all
	end
	def destroy
		@trending_deal = TrendingDeal.find(params[:id])
    	respond_to do |format|
          if @trending_deal.destroy
            format.html { redirect_to trending_deals_path, :notice => 'Successfully removed' }
            format.xml  { render :xml => @trending_deal, :status => :created, :trending_deal => @trending_deal }
          end
    	end	
	end
	private
	def trending_deal_params
  	params.permit(:deal_id, :subscription_count, :category_id)
  end
end	