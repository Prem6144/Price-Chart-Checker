class TelephoneServicePreferencesController < ApplicationController
	
	def index
		@telephone_service_preferences = TelephoneServicePreference.all
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row
          csv << 
          [ "ID",   
            "ServicePreference ID",
            "DomesticCall Minutes",
            "InternationalCall Minutes",
            "DomesticCall Unlimited",
            "InternationalCall Unlimited",
            "Created At",
            "Updated At",         
          ]  

          # data rows
          TelephoneServicePreference.all.order("id ASC").each do |bsp|
            csv << 
            [ bsp.id, 
              bsp.service_preference_id, 
              bsp.domestic_call_minutes,
              bsp.international_call_minutes,
              bsp.domestic_call_unlimited,
              bsp.international_call_unlimited,
              bsp.created_at,
              bsp.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=telephone_service_preferences.csv" 
      }
    end
	end

  def destroy
      @telephone_service_preference = TelephoneServicePreference.find(params[:id])
      respond_to do |format|
          if @telephone_service_preference.destroy
            format.html { redirect_to telephone_service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @telephone_service_preference, :status => :created, :telephone_service_preference => @telephone_service_preference }
          end
      end
  end

  private
  def telephone_service_preference_params
    params.require(:telephone_service_preference).permit(:service_preference_id, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited)
  end
end 