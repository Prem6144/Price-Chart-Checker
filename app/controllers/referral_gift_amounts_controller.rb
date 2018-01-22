class ReferralGiftAmountsController < ApplicationController
	def index
    @referral_gift_amounts = ReferralGiftAmount.all.order("id ASC")
	end
	def new
    @referral_gift_amount = ReferralGiftAmount.new
	end
	def edit
     @referral_gift_amount = ReferralGiftAmount.find(params[:id])
	end
	def create
     @referral_gift_amount = ReferralGiftAmount.new(referral_gift_amount_params)    
    respond_to do |format|
      if @referral_gift_amount.save
        format.html { redirect_to referral_gift_amounts_path, :notice => 'You have successfully created a referral gift amount' }
        format.xml  { render :xml => @referral_gift_amount, :status => :created, :gift => @referral_gift_amount }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @referral_gift_amount.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @referral_gift_amount = ReferralGiftAmount.find(params[:id])
    respond_to do |format|
      if @referral_gift_amount.update(referral_gift_amount_params)
        format.html { redirect_to referral_gift_amounts_path, notice: 'You have successfully updated a Referral gift amount.' }
        format.xml  { render :xml => @gift, :status => :created, :referral_gift_amount => @referral_gift_amount}
      else
        format.html { render :edit }
        format.json { render json: @referral_gift_amount.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @referral_gift_amount = ReferralGiftAmount.find(params[:id])
    respond_to do |format|
      if @referral_gift_amount.destroy
        format.html { redirect_to referral_gift_amounts_path, :notice => 'You have successfully removed a referral gift amount' }
        format.xml  { render :xml => referral_gift_amount, :status => :created, :referral_gift_amount=> @referral_gift_amount }
      end
    end
    	
   end

	private
  def referral_gift_amount_params
   params.require(:referral_gift_amount).permit(:referrer_amount, :referral_amount, :referrer_gift_image, :referral_gift_image, :is_active )
  end
end

