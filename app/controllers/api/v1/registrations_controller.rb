class Api::V1::RegistrationsController  < ApplicationController
  skip_before_filter :verify_authenticity_token
  respond_to :json
end  