class ExtraServicesController < ApplicationController
  def index
    @extra_services = ExtraService.paginate(:page => params[:page], :per_page => 20).order("id ASC")
  end
  def new
    @extra_service = ExtraService.new
  end
  def edit
     @extra_service = ExtraService.find(params[:id])
  end
  def create
     @extra_service = ExtraService.new(extra_service_params)    
    respond_to do |format|
      if @extra_service.save
        format.html { redirect_to extra_services_path, :notice => 'You have successfully created a extra_service' }
        format.xml  { render :xml => @extra_service, :status => :created, :extra_service => @extra_service }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @extra_service.errors, :status => :unprocessable_entity }
      end
    end
  
  end

  def update
    @extra_service = ExtraService.find(params[:id])
    respond_to do |format|
      if @extra_service.update(extra_service_params)
        format.html { redirect_to extra_services_path, notice: 'You have successfully updated a extra_service.' }
        format.xml  { render :xml => @extra_service, :status => :created, :extra_service => @extra_service }
      else
        format.html { render :edit }
        format.json { render json: @extra_service.errors, status: :unprocessable_entity }
      end
    end
    
    end
    def destroy
      @extra_service = ExtraService.find(params[:id])
    respond_to do |format|
      if @extra_service.destroy
        format.html { redirect_to extra_services_path, :notice => 'You have successfully removed a extra_service' }
        format.xml  { render :xml => @extra_service, :status => :created, :extra_service=> @extra_service }
      end
    end
      
   end

   def extra_service_deal
    if params[:id].present?
      service_category_id =  ExtraService.find(params[:id]).service_category_id
      deals = Deal.select("id, title").where(service_category_id: service_category_id) if service_category_id.present?
       render :json=>{
          :deals=>deals
        }
      end
   end

  private
  def extra_service_params
   params.require(:extra_service).permit( :service_name, :service_category_id, :status, :service_description)
  end
end


