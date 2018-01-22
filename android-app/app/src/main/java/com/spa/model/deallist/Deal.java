
package com.spa.model.deallist;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("service_category_id")
    @Expose
    private Integer serviceCategoryId;
    @SerializedName("service_provider_id")
    @Expose
    private Integer serviceProviderId;
    @SerializedName("service_category_name")
    @Expose
    private String serviceCategoryName;
    @SerializedName("service_provider_name")
    @Expose
    private String serviceProviderName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("detail_description")
    @Expose
    private String detailDescription;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("free_channels")
    @Expose
    private String freeChannels;
    @SerializedName("premium_channels")
    @Expose
    private String premiumChannels;
    @SerializedName("domestic_call_minutes")
    @Expose
    private String domesticCallMinutes;
    @SerializedName("international_call_minutes")
    @Expose
    private String internationalCallMinutes;
    @SerializedName("domestic_call_unlimited")
    @Expose
    private String domesticCallUnlimited;
    @SerializedName("international_call_unlimited")
    @Expose
    private String internationalCallUnlimited;
    @SerializedName("data_plan")
    @Expose
    private String dataPlan;
    @SerializedName("data_speed")
    @Expose
    private String dataSpeed;
    @SerializedName("upload_speed")
    @Expose
    private String uploadSpeed;
    @SerializedName("download_speed")
    @Expose
    private String downloadSpeed;
    @SerializedName("bundle_combo")
    @Expose
    private String bundleCombo;
    @SerializedName("deal_image_url")
    @Expose
    private String dealImageUrl;
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("rating_count")
    @Expose
    private Integer ratingCount;
    @SerializedName("deal_price")
    @Expose
    private String dealPrice;
    @SerializedName("is_customisable")
    @Expose
    private String customisable;

    public String getCustomisable() {
        return customisable;
    }

    public void setCustomisable(String customisable) {
        this.customisable = customisable;
    }




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
    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    /**
     * 
     * @param serviceProviderId
     *     The service_provider_id
     */
    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    /**
     * 
     * @return
     *     The serviceCategoryName
     */
    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    /**
     * 
     * @param serviceCategoryName
     *     The service_category_name
     */
    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    /**
     * 
     * @return
     *     The serviceProviderName
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * 
     * @param serviceProviderName
     *     The service_provider_name
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * 
     * @param zip
     *     The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return
     *     The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * 
     * @param shortDescription
     *     The short_description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * 
     * @return
     *     The detailDescription
     */
    public String getDetailDescription() {
        return detailDescription;
    }

    /**
     * 
     * @param detailDescription
     *     The detail_description
     */
    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
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
     *     The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * 
     * @param isActive
     *     The is_active
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * 
     * @return
     *     The freeChannels
     */
    public String getFreeChannels() {
        return freeChannels;
    }

    /**
     * 
     * @param freeChannels
     *     The free_channels
     */
    public void setFreeChannels(String freeChannels) {
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

    /**
     * 
     * @return
     *     The domesticCallMinutes
     */
    public String getDomesticCallMinutes() {
        return domesticCallMinutes;
    }

    /**
     * 
     * @param domesticCallMinutes
     *     The domestic_call_minutes
     */
    public void setDomesticCallMinutes(String domesticCallMinutes) {
        this.domesticCallMinutes = domesticCallMinutes;
    }

    /**
     * 
     * @return
     *     The internationalCallMinutes
     */
    public String getInternationalCallMinutes() {
        return internationalCallMinutes;
    }

    /**
     * 
     * @param internationalCallMinutes
     *     The international_call_minutes
     */
    public void setInternationalCallMinutes(String internationalCallMinutes) {
        this.internationalCallMinutes = internationalCallMinutes;
    }

    /**
     * 
     * @return
     *     The domesticCallUnlimited
     */
    public String getDomesticCallUnlimited() {
        return domesticCallUnlimited;
    }

    /**
     * 
     * @param domesticCallUnlimited
     *     The domestic_call_unlimited
     */
    public void setDomesticCallUnlimited(String domesticCallUnlimited) {
        this.domesticCallUnlimited = domesticCallUnlimited;
    }

    /**
     * 
     * @return
     *     The internationalCallUnlimited
     */
    public String getInternationalCallUnlimited() {
        return internationalCallUnlimited;
    }

    /**
     * 
     * @param internationalCallUnlimited
     *     The international_call_unlimited
     */
    public void setInternationalCallUnlimited(String internationalCallUnlimited) {
        this.internationalCallUnlimited = internationalCallUnlimited;
    }

    /**
     * 
     * @return
     *     The dataPlan
     */
    public String getDataPlan() {
        return dataPlan;
    }

    /**
     * 
     * @param dataPlan
     *     The data_plan
     */
    public void setDataPlan(String dataPlan) {
        this.dataPlan = dataPlan;
    }

    /**
     * 
     * @return
     *     The dataSpeed
     */
    public String getDataSpeed() {
        return dataSpeed;
    }

    /**
     * 
     * @param dataSpeed
     *     The data_speed
     */
    public void setDataSpeed(String dataSpeed) {
        this.dataSpeed = dataSpeed;
    }

    /**
     * 
     * @return
     *     The uploadSpeed
     */
    public String getUploadSpeed() {
        return uploadSpeed;
    }

    /**
     * 
     * @param uploadSpeed
     *     The upload_speed
     */
    public void setUploadSpeed(String uploadSpeed) {
        this.uploadSpeed = uploadSpeed;
    }

    /**
     * 
     * @return
     *     The downloadSpeed
     */
    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    /**
     * 
     * @param downloadSpeed
     *     The download_speed
     */
    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    /**
     * 
     * @return
     *     The bundleCombo
     */
    public String getBundleCombo() {
        return bundleCombo;
    }

    /**
     * 
     * @param bundleCombo
     *     The bundle_combo
     */
    public void setBundleCombo(String bundleCombo) {
        this.bundleCombo = bundleCombo;
    }

    /**
     * 
     * @return
     *     The dealImageUrl
     */
    public String getDealImageUrl() {
        return dealImageUrl;
    }

    /**
     * 
     * @param dealImageUrl
     *     The deal_image_url
     */
    public void setDealImageUrl(String dealImageUrl) {
        this.dealImageUrl = dealImageUrl;
    }

    /**
     * 
     * @return
     *     The averageRating
     */
    public String getAverageRating() {
        return averageRating;
    }

    /**
     * 
     * @param averageRating
     *     The average_rating
     */
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * 
     * @return
     *     The ratingCount
     */
    public Integer getRatingCount() {
        return ratingCount;
    }

    /**
     * 
     * @param ratingCount
     *     The rating_count
     */
    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    /**
     * 
     * @return
     *     The dealPrice
     */
    public String getDealPrice() {
        return dealPrice;
    }

    /**
     * 
     * @param dealPrice
     *     The deal_price
     */
    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
