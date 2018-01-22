
package com.spa.model.confirmationorder;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OrderItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("ref_number")
    @Expose
    private Object refNumber;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("deal_price")
    @Expose
    private Double dealPrice;
    @SerializedName("effective_price")
    @Expose
    private Double effectivePrice;
    @SerializedName("discount_price")
    @Expose
    private Object discountPrice;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("you_save")
    @Expose
    private Object youSave;

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
     *     The orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The refNumber
     */
    public Object getRefNumber() {
        return refNumber;
    }

    /**
     * 
     * @param refNumber
     *     The ref_number
     */
    public void setRefNumber(Object refNumber) {
        this.refNumber = refNumber;
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
     *     The dealPrice
     */
    public Double getDealPrice() {
        return dealPrice;
    }

    /**
     * 
     * @param dealPrice
     *     The deal_price
     */
    public void setDealPrice(Double dealPrice) {
        this.dealPrice = dealPrice;
    }

    /**
     * 
     * @return
     *     The effectivePrice
     */
    public Double getEffectivePrice() {
        return effectivePrice;
    }

    /**
     * 
     * @param effectivePrice
     *     The effective_price
     */
    public void setEffectivePrice(Double effectivePrice) {
        this.effectivePrice = effectivePrice;
    }

    /**
     * 
     * @return
     *     The discountPrice
     */
    public Object getDiscountPrice() {
        return discountPrice;
    }

    /**
     * 
     * @param discountPrice
     *     The discount_price
     */
    public void setDiscountPrice(Object discountPrice) {
        this.discountPrice = discountPrice;
    }

    /**
     * 
     * @return
     *     The activationDate
     */
    public String getActivationDate() {
        return activationDate;
    }

    /**
     * 
     * @param activationDate
     *     The activation_date
     */
    public void setActivationDate(String activationDate) {
        this.activationDate = activationDate;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
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
     *     The youSave
     */
    public Object getYouSave() {
        return youSave;
    }

    /**
     * 
     * @param youSave
     *     The you_save
     */
    public void setYouSave(Object youSave) {
        this.youSave = youSave;
    }

}
