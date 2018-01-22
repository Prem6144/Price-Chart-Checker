
package com.spa.model.orderhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class MyEarnings {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("account_referral")
    @Expose
    private List<AccountReferral> accountReferral = new ArrayList<AccountReferral>();
    @SerializedName("gifts")
    @Expose
    private List<Gift> gifts = new ArrayList<Gift>();
    @SerializedName("total_referral_amount")
    @Expose
    private int totalReferralAmount;
    @SerializedName("gift_amount")
    @Expose
    private int giftAmount;
    @SerializedName("total_amount")
    @Expose
    private int totalAmount;
    @SerializedName("redeem_amount")
    @Expose
    private int redeemAmount;

    /**
     * @return The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * @param success The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * @return The accountReferral
     */
    public List<AccountReferral> getAccountReferral() {
        return accountReferral;
    }

    /**
     * @param accountReferral The account_referral
     */
    public void setAccountReferral(List<AccountReferral> accountReferral) {
        this.accountReferral = accountReferral;
    }

    /**
     * @return The gifts
     */
    public List<Gift> getGifts() {
        return gifts;
    }

    /**
     * @param gifts The gifts
     */
    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

    /**
     * @return The totalReferralAmount
     */
    public int getTotalReferralAmount() {
        return totalReferralAmount;
    }

    /**
     * @param totalReferralAmount The total_referral_amount
     */
    public void setTotalReferralAmount(int totalReferralAmount) {
        this.totalReferralAmount = totalReferralAmount;
    }

    /**
     * @return The giftAmount
     */
    public int getGiftAmount() {
        return giftAmount;
    }

    /**
     * @param giftAmount The gift_amount
     */
    public void setGiftAmount(int giftAmount) {
        this.giftAmount = giftAmount;
    }

    /**
     * @return The totalAmount
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount The total_amount
     */
    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return The redeemAmount
     */
    public int getRedeemAmount() {
        return redeemAmount;
    }

    /**
     * @param redeemAmount The redeem_amount
     */
    public void setRedeemAmount(int redeemAmount) {
        this.redeemAmount = redeemAmount;
    }

}
