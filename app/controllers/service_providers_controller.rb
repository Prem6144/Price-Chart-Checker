class ServiceProvidersController < ApplicationController
	def index
		@service_providers = ServiceProvider.paginate(:page => params[:page], :per_page => 20)
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << 
          [ "ID", 
            "ServiceCategory ID",              
            "Name", 
            "Address",
            "State",
            "City",
            "Zip",
            "Email",
            "Telephone",
            "Logo",
            "Is Preferred",
            "Is Active",
            "Created At",
            "Updated At",           
          ]  

          # data rows
          ServiceProvider.all.order("id ASC").each do |sp|
            csv << 
            [ sp.id,  
              sp.service_category_id,
              sp.name,            
              sp.address,
              sp.state,
              sp.city,
              sp.zip,
              sp.email,
              sp.telephone,
              sp.logo.url,
              sp.is_preferred,
              sp.is_active,
              sp.created_at,
              sp.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=service_providers.csv" 
      }
    end
	end
	def new
		@service_provider = ServiceProvider.new
	end
	def show
		@service_provider = ServiceProvider.find(params[:id])
	end
	def edit
		@service_provider = ServiceProvider.find(params[:id])
	end
	def create
		@service_provider = ServiceProvider.new(service_provider_params)    
    	respond_to do |format|
      		if @service_provider.save
        		format.html { redirect_to service_providers_path, :notice => 'You have successfully created a service provider' }
        		format.xml  { render :xml => @service_provider, :status => :created, :service_category => @service_provider }
      		else
        		format.html { render :action => "new" }
        		format.xml  { render :xml => @service_provider.errors, :status => :unprocessable_entity }
      		end
    	end
	end
	def update
		@service_provider = ServiceProvider.find(params[:id])
    	respond_to do |format|
      		if @service_provider.update(service_provider_params)
        		format.html { redirect_to service_providers_path, notice: 'You have successfully updated a Service Provider.' }
        		format.xml  { render :xml => @service_provider, :status => :created, :service_provider => @service_provider }
      		else
        		format.html { render :edit }
        		format.json { render json: @service_provider.errors, status: :unprocessable_entity }
      		end
    	end
	end
	def destroy
		@service_provider = ServiceProvider.find(params[:id])
    	respond_to do |format|
          if @service_provider.destroy
            format.html { redirect_to service_providers_path, :notice => 'You have successfully removed a service provider' }
            format.xml  { render :xml => @service_provider, :status => :created, :service_provider => @service_provider }
          end
    	end	
	end
  def import
    ServiceProvider.import(params[:file])
    redirect_to service_providers_path, notice: "Service Providers imported."
  end

	private
  	def service_provider_params
  		params.require(:service_provider).permit(:name, :service_category_id, :address, :state, :city, :zip, :email, :telephone, :logo, :is_preferred, :is_active)
  	end
end	