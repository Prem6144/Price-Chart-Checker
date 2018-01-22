class ServicePreferencesController < ApplicationController

	def index
		@service_preferences = ServicePreference.all
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << ["ID",
          "App User ID",
          "ServiceCategory ID",
          "ServiceProvider ID",
          "ServiceCategory Name",
          "ServiceProvider Name",
          "Is Contract",
          "Start Date",
          "End Date",
          "Price",
          "Plan Name",
          "Created At",
          "Updated At",
          ]

          # data rows
          ServicePreference.all.order("id ASC").each do |sp|
            csv <<
            [ sp.id,
              sp.app_user_id,
              sp.service_category_id,
              sp.service_provider_id,
              sp.service_category.name,
              sp.service_provider.name,
              sp.is_contract,
              sp.start_date,
              sp.end_date,
              sp.price,
              sp.plan_name,
              sp.created_at,
              sp.updated_at
            ]
          end
        end
        # send it to the browsah
        send_data csv_string,
          :type => 'text/csv; charset=iso-8859-1; header=present',
          :disposition => "attachment; filename=service_preferences.csv"
      }
    end
	end

	def new
		@service_preference = ServicePreference.new
	end

  def edit
    @service_preference = ServicePreference.find(params[:id])
  end

	def create
		
 		@service_preference = ServicePreference.new(service_preference_params)
    #raise @service_preference.inspect
    	respond_to do |format|
      		if @service_preference.save
        		format.html { redirect_to service_preferences_path, :notice => 'You have successfully created a service preference' }
        		format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
      		else
        		format.html { render :action => "new" }
        		format.xml  { render :xml => @service_preference.errors, :status => :unprocessable_entity }
      		end
    	end

	end

  def update
    @service_preference = ServicePreference.find(params[:id])
      respond_to do |format|
          if @service_preference.update(service_preference_params)
            format.html { redirect_to service_preferences_path, notice: 'You have successfully updated a Service Preference.' }
            format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
          else
            format.html { render :edit }
            format.json { render json: @service_preference.errors, status: :unprocessable_entity }
          end
      end

  end

	def destroy
    	@service_preference = ServicePreference.find(params[:id])
    	respond_to do |format|
          if @service_preference.destroy
            format.html { redirect_to service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
          end
    	end
  	end

	private
	def service_preference_params
		params.require(:service_preference).permit(:app_user_id, :service_category_name, :service_provider_name, :start_date, :end_date, :is_contract, :price, :plan_name)
	end
end
