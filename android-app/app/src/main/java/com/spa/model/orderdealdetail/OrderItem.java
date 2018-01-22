
package com.spa.model.orderdealdetail;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OrderItem {

    @SerializedName("deal")
    @Expose
    private Deal deal;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("deal_price")
    @Expose
    private Integer dealPrice;
    @SerializedName("effective_price")
    @Expose
    private Double effectivePrice;
    @SerializedName("activation_date")
    @Expose
    private String activationDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("you_save")
    @Expose
    private Object youSave;

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
    public Integer getDealPrice() {
        return dealPrice;
    }

    /**
     * 
     * @param dealPrice
     *     The deal_price
     */
    public void setDealPrice(Integer dealPrice) {
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
