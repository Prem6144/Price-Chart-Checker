class Api::V1::ServiceCategoriesController < ApplicationController
	skip_before_filter :verify_authenticity_token
	respond_to :json

	def index
		@s_categories = ServiceCategory.all.order("id ASC").to_a
		if @s_categories.present?
			render :status => 200,
             :json => {
                        :success => true,
                        :service_category => @s_categories.as_json(:except => [:description, :created_at, :updated_at])
                      }
		else
			render :json => { :success => false }
		end	
	end
end	