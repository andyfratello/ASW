class AddLikesToTweets < ActiveRecord::Migration[7.0]
  def change
    add_column :tweets, :likes, :integer, default: 0
  end
end
