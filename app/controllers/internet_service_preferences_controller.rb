class InternetServicePreferencesController < ApplicationController

	def index
		@internet_service_preferences = InternetServicePreference.all
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv <<
          [ "ID",
            "ServicePreference ID",
            "Upload Speed",
            "Download Speed",
            "Online Storage",
            "Wifi Hotspot",
            "Email",
            "Data",
            "Created At",
            "Updated At",
          ]

          # data rows
          InternetServicePreference.all.order("id ASC").each do |isp|
            csv <<
            [ isp.id,
              isp.service_preference_id,
              isp.upload_speed,
              isp.download_speed,
              isp.online_storage,
              isp.wifi_hotspot,
              isp.email,
              isp.data,
              isp.created_at,
              isp.updated_at
            ]
          end
        end
        # send it to the browser
        send_data csv_string,
          :type => 'text/csv; charset=iso-8859-1; header=present',
          :disposition => "attachment; filename=internet_service_preferences.csv"
      }
    end
	end

  def destroy
      @internet_service_preference = InternetServicePreference.find(params[:id])
      respond_to do |format|
          if @internet_service_preference.destroy
            format.html { redirect_to internet_service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @internet_service_preference, :status => :created, :internet_service_preference => @internet_service_preference }
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
	def internet_service_preference_params
		params.require(:internet_service_preference).permit(:service_preference_id, :upload_speed, :download_speed, :data, :email, :online_storage, :wifi_hotspot)
	end
end
