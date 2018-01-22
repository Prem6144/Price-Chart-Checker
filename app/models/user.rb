class User < ActiveRecord::Base
	has_and_belongs_to_many :roles
  # Include default devise modules. Others available are:
  # :confirmable, :lockable, :timeoutable and :omniauthable
  devise :database_authenticatable, :recoverable, :rememberable, :trackable
  
  validates :email, :presence => true, :uniqueness => true
  validates :password, :presence => true, :confirmation => true, :on => :create

  def as_json(opts={})
    json = super(opts)
    Hash[*json.map{|k, v| [k, v || ""]}.flatten]
  end

  ROLES = %w[super_admin admin serviceprovider]

	def is_super_admin?
		return !!(self.role == 'super_admin')
	end

	def is_at_admin_level?
    return !!(self.role == 'admin' || self.role == 'super_admin')
  end

  def role?(r)
    return !!(self.role == r)
  end
end
