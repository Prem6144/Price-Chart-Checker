
package com.spa.model.channels;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Channel {

    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("channel")
    @Expose
    private List<Channel_> channel = new ArrayList<Channel_>();

    /**
     * 
     * @return
     *     The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * @param categoryName
     *     The category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * @return
     *     The channel
     */
    public List<Channel_> getChannel() {
        return channel;
    }

    /**
     * 
     * @param channel
     *     The channel
     */
    public void setChannel(List<Channel_> channel) {
        this.channel = channel;
    }

}
