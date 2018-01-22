
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ChannelName {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("image")
    @Expose
    private Image image;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The channelName
     */
    public String getChannelName() {
        return channelName;
    }

    /**
     * 
     * @param channelName
     *     The channel_name
     */
    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 
     * @return
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

}
