class ServiceCategoriesController < ApplicationController
	def index
		@service_categories = ServiceCategory.all.order("id ASC")
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << 
          [ "id",              
            "name",             
            "description",  
            "created_at",
            "updated_at",           
          ]  

          # data rows
          @service_categories.each do |sc|
            csv << 
            [ sc.id,              
              sc.name,
              sc.description,
              sc.created_at,
              sc.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=service_categories.csv" 
      }
    end
	end

	def new
		@service_category = ServiceCategory.new
	end

  def show
    @service_category = ServiceCategory.find(params[:id])
  end

	def create
    @service_category = ServiceCategory.new(service_category_params)    
    respond_to do |format|
      if @service_category.save
        format.html { redirect_to service_categories_path, :notice => 'You have successfully created a service category' }
        format.xml  { render :xml => @service_category, :status => :created, :service_category => @service_category }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @service_category.errors, :status => :unprocessable_entity }
      end
    end
  end

  def edit
    @service_category = ServiceCategory.find(params[:id])
  end

  def update
    @service_category = ServiceCategory.find(params[:id])
    respond_to do |format|
      if @service_category.update(service_category_params)
        format.html { redirect_to service_categories_path, notice: 'You have successfully updated a Service.' }
        format.xml  { render :xml => @service_category, :status => :created, :service_category => @service_category }
      else
        format.html { render :edit }
        format.json { render json: @service_category.errors, status: :unprocessable_entity }
      end
    end
  end

  def destroy
    @service_category = ServiceCategory.find(params[:id])
    respond_to do |format|
      if @service_category.destroy
        format.html { redirect_to service_categories_path, :notice => 'You have successfully removed a service category' }
        format.xml  { render :xml => @service_category, :status => :created, :service_category=> @service_category }
      end
    end
  end

  def import
    ServiceCategory.import(params[:file])
    redirect_to service_categories_path, notice: "Successfully imported."
  end

  private
  def service_category_params
  	params.require(:service_category).permit(:name,:description)
  end

end