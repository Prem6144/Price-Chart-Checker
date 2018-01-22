class AddPasswordResetToAppUsers < ActiveRecord::Migration
  def change
    add_column :app_users, :password_reset_token, :string
    add_column :app_users, :password_reset_sent_at, :datetime
  end
end
