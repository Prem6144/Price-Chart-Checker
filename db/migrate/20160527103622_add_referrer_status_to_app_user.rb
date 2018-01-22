class AddReferrerStatusToAppUser < ActiveRecord::Migration
  def change
    add_column :app_users, :refer_status, :boolean, default: false
  end
end
