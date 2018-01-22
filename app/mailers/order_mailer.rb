class OrderMailer < ApplicationMailer
  layout 'custom_mailer'
  # Subject can be set in your I18n file at config/locales/en.yml
  # with the following lookup:
  #
  #   en.app_user_mailer.recover_password_email.subject
  #
  def order_confirmation(app_user,order)
    recipient = app_user.email
    @name=Base64.decode64(app_user.first_name) + " " + Base64.decode64(app_user.last_name)
    @app_user = app_user
    @order = order
    @order_billing_address=order.order_addresses[1]
    mail(to: recipient, subject: "ServiceDealz Order Confirmation# #{order.order_number}")
  end

end
