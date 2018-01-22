class DealEquipmentsController < ApplicationController

  def index
    @category_name = params[:category_name]
    @deal_id = params[:deal_id]
    @deal_equipments = eval("#{@category_name.camelcase}Equipment").where(:deal_id => @deal_id)
  end

  def new
    @deal_id = params[:deal_id]
    deal = Deal.where(:id => params[:deal_id]).first
    @category_name = ServiceCategory.get_category_name_by_id(deal.service_category_id)
    @deal_equipment = eval("#{@category_name.camelcase}Equipment").new()
  end

  def create
    @deal_equipment = eval("#{params[:category_name].camelcase}Equipment").new(deal_equipments_params)

    respond_to do |format|
      if @deal_equipment.save
        #send_notification
        format.html { redirect_to deals_path, :notice => 'You have successfully created a deal equipment' }
        format.xml  { render :xml => @deal_equipment, :status => :created, :deal => @deal }
      else
        #raise @deal.errors.inspect
        format.html { render :action => "new" }
        format.json  { render :json => @deal_equipment.errors, :status => :unprocessable_entity }
      end
    end
  end

  def edit
    @category_name = params[:category_name]
    @deal_id = params[:deal_id]
    @deal_equipment = eval("#{@category_name.camelcase}Equipment").where(:id => params[:id]).first
  end

  def update
     @deal_equipment = eval("#{params[:category_name].camelcase}Equipment").where(:id => params[:id]).first
    respond_to do |format|
      if @deal_equipment.update(deal_equipments_params)
        flash[:notice] = 'You have successfully updated a Deal equipment.'
        format.html { redirect_to deals_path, notice: 'You have successfully updated a Deal equipment.' }
        format.xml  { render :xml => @deal_equipment, :status => :created, :deal_equipment => @deal_equipment }
      else
        flash[:warning] = @deal_equipment.errors.full_messages
        format.html {  redirect_to edit_deal_equipment_path(:id => params[:id],:category_name => params[:category_name],:deal_id => @deal_equipment.deal_id) }
        format.json { render json: @deal_equipment.errors, status: :unprocessable_entity }
      end
    end

    # @deal = Deal.find(params[:id])
    # respond_to do |format|
    #   if @deal.update(deal_params)
    #     format.html { redirect_to deals_path, notice: 'You have successfully updated a Deal.' }
    #     format.xml  { render :xml => @deal, :status => :created, :deal => @deal }
    #   else
    #     format.html { render :edit }
    #     format.json { render json: @deal.errors, status: :unprocessable_entity }
    #   end
    # end
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

  def deal_equipments_params
    params.require(:deal_equipments).permit!
  end



end