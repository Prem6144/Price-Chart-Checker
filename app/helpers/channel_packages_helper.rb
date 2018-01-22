module ChannelPackagesHelper

  def get_channels
    channel_hash = {}
    channels = Channel.get_channels
    channels.each do |channel|
      channel_hash[channel.channel_name] = channel.id
    end
    channel_hash
  end


end
