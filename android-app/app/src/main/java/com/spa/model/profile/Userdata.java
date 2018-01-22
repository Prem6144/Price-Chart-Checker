
package com.spa.model.profile;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Userdata {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("app_user")
    @Expose
    private AppUser appUser;
    @SerializedName("user_preference")
    @Expose
    private Boolean userPreference;
    @SerializedName("app_user_addresses")
    @Expose
    private AppUserAddresses appUserAddresses;
    @SerializedName("business")
    @Expose
    private Business business;

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
     *     The appUser
     */
    public AppUser getAppUser() {
        return appUser;
    }

    /**
     * 
     * @param appUser
     *     The app_user
     */
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * 
     * @return
     *     The userPreference
     */
    public Boolean getUserPreference() {
        return userPreference;
    }

    /**
     * 
     * @param userPreference
     *     The user_preference
     */
    public void setUserPreference(Boolean userPreference) {
        this.userPreference = userPreference;
    }

    /**
     * 
     * @return
     *     The appUserAddresses
     */
    public AppUserAddresses getAppUserAddresses() {
        return appUserAddresses;
    }

    /**
     * 
     * @param appUserAddresses
     *     The app_user_addresses
     */
    public void setAppUserAddresses(AppUserAddresses appUserAddresses) {
        this.appUserAddresses = appUserAddresses;
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

}
