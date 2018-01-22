class CreateBandwidthCalculatorSettings < ActiveRecord::Migration
  def change
    create_table :bandwidth_calculator_settings do |t|
      t.integer :email
      t.integer :web_page
      t.integer :video_calling
      t.integer :audio_calling
      t.integer :photo_upload_download
      t.integer :video_streaming
    end
  end
end