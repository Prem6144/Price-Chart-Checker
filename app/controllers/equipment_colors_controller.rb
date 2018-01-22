class EquipmentColorsController < ApplicationController
	def index
    @equipment_colors = EquipmentColor.paginate(:page => params[:page], :per_page => 20).order("id ASC")
	end
	def new
    @equipment_color = EquipmentColor.new
	end
	def edit
     @equipment_color= EquipmentColor.find(params[:id])
	end
	def create
    @equipment_color = EquipmentColor.new(equipment_colors_params)    
    respond_to do |format|
      if @equipment_color.save
        format.html { redirect_to equipment_colors_path, :notice => 'You have successfully created a Equipment Color' }
        format.xml  { render :xml => @equipment_color, :status => :created, :equipment_color => @equipment_color }
      else
        format.html { render :action => "new" }
        format.xml  { render :xml => @equipment_color.errors, :status => :unprocessable_entity }
      end
    end
	
	end

	def update
    @equipment_color = EquipmentColor.find(params[:id])
    respond_to do |format|
      if @equipment_color.update(equipment_colors_params)
        format.html { redirect_to equipment_colors_path, notice: 'You have successfully updated a EquipmentColor.' }
        format.xml  { render :xml => @equipment_color, :status => :created, :equipment_color=> @equipment_color }
      else
        format.html { render :edit }
        format.json { render json: @equipment_color.errors, status: :unprocessable_entity }
      end
    end
		
  	end
  	def destroy
      @equipment_color = EquipmentColor.find(params[:id])
    respond_to do |format|
      if @equipment_color.destroy
        format.html { redirect_to equipment_colors_path, :notice => 'You have successfully removed a EquipmentColor' }
        format.xml  { render :xml => @equipment_color, :status => :created, :equipment_color=> @equipment_color }
      end
    end
    	
   end

	private
  def equipment_colors_params
   params.require(:equipment_color).permit(:color_name, :status,:image)
  end
end
 
