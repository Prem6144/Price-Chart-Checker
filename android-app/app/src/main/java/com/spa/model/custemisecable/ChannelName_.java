
package com.spa.model.custemisecable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ChannelName_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("channel_name")
    @Expose
    private String channelName;
    @SerializedName("image")
    @Expose
    private Image_ image;

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
    public Image_ getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Image_ image) {
        this.image = image;
    }

}
