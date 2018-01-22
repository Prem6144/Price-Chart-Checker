class ChannelPackagesController < ApplicationController
  # GET /ChannelPackages
  # GET /ChannelPackages.json
  # def index
  #   @channel_packages = ChannelPackage.all
  # end

  before_filter :service_provider_list, only: [:new,:edit]

  def index
    @channel_packages = ChannelPackage.paginate(:page => params[:page], :per_page => 20)
    respond_to do |format|
      format.html
    end
  end

  # GET /ChannelPackages/1
  # GET /ChannelPackages/1.json
  def show
    @channel_package = ChannelPackage.find(params[:id])
  end

  # GET /ChannelPackages/new
  def new

    @channel_package = ChannelPackage.new
  end

  # GET /ChannelPackages/1/edit
  def edit
    @channel_package = ChannelPackage.find(params[:id])
  end

  # POST /ChannelPackages
  # POST /ChannelPackages.json
  def create
    @channel_package = ChannelPackage.new(channel_package_params)

    respond_to do |format|
      if @channel_package.save
        format.html { redirect_to @channel_package, notice: 'ChannelPackage was successfully created.' }
        format.json { render :show, status: :created, location: @channel_package }
      else
        format.html { render :new }
        format.json { render json: @channel_package.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /ChannelPackages/1
  # PATCH/PUT /ChannelPackages/1.json
  def update
    @channel_package = ChannelPackage.find(params[:id])
    respond_to do |format|
      if @channel_package.update(channel_package_params)
        format.html { redirect_to @channel_package, notice: 'ChannelPackage was successfully updated.' }
        format.json { render :show, status: :ok, location: @channel_package }
      else
        format.html { render :edit }
        format.json { render json: @channel_package.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /ChannelPackages/1
  # DELETE /ChannelPackages/1.json
  def destroy
    @channel_package = ChannelPackage.find(params[:id])
    @channel_package.update_attributes(:status => false)
    respond_to do |format|
      format.html { redirect_to channels_url, notice: 'ChannelPackage was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Never trust parameters from the scary internet, only allow the white list through.
    def channel_package_params
      params.require(:channel_package).permit!
    end

    def service_provider_list
      @service_providers = ServiceProvider.where(service_category: Deal::CABLE_CATEGORY).select('name,id')
    end
end
