class DealsController < ApplicationController


	def index
		@deals = Deal.paginate(:page => params[:page], :per_page => 20).order("created_at DESC")
    respond_to do |format|
      format.html
      #format.xls # { send_data @products.to_csv(col_sep: "\t") }
      format.csv {
        csv_string = CSV.generate do |csv|
          # header row

          csv <<
          [ "ID",
            "Service Category ID",
            "Service Provider ID",
            "Title",
            "State",
            "City",
            "Zip",
            "Price",
            "Upload Speed",
            "Download Speed",
            "Free Channels",
            "Premium Channels",
            "Data Plan",
            "Data Speed",
            "Domestic Call Minutes",
            "International Call Minutes",
            "Domestic Call Unlimited",
            "International Call Unlimited",
            "Bundle Combo",
            "Is Active",
            "URL",
            "Start Date",
            "End Date",
            "Short Description",
            "Detail Description",
            "Image",
            "Created At",
            "Updated At",
          ]

          # data rows
          Deal.all.order("id ASC").each do |deal|
            csv <<
            [ deal.id,
              deal.service_category_id,
              deal.service_provider_id,
              deal.title,
              deal.state,
              deal.city,
              deal.zip,
              deal.price,
              deal.upload_speed,
              deal.download_speed,
              deal.free_channels,
              deal.premium_channels,
              deal.data_plan,
              deal.data_speed,
              deal.domestic_call_minutes,
              deal.international_call_minutes,
              deal.domestic_call_unlimited,
              deal.international_call_unlimited,
              deal.bundle_combo,
              deal.is_active,
              deal.url,
              deal.start_date,
              deal.end_date,
              deal.short_description,
              deal.detail_description,
              deal.image.url,
              deal.created_at,
              deal.updated_at
            ]
          end
        end
        # send it to the browser
        send_data csv_string,
          :type => 'text/csv; charset=iso-8859-1; header=present',
          :disposition => "attachment; filename=deals.csv"
      }
    end
	end

	def new
    @deal = Deal.new
    @deal_internet = @deal.internet_deal_attributes.build
    @deal_internet.internet_equipments.build
    @deal_telephone = @deal.telephone_deal_attributes.build
    @deal_telephone.telephone_equipments.build
    @deal_cable = @deal.cable_deal_attributes.build
    @deal_cable.cable_equipments.build
    @deal_cellphone = @deal.cellphone_deal_attributes.build
    @deal_cellphone.cellphone_equipments.build
    @deal_bundle = @deal.bundle_deal_attributes.build
    @deal_bundle.bundle_equipments.build
    @deal.additional_offers.build
    @deal.deal_include_zipcodes.build
  end

  def get_service_providers
    #@ServiceProviders=ServiceProvider.select("id, name").where(service_category_name: params[:category])
    @ServiceProviders=ServiceProvider.select("id, name").where(service_category_id: params[:category])
    render :json => @ServiceProviders
  end

	def create
    @deal = Deal.new(deal_params)

    respond_to do |format|
      if @deal.save
        #send_notification
        format.html { redirect_to deals_path, :notice => 'You have successfully created a deal' }
        format.xml  { render :xml => @deal, :status => :created, :deal => @deal }
      else
        #raise @deal.errors.inspect
        format.html { render :action => "new" }
        format.json  { render :json => @deal.errors, :status => :unprocessable_entity }
      end
    end
	end
	def edit
  	@deal = Deal.find(params[:id])
  end
	def update
		@deal = Deal.find(params[:id])
    respond_to do |format|
      if @deal.update(deal_params)
        format.html { redirect_to deals_path, notice: 'You have successfully updated a Deal.' }
        format.xml  { render :xml => @deal, :status => :created, :deal => @deal }
      else
        format.html { render :edit }
        format.json { render json: @deal.errors, status: :unprocessable_entity }
      end
    end
  end
  def destroy
    @deal = Deal.find(params[:id])
    respond_to do |format|
          if @deal.destroy
            format.html { redirect_to deals_path, :notice => 'You have successfully removed a deal' }
            format.xml  { render :xml => @deal, :status => :created, :deal => @deal }
          end
    end
  end
  def import
    Deal.import(params[:file])
    redirect_to deals_path, :notice => "Successfully imported."
  end

	def searchzip
		zip = Zipcode.where("code like '#{params[:zip]}%' ").first
		#puts zip.to_yaml
		if zip
			render :json=>{
				:city => zip.city,
				:area => zip.area,
				:country => zip.country
			}
		end
	end

	private

  #def send_notification
  #  @app_user = AppUser.where("zip = ?",params[:deal][:zip])
  #  gcm = GCM.new("AIzaSyASkbVZHnrSGtqjruBalX0o0rQRA1dYU7w")
  #  @app_user.map do |a_user|
  #    registration_id = ["#{a_user.gcm_id}"]
      #message = "New deal for zip #{params[:deal][:zip]}.Visit Url : #{params[:deal][:url]}"
  #    gcm.send(registration_id, {data: {message: "New deal for zip #{params[:deal][:zip]}.Visit Url : #{params[:deal][:url]}"}})
  #  end
  #end

  def deal_params
    params.require(:deal).permit!
  end



end
