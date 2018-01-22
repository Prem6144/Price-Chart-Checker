class BundleServicePreferencesController < ApplicationController
	
	def index
		@bundle_service_preferences = BundleServicePreference.all
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << 
          [ "ID",   
            "ServicePreference ID", 
            "Bundle Combo",          
            "Upload Speed",
            "Download Speed",
            "Free Channels",
            "Premium Channels",
            "DomesticCall Minutes",
            "InternationalCall Minutes",
            "DomesticCall Unlimited",
            "InternationalCall Unlimited",
            "Data",
            "Data Speed",
            "Data Plan",
            "Created At",
            "Updated At",         
          ]  

          # data rows
          BundleServicePreference.all.order("id ASC").each do |bsp|
            csv << 
            [ bsp.id, 
              bsp.service_preference_id,                 
              bsp.bundle_combo,
              bsp.upload_speed,
              bsp.download_speed,
              bsp.free_channels,
              bsp.premium_channels,
              bsp.domestic_call_minutes,
              bsp.international_call_minutes,
              bsp.domestic_call_unlimited,
              bsp.international_call_unlimited,
              bsp.data,
              bsp.data_speed,
              bsp.data_plan,
              bsp.created_at,
              bsp.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=bundle_service_preferences.csv" 
      }
    end
	end

  def edit
    @bundle_service_preference = BundleServicePreference.find(params[:id])
  end

  def update
    @bundle_service_preference = BundleServicePreference.find(params[:id])
    respond_to do |format|
      if @bundle_service_preference.update(bundle_service_preference_params)
        format.html { redirect_to bundle_service_preferences_path, notice: 'Successfully updated.' }
        format.xml  { render :xml => @bundle_service_preference, :status => :created, :bundle_service_preference => @bundle_service_preference }
      else
        format.html { render :edit }
        format.json { render json: @bundle_service_preference.errors, status: :unprocessable_entity }
      end
    end
  end

  def destroy
      @bundle_service_preference = BundleServicePreference.find(params[:id])
      respond_to do |format|
          if @bundle_service_preference.destroy
            format.html { redirect_to bundle_service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @bundle_service_preference, :status => :created, :bundle_service_preference => @bundle_service_preference }
          end
      end
  end

  private
  def bundle_service_preference_params
    params.require(:bundle_service_preference).permit(:service_preference_id, :upload_speed, :download_speed, :data, :free_channels, :premium_channels, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited, :data_plan, :data_speed, :bundle_combo)
  end
end 