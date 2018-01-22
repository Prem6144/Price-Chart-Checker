
package com.spa.model.custemizedealdetail;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DealEquipment {

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean isSelected=false;


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cellphone_deal_attribute_id")
    @Expose
    private Object cellphoneDealAttributeId;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("memory")
    @Expose
    private Integer memory;
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
    private Object offer;
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
    @SerializedName("cellphone_detail_id")
    @Expose
    private Integer cellphoneDetailId;
    @SerializedName("available_color")
    @Expose
    private List<AvailableColor> availableColor = new ArrayList<AvailableColor>();
    @SerializedName("cellphone_name")
    @Expose
    private String cellphoneName;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("description")
    @Expose
    private String description;

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
     *     The cellphoneDealAttributeId
     */
    public Object getCellphoneDealAttributeId() {
        return cellphoneDealAttributeId;
    }

    /**
     * 
     * @param cellphoneDealAttributeId
     *     The cellphone_deal_attribute_id
     */
    public void setCellphoneDealAttributeId(Object cellphoneDealAttributeId) {
        this.cellphoneDealAttributeId = cellphoneDealAttributeId;
    }

    /**
     * 
     * @return
     *     The model
     */
    public String getModel() {
        return model;
    }

    /**
     * 
     * @param model
     *     The model
     */
    public void setModel(String model) {
        this.model = model;
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
     *     The memory
     */
    public Integer getMemory() {
        return memory;
    }

    /**
     * 
     * @param memory
     *     The memory
     */
    public void setMemory(Integer memory) {
        this.memory = memory;
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
    public Object getOffer() {
        return offer;
    }

    /**
     * 
     * @param offer
     *     The offer
     */
    public void setOffer(Object offer) {
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
     *     The cellphoneDetailId
     */
    public Integer getCellphoneDetailId() {
        return cellphoneDetailId;
    }

    /**
     * 
     * @param cellphoneDetailId
     *     The cellphone_detail_id
     */
    public void setCellphoneDetailId(Integer cellphoneDetailId) {
        this.cellphoneDetailId = cellphoneDetailId;
    }

    /**
     * 
     * @return
     *     The availableColor
     */
    public List<AvailableColor> getAvailableColor() {
        return availableColor;
    }

    /**
     * 
     * @param availableColor
     *     The available_color
     */
    public void setAvailableColor(List<AvailableColor> availableColor) {
        this.availableColor = availableColor;
    }

    /**
     * 
     * @return
     *     The cellphoneName
     */
    public String getCellphoneName() {
        return cellphoneName;
    }

    /**
     * 
     * @param cellphoneName
     *     The cellphone_name
     */
    public void setCellphoneName(String cellphoneName) {
        this.cellphoneName = cellphoneName;
    }

    /**
     * 
     * @return
     *     The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 
     * @param brand
     *     The brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
