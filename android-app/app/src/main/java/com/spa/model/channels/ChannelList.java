
package com.spa.model.channels;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ChannelList {

    @SerializedName("channels")
    @Expose
    private List<Channel> channels = new ArrayList<Channel>();

    /**
     * 
     * @return
     *     The channels
     */
    public List<Channel> getChannels() {
        return channels;
    }

    /**
     * 
     * @param channels
     *     The channels
     */
    public void setChannels(List<Channel> channels) {
        this.channels = channels;
    }

}
