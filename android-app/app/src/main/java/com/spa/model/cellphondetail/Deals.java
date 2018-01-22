
package com.spa.model.cellphondetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Deals {
    @SerializedName("is_order_available")
    @Expose
    private Boolean isOrderAvailable;
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
    @SerializedName("is_contract")
    @Expose
    private Boolean isContract;
    @SerializedName("contract_period")
    @Expose
    private Integer contractPeriod;
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
    @SerializedName("deal_type")
    @Expose
    private String dealType;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("is_sponsored")
    @Expose
    private Boolean isSponsored;
    @SerializedName("effective_price")
    @Expose
    private String effectivePrice;
    @SerializedName("is_customisable")
    @Expose
    private Boolean isCustomisable;
    @SerializedName("deal_image_url")
    @Expose
    private String dealImageUrl;
    @SerializedName("average_rating")
    @Expose
    private Double averageRating;
    @SerializedName("rating_count")
    @Expose
    private Integer ratingCount;
    @SerializedName("deal_price")
    @Expose
    private String dealPrice;
    @SerializedName("service_category_name")
    @Expose
    private String serviceCategoryName;
    @SerializedName("service_provider_name")
    @Expose
    private String serviceProviderName;
    @SerializedName("deal_additional_offers")
    @Expose
    private Object dealAdditionalOffers;
    @SerializedName("deal_attributes")
    @Expose
    private List<DealAttribute> dealAttributes = new ArrayList<DealAttribute>();
    @SerializedName("deal_equipments")
    @Expose
    private List<DealEquipment> dealEquipments = new ArrayList<DealEquipment>();
    @SerializedName("deal_extra_services")
    @Expose
    private List<DealExtraService> dealExtraServices = new ArrayList<DealExtraService>();

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
     * @return The isOrderAvailable
     */
    public Boolean getIsOrderAvailable() {
        return isOrderAvailable;
    }

    /**
     * @param isOrderAvailable The is_order_available
     */
    public void setIsOrderAvailable(Boolean isOrderAvailable) {
        this.isOrderAvailable = isOrderAvailable;
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
     * @return The isContract
     */
    public Boolean getIsContract() {
        return isContract;
    }

    /**
     * @param isContract The is_contract
     */
    public void setIsContract(Boolean isContract) {
        this.isContract = isContract;
    }

    /**
     * @return The contractPeriod
     */
    public Integer getContractPeriod() {
        return contractPeriod;
    }

    /**
     * @param contractPeriod The contract_period
     */
    public void setContractPeriod(Integer contractPeriod) {
        this.contractPeriod = contractPeriod;
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
     * @return The isNationwide
     */
    public Boolean getIsNationwide() {
        return isNationwide;
    }

    /**
     * @param isNationwide The is_nationwide
     */
    public void setIsNationwide(Boolean isNationwide) {
        this.isNationwide = isNationwide;
    }

    /**
     * @return The dealType
     */
    public String getDealType() {
        return dealType;
    }

    /**
     * @param dealType The deal_type
     */
    public void setDealType(String dealType) {
        this.dealType = dealType;
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
     * @return The isSponsored
     */
    public Boolean getIsSponsored() {
        return isSponsored;
    }

    /**
     * @param isSponsored The is_sponsored
     */
    public void setIsSponsored(Boolean isSponsored) {
        this.isSponsored = isSponsored;
    }

    /**
     * @return The effectivePrice
     */
    public String getEffectivePrice() {
        return effectivePrice;
    }

    /**
     * @param effectivePrice The effective_price
     */
    public void setEffectivePrice(String effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    /**
     * @return The isCustomisable
     */
    public Boolean getIsCustomisable() {
        return isCustomisable;
    }

    /**
     * @param isCustomisable The is_customisable
     */
    public void setIsCustomisable(Boolean isCustomisable) {
        this.isCustomisable = isCustomisable;
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
    public Double getAverageRating() {
        return averageRating;
    }

    /**
     * @param averageRating The average_rating
     */
    public void setAverageRating(Double averageRating) {
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
     * @return The dealAdditionalOffers
     */
    public Object getDealAdditionalOffers() {
        return dealAdditionalOffers;
    }

    /**
     * @param dealAdditionalOffers The deal_additional_offers
     */
    public void setDealAdditionalOffers(Object dealAdditionalOffers) {
        this.dealAdditionalOffers = dealAdditionalOffers;
    }

    /**
     * @return The dealAttributes
     */
    public List<DealAttribute> getDealAttributes() {
        return dealAttributes;
    }

    /**
     * @param dealAttributes The deal_attributes
     */
    public void setDealAttributes(List<DealAttribute> dealAttributes) {
        this.dealAttributes = dealAttributes;
    }

    /**
     * @return The dealEquipments
     */
    public List<DealEquipment> getDealEquipments() {
        return dealEquipments;
    }

    /**
     * @param dealEquipments The deal_equipments
     */
    public void setDealEquipments(List<DealEquipment> dealEquipments) {
        this.dealEquipments = dealEquipments;
    }

    /**
     * @return The dealExtraServices
     */
    public List<DealExtraService> getDealExtraServices() {
        return dealExtraServices;
    }

    /**
     * @param dealExtraServices The deal_extra_services
     */
    public void setDealExtraServices(List<DealExtraService> dealExtraServices) {
        this.dealExtraServices = dealExtraServices;
    }

}
