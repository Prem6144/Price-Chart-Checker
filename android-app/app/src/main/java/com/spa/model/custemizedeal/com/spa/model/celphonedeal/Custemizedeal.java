
package com.spa.model.custemizedeal.com.spa.model.celphonedeal;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Custemizedeal {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("deals")
    @Expose
    private List<Deal> deals = new ArrayList<Deal>();

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
     *     The deals
     */
    public List<Deal> getDeals() {
        return deals;
    }

    /**
     * 
     * @param deals
     *     The deals
     */
    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

}
