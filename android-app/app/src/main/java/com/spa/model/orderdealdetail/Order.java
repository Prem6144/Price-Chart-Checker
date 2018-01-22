
package com.spa.model.orderdealdetail;

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
    private Object dealId;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("deal_price")
    @Expose
    private Object dealPrice;
    @SerializedName("effective_price")
    @Expose
    private Object effectivePrice;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("order_type")
    @Expose
    private Integer orderType;
    @SerializedName("security_deposit")
    @Expose
    private Object securityDeposit;
    @SerializedName("primary_id")
    @Expose
    private String primaryId;
    @SerializedName("secondary_id")
    @Expose
    private String secondaryId;
    @SerializedName("primary_id_number")
    @Expose
    private String primaryIdNumber;
    @SerializedName("secondary_id_number")
    @Expose
    private String secondaryIdNumber;
    @SerializedName("free_text")
    @Expose
    private Object freeText;

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
    public Object getDealId() {
        return dealId;
    }

    /**
     * 
     * @param dealId
     *     The deal_id
     */
    public void setDealId(Object dealId) {
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
    public Object getDealPrice() {
        return dealPrice;
    }

    /**
     * 
     * @param dealPrice
     *     The deal_price
     */
    public void setDealPrice(Object dealPrice) {
        this.dealPrice = dealPrice;
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
     *     The primaryId
     */
    public String getPrimaryId() {
        return primaryId;
    }

    /**
     * 
     * @param primaryId
     *     The primary_id
     */
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * 
     * @return
     *     The secondaryId
     */
    public String getSecondaryId() {
        return secondaryId;
    }

    /**
     * 
     * @param secondaryId
     *     The secondary_id
     */
    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }

    /**
     * 
     * @return
     *     The primaryIdNumber
     */
    public String getPrimaryIdNumber() {
        return primaryIdNumber;
    }

    /**
     * 
     * @param primaryIdNumber
     *     The primary_id_number
     */
    public void setPrimaryIdNumber(String primaryIdNumber) {
        this.primaryIdNumber = primaryIdNumber;
    }

    /**
     * 
     * @return
     *     The secondaryIdNumber
     */
    public String getSecondaryIdNumber() {
        return secondaryIdNumber;
    }

    /**
     * 
     * @param secondaryIdNumber
     *     The secondary_id_number
     */
    public void setSecondaryIdNumber(String secondaryIdNumber) {
        this.secondaryIdNumber = secondaryIdNumber;
    }

    /**
     * 
     * @return
     *     The freeText
     */
    public Object getFreeText() {
        return freeText;
    }

    /**
     * 
     * @param freeText
     *     The free_text
     */
    public void setFreeText(Object freeText) {
        this.freeText = freeText;
    }

}
