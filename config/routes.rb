require 'api'
Rails.application.routes.draw do
  devise_for :app_users, skip: [:sessions, :passwords, :registrations]
# root to: "home#index"
match  'blog' => 'website/home#blog', :via => :get
  get  '*a.txt', :to => 'website/errors#routing'


  namespace :api do
    namespace :v1 do
      devise_scope :app_user do
        post 'sessions' => 'sessions#create', :as => 'login'
      end
      match 'service_categories' => 'service_categories#index', :via => :get
      match 'service_preferences' => 'service_preferences#create', :via => :post
      match 'service_preferences/send_ios_notification' => 'service_preferences#send_ios_notification', :via => :get
      match 'notifications' => 'notifications#create', :via => :post
      match 'get_notification' => 'notifications#fetch_notification', :via => :get
      match 'app_users' => 'app_users#create', :via => :post
      match 'update_user' => 'app_users#update_app_user', :via => :post
      match 'service_providers' => 'service_providers#get_service_providers', :via => :get
      match 'device_details' => 'device_tracker#add_devise_details', :via => :post
      match 'destroy_session' => 'device_tracker#destroy_session', :via => :post
      match 'get_preferences' => 'service_preferences#fetch_service_preferences', :via => :get
      match 'deselect_prference' => 'service_preferences#deselect_service_preference', :via => [:delete,:post]
      match 'get_states' => 'orders#get_states', :via => :get
      match 'get_cities' => 'orders#get_cities', :via => :get
      match 'primary_information' => "app_users#primary_information", :via => :get
      match 'cellphone_equipments' => "deals#cellphone_equipments", :via => :get
      match 'customisable_deals' => "deals#customisable_deals", :via => :get
      match 'customisable_deal_deatail' => "deals#customisable_deal_deatail", :via => :get
      match 'channel_customisable_deals' => "deals#channel_customisable_deals", :via => :get
      match 'cellphone_details' => "deals#cellphone_details", :via => :get
      match 'service_deal_config' => "deals#service_deal_config", :via => :get
      match 'password_complicated_setting' => "deals#password_complicated_setting", :via => :get
       match 'compare_deals' => "deals#compare_deals", :via => :get


      resources :dashboards do
        post 'dashboards' => 'dashboards#index'
      end
      resources :deals do
        get 'deals' => 'deals#index'
      end
      match 'app_user' => 'app_users#get_app_user', :via => :get
      match 'you_save' => 'app_users#you_save', :via => :get
      match 'service_preferences' => 'service_preferences#get_service_preferences', :via => :get
      match 'forget_password' => 'app_users#recover_password', :via => :post
      match 'comment_ratings' => 'comment_ratings#create', :via => :post
      match 'comment_ratings' => 'comment_ratings#index', :via => :get
      match 'referral_tracking' => 'referral_infos#create', :via => :post
      match 'get_category_deals' => 'dashboards#category_deals', :via => :get
      match 'subscribed_deals' => 'subscribe_deals#subscription_info', :via => :post
      match 'orders' => 'orders#create', :via => :post
      match 'get_orders' => 'orders#get_orders', :via => :get
      match 'my_orders' => 'orders#my_orders', :via => :get
      match 'get_order_address' => 'orders#get_order_address', :via => :get
      match 'get_gifts' => 'gifts#get_gifts', :via => :get
      match 'account_referrals' => 'account_referrals#create', :via => :post
      match 'referral_code' => 'app_users#my_referral_code', :via => :get
      match 'my_earnings' => 'app_users#referrals_and_gifts', :via => :get
      match 'cashout' => 'cashout_infos#create', :via => :post
      match 'refer_contact' => 'app_users#refer_contact', :via => :post
      match 'validate_business_name' => 'dashboards#validate_business_name', :via => :post
      match 'get_deal_channels' => 'deals#get_deal_channels', :via => :get
      match 'get_channel_details' => 'deals#get_channel_details', :via => :get
      match 'get_estimated_bandwidth' => 'deals#get_estimated_bandwidth', :via => :post
      match 'verify_user'=>"app_users#verify_user", :via => :get
      match 'deal_details'=>"deals#fetch_deal_details", :via => :get
      match 'dynamic_label_for_service_provider'=>"dashboards#dynamic_label_for_service_provider", :via => :get
      match 'get_rewards' => 'gifts#get_rewards', :via => :get
      resources :orders do
        collection do
          post :fetch_user_and_deal_details
          post :my_order_details
          post :validate_business_name
        end
      end

    end
  end

  #resources :service_categories do
  #  collection { post :import }
  #end
  get "/proxy_verify"=>"website/app_users#proxy_verify"
  match "/edit_addresses" => "website/app_users#edit_addresses", :via => [:post]
  match "/set_default_address" => "website/app_users#set_default_address", :via => [:post]
  match "/delete_address" => "website/app_users#delete_address", :via => [:post]
  match "/edit_or_change_service_preferences" => "api/v1/service_preferences#create", :via => :post
  get "/tnc" =>"website/home#tnc"
  get "/contact_us" =>"website/home#contact_us"
  get "/about_us" =>"website/home#about_us"
  get "/privacy_policy" =>"website/home#privacy_policy"

  #
  # if Socket.gethostname=="servicedlz-Virtual-Machine"
  # root to: "home#index"
  # else
  root to: "website/home#index"
  # end

  devise_for :users
  resources :users do
    member do
      get :edit_password
      get :unlock
      put :update_password
    end
    collection do
      get :reset_password
      post :set_reset_password
    end
  end
  get "/calculate_bandwidth" => "website/home#calculate_bandwidth"
  get "/get_deals_from_first_page" =>"website/home#get_deals_from_first_page"
  get '/get_user_addresses'=> "website/app_users#user_addresses"
  get '/get_business_user_addresses'=> "website/app_users#business_user_addresses"
  get "/extra_service_deal" => 'extra_services#extra_service_deal'

  get 'deals/get_service_providers'=>'deals#get_service_providers'
  get "/searchzip" => "deals#searchzip"
  get "/verify_email"=>"website/app_users#verify_email"
  resources :deals do
    post 'import', on: :collection
  end
  resources :service_categories do
    post 'import', on: :collection
  end
  resources :app_users
  resources :service_preferences
  resources :notifications
  resources :service_providers do
    post 'import', on: :collection
  end
  resources :advertisements
  #resources :comments
  #resources :ratings
  resources :bulk_notifications
  resources :comment_ratings
  resources :referral_infos
  resources :internet_service_preferences
  resources :cable_service_preferences
  resources :telephone_service_preferences
  resources :cellphone_service_preferences
  resources :bundle_service_preferences
  resources :subscribe_deals
  resources :trending_deals
  resources :zipcodes do
    post 'import', on: :collection
  end
  resources :orders
  resources :gifts
  resources :user_gifts
  resources :channels
  resources :channel_packages


  get 'service_dealz' => 'website/home#index'
  get 'service_deals' => 'website/home#index'
  get 'website' => 'website/home#index'
  namespace :website do
    resources :home do
      collection do
        get :deals
        get :compare_deals
        get :deal_details
        get :more_deal_details
        get :set_zipcode_and_usertype
        get :contact_us
        get :about_us
        get :our_providers
        get :coming_soon
        get :tnc
      end
    end

    resources :app_users do
      collection do
        get :check_user_email_ajax
        get :check_user_credential
        get :signout
        post :signin
        get :checkout
        get :profile
        get :order_history
        post :preferences
        get :order
        get :order_detail
        post :create_order
        post :contact_us
        get :forget_password
        get :edit_password
        post :update_password
        get :order_attributes
        get :order_extra_services
        get :order_equipment_data
        get :order_channel_packages
        get :order_cable_equipments
      end
    end
  end

  resources :referral_gift_amounts
  resources :service_provider_checklists do
    post 'import', on: :collection
  end
  resources :deal_equipments
  resources :deal_attributes
  resources :deal_extra_services
  resources :equipment_colors
  resources :cellphone_details
  resources :extra_services

  get  '*a', :to => 'website/errors#routing'


end
