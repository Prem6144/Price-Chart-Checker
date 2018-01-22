class AdvertisementsController < ApplicationController

	def index
		@advertisements = Advertisement.all
	end

	def show
		@advertisement = Advertisement.find(params[:id])
	end

	def new
		@advertisement = Advertisement.new
	end

	def create
		@advertisement = Advertisement.new(advertisement_params)
		respond_to do |format|
			if @advertisement.save
				format.html { redirect_to advertisements_path, :notice => 'You have successfully added an advertisement' }
				format.xml { render :xml => @advertisement, :status => :created, :advertisement => @advertisement }
			else
				format.html { render :action => "new" }
				format.xml { render :xml => @advertisement.errors, :status => :unprocessable_entity }
			end
		end
	end

	def edit
		@advertisement = Advertisement.find(params[:id])
	end

	def update
		@advertisement = Advertisement.find(params[:id])
		respond_to do |format|
			if @advertisement.update(advertisement_params)
				format.html { redirect_to advertisements_path, notice: 'You have successfully updated an Advertisement.' }
				format.xml { render :xml => @advertisement, :status => :created, :advertisement => @advertisement }
			else
				format.html { render :edit }
				format.json { render json: @advertisement.errors, status: :unprocessable_entity }
			end
		end
	end

	def destroy
		@advertisement = Advertisement.find(params[:id])
		respond_to do |format|
			if @advertisement.destroy
				format.html { redirect_to advertisements_path, :notice => 'You have successfully removed an advertisement' }
				format.xml { render :xml => @advertisement, :status => :created, :advertisement => @advertisement }
			end
		end	
	end

	private
	
	def advertisement_params
		params.require(:advertisement).permit(:service_category_id, :service_category_name, :name, :url, :status, :start_date, :end_date, :image)
	end

end	