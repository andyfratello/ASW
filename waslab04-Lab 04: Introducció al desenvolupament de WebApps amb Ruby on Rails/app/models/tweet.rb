class Tweet < ApplicationRecord
  validates :author, length: {minimum: 4}
  validates :content, length: {in: 4..280}

end
