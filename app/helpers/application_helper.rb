module ApplicationHelper
  def match_controller?(name)
    controller.controller_name == name
  end

  def match_action?(name)
    controller.action_name == name
  end

  def current_controller
    controller.controller_name.humanize
  end

  def link_to_add_fields(name, f, association)
    new_object = f.object.send(association).klass.new
    id = new_object.object_id
    fields = f.fields_for(association, new_object, child_index: id) do |builder|
      render(association.to_s.singularize + "_fields", f: builder)
    end
    link_to(name, '#', class: "add_fields", data: {id: id, fields: fields.gsub("\n", "")})
  end

  def get_zipcode_and_user_type
    unless session[:zip_code].present?
      if session[:user_id].present?
        app_user = AppUser.find_by_id(session[:user_id])
        session[:zip_code] = app_user.zip.present? ? app_user.zip : session[:zip_code]
        session[:user_type] = app_user.user_type.present? ? app_user.user_type : session[:user_type]
      end
    else
      session[:zip_code]
    end
  end

  def get_label_name(service_provider_id,label_key)
    if service_provider_id.present?
      label = DynamicLabel.where(:label_key => label_key,:service_provider_id =>  service_provider_id).first
      label_value = label.present? ? label.label_value.capitalize : label_key
      label_value
    else
      label_key
    end
  end
end
