class DealAttributesController < ApplicationController

  def index
    @category_name = params[:category_name]
    @deal_id = params[:deal_id]
    @deal_attributes = eval("#{@category_name.camelcase}DealAttribute").where(:deal_id => @deal_id)
  end

  def new
    @deal_id = params[:deal_id]
    @deal = Deal.where(:id => params[:deal_id]).first
    @category_name = ServiceCategory.get_category_name_by_id(@deal.service_category_id)
    @deal_attribute = eval("#{@category_name.camelcase}DealAttribute").new()
   end

  def create
    @deal_attribute = eval("#{params[:category_name].camelcase}DealAttribute").new(deal_attributes_params)

    respond_to do |format|
      if @deal_attribute.save!
        #send_notification
        format.html { redirect_to deals_path, :notice => 'You have successfully created a deal attribute' }
        format.xml  { render :xml => @deal_attribute, :status => :created, :deal_attribute => @deal_attribute }
      else
        #raise @deal.errors.inspect
        format.html { render :action => "new" }
        format.json  { render :json => @deal_attribute.errors, :status => :unprocessable_entity }
      end
    end
  end

  def edit
    @category_name = params[:category_name]
    @deal_id = params[:deal_id]
    @deal = Deal.find_by(:id => params[:deal_id])
    @deal_attribute = eval("#{@category_name.camelcase}DealAttribute").where(:id => params[:id]).first
  end

  def update
    @deal_attribute = eval("#{params[:category_name].camelcase}DealAttribute").where(:id => params[:id]).first
    respond_to do |format|
      if @deal_attribute.update(deal_attributes_params)
        flash[:notice] = 'You have successfully updated a Deal attribute.'
        format.html { redirect_to deals_path, notice: 'You have successfully updated a Deal attribute.' }
        format.xml  { render :xml => @deal_attribute, :status => :created, :deal_attribute => @deal_attribute }
      else
        flash[:warning] = @deal_attribute.errors.full_messages
        format.html {  redirect_to edit_deal_attribute_path(:id => params[:id],:category_name => params[:category_name],:deal_id => @deal_attribute.deal_id) }
        format.json { render json: @deal_attribute.errors, status: :unprocessable_entity }
      end
    end
  end

  def destroy
    @deal_attribute = Deal.find(params[:id])
    respond_to do |format|
      if true#@deal_attribute.destroy
        format.html { redirect_to deals_path, :notice => 'You have successfully removed a deal' }
        format.xml  { render :xml => @deal_attribute, :status => :created, :deal_attribute => @deal_attribute }
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

  def deal_attributes_params
    params.require(:deal_attributes).permit!
  end



end
