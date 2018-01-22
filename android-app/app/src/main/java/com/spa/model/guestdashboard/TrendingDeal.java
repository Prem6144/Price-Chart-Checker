
package com.spa.model.guestdashboard;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class TrendingDeal {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("service_category_id")
    @Expose
    private Integer serviceCategoryId;
    @SerializedName("service_provider_id")
    @Expose
    private Integer serviceProviderId;
    @SerializedName("title")
    @Expose
    private String title;
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
    @SerializedName("is_nationwide")
    @Expose
    private Boolean isNationwide;
    @SerializedName("is_business")
    @Expose
    private String isBusiness;
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

    public String getEffective_price() {
        return effective_price;
    }

    public void setEffective_price(String effective_price) {
        this.effective_price = effective_price;
    }

    @SerializedName("effective_price")
    @Expose
    private String effective_price;

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
     *     The isNationwide
     */
    public Boolean getIsNationwide() {
        return isNationwide;
    }

    /**
     * 
     * @param isNationwide
     *     The is_nationwide
     */
    public void setIsNationwide(Boolean isNationwide) {
        this.isNationwide = isNationwide;
    }

    /**
     * 
     * @return
     *     The isBusiness
     */
    public String getIsBusiness() {
        return isBusiness;
    }

    /**
     * 
     * @param isBusiness
     *     The is_business
     */
    public void setIsBusiness(String isBusiness) {
        this.isBusiness = isBusiness;
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

}
