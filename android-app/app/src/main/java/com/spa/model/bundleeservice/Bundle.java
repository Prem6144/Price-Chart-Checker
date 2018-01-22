
package com.spa.model.bundleeservice;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Bundle {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("service_preference")
    @Expose
    private ServicePreference servicePreference;

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
     *     The servicePreference
     */
    public ServicePreference getServicePreference() {
        return servicePreference;
    }

    /**
     * 
     * @param servicePreference
     *     The service_preference
     */
    public void setServicePreference(ServicePreference servicePreference) {
        this.servicePreference = servicePreference;
    }

}
