class Ability
  include CanCan::Ability

  def initialize(user)
    user ||= User.new

    if user.role? 'super_admin'
      can :manage, :all
    elsif user.role? 'admin'
      can :read, [User, ServiceCategory, SubscribeDeal, TrendingDeal, Deal, ServicePreference, InternetServicePreference, BundleServicePreference, CableServicePreference, TelephoneServicePreference, CellphoneServicePreference, Notification, Comment, Advertisement, Rating, BulkNotification, CommentRating]  
    end  

  end
end
