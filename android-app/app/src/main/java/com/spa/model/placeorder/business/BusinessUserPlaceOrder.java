
package com.spa.model.placeorder.business;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class BusinessUserPlaceOrder {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("app_users")
    @Expose
    private AppUsers appUsers;
    @SerializedName("business")
    @Expose
    private Business business;
    @SerializedName("business_addresses")
    @Expose
    private List<BusinessAddress> businessAddresses = new ArrayList<BusinessAddress>();
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
     *     The appUsers
     */
    public AppUsers getAppUsers() {
        return appUsers;
    }

    /**
     * 
     * @param appUsers
     *     The app_users
     */
    public void setAppUsers(AppUsers appUsers) {
        this.appUsers = appUsers;
    }

    /**
     * 
     * @return
     *     The business
     */
    public Business getBusiness() {
        return business;
    }

    /**
     * 
     * @param business
     *     The business
     */
    public void setBusiness(Business business) {
        this.business = business;
    }

    /**
     * 
     * @return
     *     The businessAddresses
     */
    public List<BusinessAddress> getBusinessAddresses() {
        return businessAddresses;
    }

    /**
     * 
     * @param businessAddresses
     *     The business_addresses
     */
    public void setBusinessAddresses(List<BusinessAddress> businessAddresses) {
        this.businessAddresses = businessAddresses;
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
