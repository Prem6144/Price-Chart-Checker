module ServiceCategoriesHelper

  def get_service_categories
		service_categories_hash = {}
		service_categories = ServiceCategory.get_service_categories
		service_categories.each do |sc|
			service_categories_hash[sc.name] = sc.id
		end
		service_categories_hash
	end

end