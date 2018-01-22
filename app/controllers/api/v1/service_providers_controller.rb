class Api::V1::ServiceProvidersController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def get_service_providers
		@service_provider = ServiceProvider.where("is_active = ?", true).where("service_category_id = ?", params[:category])
		if @service_provider.present?
			render :status => 200,
             :json => {
                        :success => true,
                        :service_provider => @service_provider.as_json(:except => [:created_at, :updated_at])
                      }
		else
			render :json => { :success => false }
		end	
	end

end