class ApplicationController < ActionController::Base
  def hello
    render html: "<h1>It works WASLAB04!</h1>".html_safe
  end
end
