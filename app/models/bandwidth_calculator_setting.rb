class BandwidthCalculatorSetting < ActiveRecord::Base

  def self.calculate_bandwidth(params)
    setting = self.first
    no_of_devices = params[:no_of_devices].present? ? params[:no_of_devices].to_i : 1

    email = params[:email].present? ? params[:email].to_i : 0

    web_page = params[:web_page].present? ? params[:web_page].to_i : 0

    video_calling = params[:video_calling].present? ? params[:video_calling].to_i : 0

    audio_calling = params[:audio_calling].present? ? params[:audio_calling].to_i : 0

    photo_upload_download = params[:photo_upload_download].present? ? params[:photo_upload_download].to_i : 0

    video_streaming = params[:video_streaming].present? ? params[:video_streaming].to_i : 0

    total_data_usage = (email * setting.email) + (web_page * setting.web_page) + (video_calling * setting.video_calling) + (audio_calling * setting.audio_calling) + (photo_upload_download * setting.photo_upload_download) + (video_streaming * setting.video_streaming)
    total_data_usage = total_data_usage * no_of_devices
    usage_in_mb = (total_data_usage / 1024)
    usage_in_gb = '%.2f' % (usage_in_mb / 1024.0)
    return usage_in_mb,usage_in_gb
  end
end
