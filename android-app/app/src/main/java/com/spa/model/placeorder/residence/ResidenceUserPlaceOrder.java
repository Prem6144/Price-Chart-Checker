
package com.spa.model.placeorder.residence;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ResidenceUserPlaceOrder {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("app_users")
    @Expose
    private AppUsers appUsers;
    @SerializedName("app_user_addresses")
    @Expose
    private List<AppUserAddress> appUserAddresses = new ArrayList<AppUserAddress>();
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
     *     The appUserAddresses
     */
    public List<AppUserAddress> getAppUserAddresses() {
        return appUserAddresses;
    }

    /**
     * 
     * @param appUserAddresses
     *     The app_user_addresses
     */
    public void setAppUserAddresses(List<AppUserAddress> appUserAddresses) {
        this.appUserAddresses = appUserAddresses;
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
