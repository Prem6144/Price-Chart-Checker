class ServiceProviderChecklistsController < ApplicationController
	def index
		@service_provider_checklists = ServiceProviderChecklist.all
   
    end
	def new
		@service_provider_checklist = ServiceProviderChecklist.new
	end
	def show
		@service_provider_checklist = ServiceProviderChecklist.find(params[:id])
	end
	def edit
		@service_provider_checklist = ServiceProviderChecklist.find(params[:id])
	end
	def create
		@service_provider_checklist = ServiceProviderChecklist.new(service_provider_checklist_params)    
    	respond_to do |format|
      		if @service_provider_checklist.save
        		format.html { redirect_to service_provider_checklists_path, :notice => 'You have successfully created a service provider checklist' }
        		format.xml  { render :xml => @service_provider_checklist, :status => :created, :service_provider_checklist => @service_provider_checklist }
      		else
        		format.html { render :action => "new" }
        		format.xml  { render :xml => @service_provider_checklist.errors, :status => :unprocessable_entity }
      		end
    	end
	end
	def update
		@service_provider_checklist = ServiceProviderChecklist.find(params[:id])
    	respond_to do |format|
      		if @service_provider_checklist.update(service_provider_checklist_params)
        		format.html { redirect_to service_provider_checklists_path, notice: 'You have successfully updated a Service Provider Checklist.' }
        		format.xml  { render :xml => @service_provider_checklist, :status => :created, :service_provider => @service_provider_checklist }
      		else
        		format.html { render :edit }
        		format.json { render json: @service_provider_checklist.errors, status: :unprocessable_entity }
      		end
    	end
	end
	def destroy
		@service_provider_checklist = ServiceProviderChecklist.find(params[:id])
    	respond_to do |format|
          if @service_provider_checklist.destroy
            format.html { redirect_to service_provider_checklists_path, :notice => 'You have successfully removed a service provider checklist' }
            format.xml  { render :xml => @service_provider_checklist, :status => :created, :service_provider_checklist => @service_provider_checklist }
          end
    	end	
	end
  def import
    ServiceProviderChecklist.import(params[:file])
    redirect_to service_provider_checklists_path, notice: "Service Provider Checklists imported."
  end

  #  def get_logo
  #   #@ServiceProviders=ServiceProvider.select("id, name").where(service_category_name: params[:category])
  #   @ServiceProviders=ServiceProvider.find_by_id(params[:category]).try(:logo).try(:url)
  #   render :json => @ServiceProviders
  # end

	private
  	def service_provider_checklist_params
params.require(:service_provider_checklist).permit(:checklist_id,:service_provider_id,:service_category_id,:is_mandatory,:status)
  	end
  end

