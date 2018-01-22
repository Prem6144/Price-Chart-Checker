
# Preview all emails at http://localhost:3000/rails/mailers/app_user_mailer
class AppUserMailerPreview < ActionMailer::Preview

  # Preview this email at http://localhost:3000/rails/mailers/app_user_mailer/recover_password_email
  def recover_password_email
    AppUserMailer.recover_password_email
  end


end
