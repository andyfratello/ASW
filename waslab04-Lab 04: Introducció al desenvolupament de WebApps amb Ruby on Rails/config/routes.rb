Rails.application.routes.draw do
  # Define your application routes per the DSL in https://guides.rubyonrails.org/routing.html
  resources :tweets do
    put 'like', on: :member
  end
  root 'tweets#index'
end
