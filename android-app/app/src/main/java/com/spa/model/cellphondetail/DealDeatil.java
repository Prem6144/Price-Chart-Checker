
package com.spa.model.cellphondetail;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DealDeatil {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("deals")
    @Expose
    private Deals deals;

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
    public Deals getDeals() {
        return deals;
    }

    /**
     * 
     * @param deals
     *     The deals
     */
    public void setDeals(Deals deals) {
        this.deals = deals;
    }

}
