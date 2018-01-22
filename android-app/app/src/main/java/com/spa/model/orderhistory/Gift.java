
package com.spa.model.orderhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Gift {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("amount")
    @Expose
    private int amount;
    @SerializedName("activation_count_condition")
    @Expose
    private Integer activationCountCondition;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("gift_image_url")
    @Expose
    private String giftImageUrl;

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
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * @param amount The amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * @return The activationCountCondition
     */
    public Integer getActivationCountCondition() {
        return activationCountCondition;
    }

    /**
     * @param activationCountCondition The activation_count_condition
     */
    public void setActivationCountCondition(Integer activationCountCondition) {
        this.activationCountCondition = activationCountCondition;
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
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return The giftImageUrl
     */
    public String getGiftImageUrl() {
        return giftImageUrl;
    }

    /**
     * @param giftImageUrl The gift_image_url
     */
    public void setGiftImageUrl(String giftImageUrl) {
        this.giftImageUrl = giftImageUrl;
    }

}
