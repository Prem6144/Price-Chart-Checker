class CellphoneDetailsController < ApplicationController
	def index
    @cellphone_details = CellphoneDetail.paginate(:page => params[:page], :per_page => 20).order("id ASC")
	end
	def new
    @cellphone_detail = CellphoneDetail.new
	end
	def edit
     @cellphone_detail= CellphoneDetail.find(params[:id])
	end
	def create
    @cellphone_detail = CellphoneDetail.new(cellphone_details_params)    
    respond_to do |format|
      if @cellphone_detail.save
        format.html { redirect_to cellphone_details_path, :notice => 'You have successfully created a CellphoneDetail' }
        format.xml  { render :xml => @cellphone_detail, :status => :created, :cellphone_detail => @cellphone_detail }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @cellphone_detail.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @cellphone_detail = CellphoneDetail.find(params[:id])
    respond_to do |format|
      if @cellphone_detail.update(cellphone_details_params)
        format.html { redirect_to cellphone_details_path, notice: 'You have successfully updated a CellphoneDetail.' }
        format.xml  { render :xml => @cellphone_detail, :status => :created, :cellphone_detail=> @cellphone_detail }
      else
        format.html { render :edit }
        format.json { render json: @cellphone_detail.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @cellphone_detail = CellphoneDetail.find(params[:id])
    respond_to do |format|
      if @cellphone_detail.destroy
        format.html { redirect_to cellphone_details_path, :notice => 'You have successfully removed a CellphoneDetail' }
        format.xml  { render :xml => @cellphone_detail, :status => :created, :cellphone_detail=> @cellphone_detail }
      end
    end
    	
   end

	private
  def cellphone_details_params
   params.require(:cellphone_detail).permit(:cellphone_name, :brand, :description, :status,:image)
  end
end
 
 
