class CreateTweets < ActiveRecord::Migration[7.0]
  def change
    create_table :tweets do |t|
      t.string :author
      t.text :content

      t.timestamps
    end
  end
end
