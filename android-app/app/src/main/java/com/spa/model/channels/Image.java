
package com.spa.model.channels;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Image {

    @SerializedName("url")
    @Expose
    private Object url;

    /**
     * 
     * @return
     *     The url
     */
    public Object getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(Object url) {
        this.url = url;
    }

}
