
package com.spa.model.myorder;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Order {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deal_price")
    @Expose
    private String dealPrice;
    @SerializedName("effective_price")
    @Expose
    private String effectivePrice;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("order_type")
    @Expose
    private Integer orderType;
    @SerializedName("security_deposit")
    @Expose
    private Object securityDeposit;
    @SerializedName("order_place_time")
    @Expose
    private String orderPlaceTime;
    @SerializedName("deal")
    @Expose
    private Deal deal;

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
    public String getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
     *     The appUserId
     */
    public Integer getAppUserId() {
        return appUserId;
    }

    /**
     * 
     * @param appUserId
     *     The app_user_id
     */
    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
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

    /**
     * 
     * @return
     *     The effectivePrice
     */
    public String getEffectivePrice() {
        return effectivePrice;
    }

    /**
     * 
     * @param effectivePrice
     *     The effective_price
     */
    public void setEffectivePrice(String effectivePrice) {
        this.effectivePrice = effectivePrice;
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
     *     The orderNumber
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * 
     * @param orderNumber
     *     The order_number
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * 
     * @return
     *     The orderType
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 
     * @param orderType
     *     The order_type
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 
     * @return
     *     The securityDeposit
     */
    public Object getSecurityDeposit() {
        return securityDeposit;
    }

    /**
     * 
     * @param securityDeposit
     *     The security_deposit
     */
    public void setSecurityDeposit(Object securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    /**
     * 
     * @return
     *     The orderPlaceTime
     */
    public String getOrderPlaceTime() {
        return orderPlaceTime;
    }

    /**
     * 
     * @param orderPlaceTime
     *     The order_place_time
     */
    public void setOrderPlaceTime(String orderPlaceTime) {
        this.orderPlaceTime = orderPlaceTime;
    }

    /**
     * 
     * @return
     *     The deal
     */
    public Deal getDeal() {
        return deal;
    }

    /**
     * 
     * @param deal
     *     The deal
     */
    public void setDeal(Deal deal) {
        this.deal = deal;
    }

}
