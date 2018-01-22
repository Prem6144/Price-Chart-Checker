
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DealExtraService {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("extra_service_id")
    @Expose
    private Integer extraServiceId;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("service_term")
    @Expose
    private Integer serviceTerm;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_description")
    @Expose
    private String serviceDescription;

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
     *     The extraServiceId
     */
    public Integer getExtraServiceId() {
        return extraServiceId;
    }

    /**
     * 
     * @param extraServiceId
     *     The extra_service_id
     */
    public void setExtraServiceId(Integer extraServiceId) {
        this.extraServiceId = extraServiceId;
    }

    /**
     * 
     * @return
     *     The dealId
     */
    public Integer getDealId() {
        return dealId;
    }

    /**
     * 
     * @param dealId
     *     The deal_id
     */
    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The serviceTerm
     */
    public Integer getServiceTerm() {
        return serviceTerm;
    }

    /**
     * 
     * @param serviceTerm
     *     The service_term
     */
    public void setServiceTerm(Integer serviceTerm) {
        this.serviceTerm = serviceTerm;
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
     *     The serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * 
     * @param serviceName
     *     The service_name
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * 
     * @return
     *     The serviceDescription
     */
    public String getServiceDescription() {
        return serviceDescription;
    }

    /**
     * 
     * @param serviceDescription
     *     The service_description
     */
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

}
