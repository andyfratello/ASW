json.extract! tweet, :id, :author, :content, :created_at, :updated_at
json.url tweet_url(tweet, format: :json)
