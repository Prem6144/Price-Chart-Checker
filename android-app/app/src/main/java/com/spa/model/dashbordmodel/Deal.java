
package com.spa.model.dashbordmodel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deal {

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
    private String deal_price;
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
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deal_image_file_name")
    @Expose
    private String dealImageFileName;
    @SerializedName("deal_image_content_type")
    @Expose
    private String dealImageContentType;
    @SerializedName("deal_image_file_size")
    @Expose
    private String dealImageFileSize;
    @SerializedName("deal_image_updated_at")
    @Expose
    private String dealImageUpdatedAt;
    @SerializedName("deal_image_url")
    @Expose
    private String dealImageUrl;

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
     *     The price
     */
    public String getPrice() {
        return deal_price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.deal_price = price;
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
     *     The youSaveText
     */
    public String getYouSaveText() {
        return youSaveText;
    }

    /**
     * 
     * @param youSaveText
     *     The you_save_text
     */
    public void setYouSaveText(String youSaveText) {
        this.youSaveText = youSaveText;
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
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The dealImageFileName
     */
    public String getDealImageFileName() {
        return dealImageFileName;
    }

    /**
     * 
     * @param dealImageFileName
     *     The deal_image_file_name
     */
    public void setDealImageFileName(String dealImageFileName) {
        this.dealImageFileName = dealImageFileName;
    }

    /**
     * 
     * @return
     *     The dealImageContentType
     */
    public String getDealImageContentType() {
        return dealImageContentType;
    }

    /**
     * 
     * @param dealImageContentType
     *     The deal_image_content_type
     */
    public void setDealImageContentType(String dealImageContentType) {
        this.dealImageContentType = dealImageContentType;
    }

    /**
     * 
     * @return
     *     The dealImageFileSize
     */
    public String getDealImageFileSize() {
        return dealImageFileSize;
    }

    /**
     * 
     * @param dealImageFileSize
     *     The deal_image_file_size
     */
    public void setDealImageFileSize(String dealImageFileSize) {
        this.dealImageFileSize = dealImageFileSize;
    }

    /**
     * 
     * @return
     *     The dealImageUpdatedAt
     */
    public String getDealImageUpdatedAt() {
        return dealImageUpdatedAt;
    }

    /**
     * 
     * @param dealImageUpdatedAt
     *     The deal_image_updated_at
     */
    public void setDealImageUpdatedAt(String dealImageUpdatedAt) {
        this.dealImageUpdatedAt = dealImageUpdatedAt;
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

}
