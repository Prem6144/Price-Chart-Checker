class AppUserMailer < ApplicationMailer
  default from: "abc@abc.com"

  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.app_user_mailer.recover_password_email.subject
  #
  def recover_password_email(app_user)
    # app_user=AppUser.find_by_email("abc@abc.com")
    recipient = app_user.email
    @app_user = app_user
    # @secret_p = app_user.unhashed_password
    # app_user.send_password_reset if user
    @name=Base64.decode64(app_user.first_name) + " " + Base64.decode64(app_user.last_name)
    mail(:to => recipient, :subject => "ServiceDealz recover password") rescue nil
  end

  def cashout_email(app_user,cashout)
    recipient = "abc@abc.com"
    @reedeem_amount = cashout.reedeem_amount
    @email = cashout.email_id
    @name = app_user.first_name
    mail(:to => recipient, :subject => "Cashout") rescue nil
  end

  def contact_us(name,email,subject,message)
    recipient = "abc@abc.com,abc@abc.com,abc@abc.com"
    @name = name;@email = email; @subject = subject.titleize; @message = message
    mail(:to => recipient, :cc => @email,:subject => "New Query: #{@subject}") rescue nil
  end

  def send_verification_mail(id,code)
    @secure_token=code
    puts @secure_token
    @user_id=id
    email=AppUser.find(id).email
    mail(:to=>email,:subject=>"Service Dealz:Verification mail")
  end

  def sign_up_mail(app_user)
    # app_user=AppUser.find_by_email("abc@abc.com")
    @name=Base64.decode64(app_user.first_name) + " " + Base64.decode64(app_user.last_name)
    mail(:to=>app_user.email,:subject=>"Welcome to ServiceDealz")
   end

end
