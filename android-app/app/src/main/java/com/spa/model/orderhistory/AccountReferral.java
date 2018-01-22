
package com.spa.model.orderhistory;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AccountReferral {

    @SerializedName("refer_message")
    @Expose
    private String referMessage;
    @SerializedName("refer_image")
    @Expose
    private String referImage;

    /**
     * 
     * @return
     *     The referMessage
     */
    public String getReferMessage() {
        return referMessage;
    }

    /**
     * 
     * @param referMessage
     *     The refer_message
     */
    public void setReferMessage(String referMessage) {
        this.referMessage = referMessage;
    }

    /**
     * 
     * @return
     *     The referImage
     */
    public String getReferImage() {
        return referImage;
    }

    /**
     * 
     * @param referImage
     *     The refer_image
     */
    public void setReferImage(String referImage) {
        this.referImage = referImage;
    }

}
