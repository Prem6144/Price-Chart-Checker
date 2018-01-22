package com.spa.model.dashbordmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

public class PreferredDeal {

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
    @Expose
    private String title;
    @Expose
    private String state;
    @Expose
    private String city;
    @Expose
    private String zip;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("detail_description")
    @Expose
    private String detailDescription;
    @Expose
    private String url;
    @SerializedName("you_save_text")
    @Expose
    private String youSaveText;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
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

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The serviceCategoryId
     */
    public Integer getServiceCategoryId() {
        return serviceCategoryId;
    }

    /**
     * @param serviceCategoryId The service_category_id
     */
    public void setServiceCategoryId(Integer serviceCategoryId) {
        this.serviceCategoryId = serviceCategoryId;
    }

    /**
     * @return The serviceProviderId
     */
    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    /**
     * @param serviceProviderId The service_provider_id
     */
    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    /**
     * @return The serviceCategoryName
     */
    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    /**
     * @param serviceCategoryName The service_category_name
     */
    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    /**
     * @return The serviceProviderName
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * @param serviceProviderName The service_provider_name
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return The shortDescription
     */
    public String getShortDescription() {
        return shortDescription;
    }

    /**
     * @param shortDescription The short_description
     */
    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    /**
     * @return The detailDescription
     */
    public String getDetailDescription() {
        return detailDescription;
    }

    /**
     * @param detailDescription The detail_description
     */
    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The youSaveText
     */
    public String getYouSaveText() {
        return youSaveText;
    }

    /**
     * @param youSaveText The you_save_text
     */
    public void setYouSaveText(String youSaveText) {
        this.youSaveText = youSaveText;
    }

    /**
     * @return The startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate The start_date
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return The endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate The end_date
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return The isActive
     */
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * @param isActive The is_active
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * @return The dealImageUrl
     */
    public String getDealImageUrl() {
        return dealImageUrl;
    }

    /**
     * @param dealImageUrl The deal_image_url
     */
    public void setDealImageUrl(String dealImageUrl) {
        this.dealImageUrl = dealImageUrl;
    }

    /**
     * @return The averageRating
     */
    public String getAverageRating() {
        return averageRating;
    }

    /**
     * @param averageRating The average_rating
     */
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * @return The ratingCount
     */
    public Integer getRatingCount() {
        return ratingCount;
    }

    /**
     * @param ratingCount The rating_count
     */
    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }

    /**
     * @return The dealPrice
     */
    public String getDealPrice() {
        return dealPrice;
    }

    /**
     * @param dealPrice The deal_price
     */
    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

}
