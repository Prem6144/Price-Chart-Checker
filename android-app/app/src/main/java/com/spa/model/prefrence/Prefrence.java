
package com.spa.model.prefrence;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Prefrence {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("notification")
    @Expose
    private Notification notification;

    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The notification
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * 
     * @param notification
     *     The notification
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

}
