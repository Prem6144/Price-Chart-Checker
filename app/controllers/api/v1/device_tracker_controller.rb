class Api::V1::DeviceTrackerController < ApplicationController
  def add_devise_details
    session_token=SecureRandom.hex(5)
    if !DeviceRegister.find_by_device_id(params[:device_id])
      # Device not yet registered
      save=DeviceRegister.create!(:device_id=>params[:device_id],:imei=>params[:imei], :token=>session_token, :version => params[:version] , device_type: params[:device_type])
      register_or_update(params,1,save.id)
      
    else
      #Device already registered ,so just updating token and registering information
      device=DeviceRegister.find_by_device_id(params[:device_id])
      device.update_attributes(:token=> session_token,:version => params[:version] , device_type: params[:device_type])
      register_or_update(params,2,device.id)
    end
    if params[:device_type].present? && params[:version].present?
      app_version_details = AppVersion.where(device_type: params[:device_type] ).last
      latest_version =app_version_details.versn_num.to_f
      user_version = params[:version].to_f
      if latest_version > user_version
        is_force_upgrade = true
        is_normal_upgrade = app_version_details.is_normal_upgrade
      elsif latest_version = user_version
        is_force_upgrade = false
        is_normal_upgrade = false
      end

      # app_version_num = app_version_details
      render :json=>{
      session_token: session_token,
      is_force_upgrade: is_force_upgrade,
      is_normal_upgrade: is_normal_upgrade  ,
      app_url: app_version_details.app_url 
    }
    else
    render :json=>{
      session_token: session_token
      
    }
  end
  end

  def register_or_update(params,type,id)
    params[:device_register_id]=id
    DeviceTracker.create!(
    :device_id=>params[:device_id],
    :service_provider=>params[:service_provider],
    :imei=>params[:imei],
    :dual_sim=>params[:dual_sim],
    :country=>params[:country],
    :sim_operator=>params[:sim_operator],
    :sim_serial_number=>params[:sim_serial_number],
    :voice_mail_number=>params[:voice_mail_number],
    :location=>params[:location],
    :device_type=>params[:device_type],
    :provider_type=>params[:provider_type],
    :roaming=>params[:roaming],
    :device_register_id=>id)
  end

  def destroy_session
    DeviceRegister.find_by_device_id(params[:device_id]).update_attributes(:token=>"")
    render :json=>{
      status: "Session Destroyed"
    }
  end
end
