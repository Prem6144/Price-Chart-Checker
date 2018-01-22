
package com.spa.model.custemisecable;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DealEquipment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cable_deal_attribute_id")
    @Expose
    private Object cableDealAttributeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("installation")
    @Expose
    private String installation;
    @SerializedName("activation")
    @Expose
    private String activation;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("is_active")
    @Expose
    private Object isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("description")
    @Expose
    private Object description;

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
     *     The cableDealAttributeId
     */
    public Object getCableDealAttributeId() {
        return cableDealAttributeId;
    }

    /**
     * 
     * @param cableDealAttributeId
     *     The cable_deal_attribute_id
     */
    public void setCableDealAttributeId(Object cableDealAttributeId) {
        this.cableDealAttributeId = cableDealAttributeId;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The make
     */
    public String getMake() {
        return make;
    }

    /**
     * 
     * @param make
     *     The make
     */
    public void setMake(String make) {
        this.make = make;
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
     *     The installation
     */
    public String getInstallation() {
        return installation;
    }

    /**
     * 
     * @param installation
     *     The installation
     */
    public void setInstallation(String installation) {
        this.installation = installation;
    }

    /**
     * 
     * @return
     *     The activation
     */
    public String getActivation() {
        return activation;
    }

    /**
     * 
     * @param activation
     *     The activation
     */
    public void setActivation(String activation) {
        this.activation = activation;
    }

    /**
     * 
     * @return
     *     The offer
     */
    public String getOffer() {
        return offer;
    }

    /**
     * 
     * @param offer
     *     The offer
     */
    public void setOffer(String offer) {
        this.offer = offer;
    }

    /**
     * 
     * @return
     *     The isActive
     */
    public Object getIsActive() {
        return isActive;
    }

    /**
     * 
     * @param isActive
     *     The is_active
     */
    public void setIsActive(Object isActive) {
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
     *     The description
     */
    public Object getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(Object description) {
        this.description = description;
    }

}
