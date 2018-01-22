
package com.spa.model.custemizedeal;

import javax.annotation.Generated;

import com.google.android.gms.ads.formats.NativeAd;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static com.google.android.gms.ads.formats.NativeAd.*;

@Generated("org.jsonschema2pojo")
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
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("short_description")
    @Expose
    private String shortDescription;
    @SerializedName("detail_description")
    @Expose
    private String detailDescription;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("is_contract")
    @Expose
    private Boolean isContract;
    @SerializedName("contract_period")
    @Expose
    private Integer contractPeriod;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image")
    @Expose
    private Image image;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("is_nationwide")
    @Expose
    private Boolean isNationwide;
    @SerializedName("deal_type")
    @Expose
    private String dealType;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_sponsored")
    @Expose
    private Boolean isSponsored;
    @SerializedName("effective_price")
    @Expose
    private Object effectivePrice;
    @SerializedName("is_customisable")
    @Expose
    private Boolean isCustomisable;
    @SerializedName("service_category_name")
    @Expose
    private String serviceCategoryName;
    @SerializedName("service_provider_name")
    @Expose
    private String serviceProviderName;

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
     *     The price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(Double price) {
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
     *     The contractPeriod
     */
    public Integer getContractPeriod() {
        return contractPeriod;
    }

    /**
     * 
     * @param contractPeriod
     *     The contract_period
     */
    public void setContractPeriod(Integer contractPeriod) {
        this.contractPeriod = contractPeriod;
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
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Image image) {
        this.image = image;
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
     *     The dealType
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * 
     * @param dealType
     *     The deal_type
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
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
     *     The isSponsored
     */
    public Boolean getIsSponsored() {
        return isSponsored;
    }

    /**
     * 
     * @param isSponsored
     *     The is_sponsored
     */
    public void setIsSponsored(Boolean isSponsored) {
        this.isSponsored = isSponsored;
    }

    /**
     * 
     * @return
     *     The effectivePrice
     */
    public Object getEffectivePrice() {
        return effectivePrice;
    }

    /**
     * 
     * @param effectivePrice
     *     The effective_price
     */
    public void setEffectivePrice(Object effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    /**
     * 
     * @return
     *     The isCustomisable
     */
    public Boolean getIsCustomisable() {
        return isCustomisable;
    }

    /**
     * 
     * @param isCustomisable
     *     The is_customisable
     */
    public void setIsCustomisable(Boolean isCustomisable) {
        this.isCustomisable = isCustomisable;
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

}
