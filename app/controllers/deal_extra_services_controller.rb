class DealExtraServicesController < ApplicationController
	def index
    @deal_extra_services = DealExtraService.paginate(:page => params[:page], :per_page => 20).order("id ASC")
	end
	def new
    @deal_extra_service = DealExtraService.new
	end
	def edit
     @deal_extra_service = DealExtraService.find(params[:id])
	end
	def create
     @deal_extra_service = DealExtraService.new(deal_extra_services_params)    
    respond_to do |format|
      if @deal_extra_service.save
        format.html { redirect_to deal_extra_services_path, :notice => 'You have successfully created a Deal Extra Service' }
        format.xml  { render :xml => @deal_extra_service, :status => :created, :deal_extra_service => @deal_extra_service }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @deal_extra_service.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @deal_extra_service = DealExtraService.find(params[:id])
    respond_to do |format|
      if @deal_extra_service.update(deal_extra_services_params)
        format.html { redirect_to deal_extra_services_path, notice: 'You have successfully updated a DealExtraService.' }
        format.xml  { render :xml => @deal_extra_service, :status => :created, :deal_extra_service => @deal_extra_service }
      else
        format.html { render :edit }
        format.json { render json: @deal_extra_service.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @deal_extra_service = DealExtraService.find(params[:id])
    respond_to do |format|
      if @deal_extra_service.destroy
        format.html { redirect_to deal_extra_services_path, :notice => 'You have successfully removed a DealExtraService' }
        format.xml  { render :xml => @deal_extra_service, :status => :created, :deal_extra_service=> @deal_extra_service }
      end
    end
    	
   end

	private
  def deal_extra_services_params
   params.require(:deal_extra_service).permit(:extra_service_id,:deal_id,:price,:status,:service_term)
  end
end

