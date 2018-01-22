
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CustemiseCellphone {

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
