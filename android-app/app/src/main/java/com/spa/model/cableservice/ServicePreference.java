
package com.spa.model.cableservice;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class ServicePreference {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_user_id")
    @Expose
    private String appUserId;
    @SerializedName("service_category_id")
    @Expose
    private Integer serviceCategoryId;
    @SerializedName("service_provider_id")
    @Expose
    private String serviceProviderId;
    @SerializedName("price")
    @Expose
    private int price;
    @SerializedName("is_contract")
    @Expose
    private Boolean isContract;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("plan_name")
    @Expose
    private String planName;
    @SerializedName("free_channels")
    @Expose
    private int freeChannels;
    @SerializedName("premium_channels")
    @Expose
    private String premiumChannels;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The appUserId
     */
    public String getAppUserId() {
        return appUserId;
    }

    /**
     * 
     * @param appUserId
     *     The app_user_id
     */
    public void setAppUserId(String appUserId) {
        this.appUserId = appUserId;
    }

    /**
     * 
     * @return
     *     The serviceCategoryId
     */
    public Integer getServiceCategoryId() {
        return serviceCategoryId;
    }

    /**
     * 
     * @param serviceCategoryId
     *     The service_category_id
     */
    public void setServiceCategoryId(Integer serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    /**
     * 
     * @return
     *     The serviceProviderId
     */
    public String getServiceProviderId() {
        return serviceProviderId;
    }

    /**
     * 
     * @param serviceProviderId
     *     The service_provider_id
     */
    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    /**
     * 
     * @return
     *     The price
     */
    public int getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The isContract
     */
    public Boolean getIsContract() {
        return isContract;
    }

    /**
     * 
     * @param isContract
     *     The is_contract
     */
    public void setIsContract(Boolean isContract) {
        this.isContract = isContract;
    }

    /**
     * 
     * @return
     *     The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * 
     * @param startDate
     *     The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * 
     * @return
     *     The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * 
     * @param endDate
     *     The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * 
     * @return
     *     The planName
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * 
     * @param planName
     *     The plan_name
     */
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    /**
     * 
     * @return
     *     The freeChannels
     */
    public int getFreeChannels() {
        return freeChannels;
    }

    /**
     * 
     * @param freeChannels
     *     The free_channels
     */
    public void setFreeChannels(int freeChannels) {
        this.freeChannels = freeChannels;
    }

    /**
     * 
     * @return
     *     The premiumChannels
     */
    public String getPremiumChannels() {
        return premiumChannels;
    }

    /**
     * 
     * @param premiumChannels
     *     The premium_channels
     */
    public void setPremiumChannels(String premiumChannels) {
        this.premiumChannels = premiumChannels;
    }

}
