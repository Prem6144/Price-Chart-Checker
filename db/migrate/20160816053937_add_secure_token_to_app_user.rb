class AddSecureTokenToAppUser < ActiveRecord::Migration
  def change
    add_column :app_users, :email_verification_token, :string
    add_column :app_users, :email_verified, :boolean
  end
end
