
package com.spa.model.orderdetail;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Deal {

    @SerializedName("deal_equipments")
    @Expose
    private List<DealEquipment> dealEquipments = new ArrayList<DealEquipment>();
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
    @SerializedName("deal_image_url")
    @Expose
    private String dealImageUrl;

    /**
     * 
     * @return
     *     The dealEquipments
     */
    public List<DealEquipment> getDealEquipments() {
        return dealEquipments;
    }

    /**
     * 
     * @param dealEquipments
     *     The deal_equipments
     */
    public void setDealEquipments(List<DealEquipment> dealEquipments) {
        this.dealEquipments = dealEquipments;
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
