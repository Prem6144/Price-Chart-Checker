
package com.spa.model.bundleservice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Bundle {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("service_provider")
    @Expose
    private List<ServiceProvider> serviceProvider = new ArrayList<ServiceProvider>();

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
     *     The serviceProvider
     */
    public List<ServiceProvider> getServiceProvider() {
        return serviceProvider;
    }

    /**
     * 
     * @param serviceProvider
     *     The service_provider
     */
    public void setServiceProvider(List<ServiceProvider> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

}
