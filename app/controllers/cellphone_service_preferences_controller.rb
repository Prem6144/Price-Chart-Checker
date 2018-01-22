class CellphoneServicePreferencesController < ApplicationController
	
	def index
		@cellphone_service_preferences = CellphoneServicePreference.all
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
            "Data Speed",
            "Data Plan",
            "Created At",
            "Updated At",         
          ]  

          # data rows
          CellphoneServicePreference.all.order("id ASC").each do |csp|
            csv << 
            [ csp.id, 
              csp.service_preference_id,                 
              csp.domestic_call_minutes,
              csp.international_call_minutes,
              csp.domestic_call_unlimited,
              csp.international_call_unlimited,
              csp.data_speed,
              csp.data_plan,
              csp.created_at,
              csp.updated_at
            ]
          end               
        end 
        # send it to the browser
        send_data csv_string, 
          :type => 'text/csv; charset=iso-8859-1; header=present', 
          :disposition => "attachment; filename=cellphone_service_preferences.csv" 
      }
    end
	end

  def destroy
      @cellphone_service_preference = CellphoneServicePreference.find(params[:id])
      respond_to do |format|
          if @cellphone_service_preference.destroy
            format.html { redirect_to cellphone_service_preferences_path, :notice => 'You have successfully removed a service preference' }
            format.xml  { render :xml => @cellphone_service_preference, :status => :created, :cellphone_service_preference => @cellphone_service_preference }
          end
      end
  end

  private
  def cellphone_service_preference_params
    params.require(:cellphone_service_preference).permit(:service_preference_id, :domestic_call_minutes, :international_call_minutes, :domestic_call_unlimited, :international_call_unlimited, :data_plan, :data_speed)
  end
end 