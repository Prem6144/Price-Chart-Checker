class GiftsController < ApplicationController
	def index
    @gifts = Gift.paginate(:page => params[:page], :per_page => 20).order("id ASC")
	end
	def new
    @gift = Gift.new
	end
	def edit
     @gift = Gift.find(params[:id])
	end
	def create
     @gift = Gift.new(gift_params)    
    respond_to do |format|
      if @gift.save
        format.html { redirect_to gifts_path, :notice => 'You have successfully created a gift' }
        format.xml  { render :xml => @gift, :status => :created, :gift => @gift }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @gift.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @gift = Gift.find(params[:id])
    respond_to do |format|
      if @gift.update(gift_params)
        format.html { redirect_to gifts_path, notice: 'You have successfully updated a Gift.' }
        format.xml  { render :xml => @gift, :status => :created, :gift => @gift }
      else
        format.html { render :edit }
        format.json { render json: @gift.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @gift = Gift.find(params[:id])
    respond_to do |format|
      if @gift.destroy
        format.html { redirect_to gifts_path, :notice => 'You have successfully removed a gift' }
        format.xml  { render :xml => @gift, :status => :created, :gift=> @gift }
      end
    end
    	
   end

	private
  def gift_params
   params.require(:gift).permit(:name, :description, :amount, :is_active, :activation_count_condition,:image)
  end
end