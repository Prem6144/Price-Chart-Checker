class ChannelsController < ApplicationController
  # GET /Channels
  # GET /Channels.json
  # def index
  #   @channels = Channel.all
  # end

  def index
    @channels = Channel.paginate(:page => params[:page], :per_page => 20)
    respond_to do |format|
      format.html
    end
  end

  # GET /Channels/1
  # GET /Channels/1.json
  def show
    @channel = Channel.find(params[:id])
  end

  # GET /Channels/new
  def new

    @channel = Channel.new
  end

  # GET /Channels/1/edit
  def edit
    @channel = Channel.find(params[:id])
  end

  # POST /Channels
  # POST /Channels.json
  def create
    @channel = Channel.new(channel_params)

    respond_to do |format|
      if @channel.save
        format.html { redirect_to @channel, notice: 'Channel was successfully created.' }
        format.json { render :show, status: :created, location: @channel }
      else
        format.html { render :new }
        format.json { render json: @channel.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /Channels/1
  # PATCH/PUT /Channels/1.json
  def update
    @channel = Channel.find(params[:id])
    respond_to do |format|
      if @channel.update(channel_params)
        format.html { redirect_to @channel, notice: 'Channel was successfully updated.' }
        format.json { render :show, status: :ok, location: @channel }
      else
        format.html { render :edit }
        format.json { render json: @channel.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /Channels/1
  # DELETE /Channels/1.json
  def destroy
    @channel = Channel.find(params[:id])
    @channel.update_attributes(:status => false)
    respond_to do |format|
      format.html { redirect_to channels_url, notice: 'Channel was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Never trust parameters from the scary internet, only allow the white list through.
    def channel_params
      params.require(:channel).permit!
    end
end
