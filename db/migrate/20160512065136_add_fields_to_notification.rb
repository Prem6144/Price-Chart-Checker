class AddFieldsToNotification < ActiveRecord::Migration
  def change
    add_column :notifications, :repeat_notification_frequency, :string, default: "Weekly"
    add_column :notifications, :trending_deal_frequency, :string, default: "Weekly"
    add_column :notifications, :receive_call, :boolean,  default: true
    add_column :notifications, :min_service_provider_rating, :integer
    add_column :notifications, :min_deal_rating, :integer
    add_column :notifications, :receive_email, :boolean,  default: true
    add_column :notifications, :receive_text, :boolean,  default: true
  end
end
