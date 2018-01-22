class CableServicePreferencesController < ApplicationController
	
	def index
		@cable_service_preferences = CableServicePreference.all
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << 
          [ "ID",   
            "ServicePreference ID", 
            "Free Channels",
            "Premium Channels",
            "Created At",
            "Updated At",         
          ]  

          # data rows
          CableServicePreference.all.order("id ASC").each do |csp|
            csv << 
            [ csp.id, 
              csp.service_preference_id,                 
              csp.free_channels,
              csp.premium_channels,
              csp.created_at,
              csp.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=cable_service_preferences.csv" 
      }
    end
	end

  def destroy
      @cable_service_preference = CableServicePreference.find(params[:id])
      respond_to do |format|
          if @cable_service_preference.destroy
            format.html { redirect_to cable_service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @cable_service_preference, :status => :created, :cable_service_preference => @cable_service_preference }
          end
      end
  end

	#def new
	#	@service_preference = ServicePreference.new
	#end

  #def edit
  #  @service_preference = ServicePreference.find(params[:id])
  #end

	#def create
	#	@service_preference = ServicePreference.new(service_preference_params)   
  #  #raise @service_preference.inspect 
  #  	respond_to do |format|
  #    		if @service_preference.save
  #      		format.html { redirect_to service_preferences_path, :notice => 'You have successfully created a service preference' }
  #      		format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
  #    		else
  #      		format.html { render :action => "new" }
  #      		format.xml  { render :xml => @service_preference.errors, :status => :unprocessable_entity }
  #    		end
  #  	end
	#end

  #def update
  #  @service_preference = ServicePreference.find(params[:id])
  #    respond_to do |format|
  #        if @service_preference.update(service_preference_params)
  #          format.html { redirect_to service_preferences_path, notice: 'You have successfully updated a Service Preference.' }
  #          format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
  #        else
  #          format.html { render :edit }
  #          format.json { render json: @service_preference.errors, status: :unprocessable_entity }
  #        end
  #    end
  #end

	#def destroy
  #  	@service_preference = ServicePreference.find(params[:id])
  #  	respond_to do |format|
  #        if @service_preference.destroy
  #          format.html { redirect_to service_preferences_path, :notice => 'You have successfully removed a service preference' }
  #          format.xml  { render :xml => @service_preference, :status => :created, :service_preference => @service_preference }
  #        end
  #  	end
  #	end

	private
	def cable_service_preference_params
		params.require(:cable_service_preference).permit(:service_preference_id, :free_channels, :premium_channels)
	end
end	