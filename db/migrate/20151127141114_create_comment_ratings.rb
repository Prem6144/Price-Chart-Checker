class CreateCommentRatings < ActiveRecord::Migration
  def change
    create_table :comment_ratings do |t|
    	t.belongs_to :app_user, index: true
      t.belongs_to :deal, index: true
      t.float :rating_point
      t.boolean :status, default: true
      t.text :comment_title
      t.text :comment_text
      t.timestamps null: false
    end
    add_foreign_key :comment_ratings, :app_users
    add_foreign_key :comment_ratings, :deals
  end
end
