class HomeController < ApplicationController
  #before_filter :authenticate_user!, :except => [:index]

  def index
    redirect_to new_user_session_path if !user_signed_in? 
  end

end