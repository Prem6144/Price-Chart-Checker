
package com.spa.model.prefrence;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Notification {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
    @SerializedName("recieve_notification")
    @Expose
    private Boolean recieveNotification;
    @SerializedName("day")
    @Expose
    private Integer day;
    @SerializedName("recieve_trending_deals")
    @Expose
    private Boolean recieveTrendingDeals;
    @SerializedName("repeat_notification_frequency")
    @Expose
    private String repeatNotificationFrequency;
    @SerializedName("trending_deal_frequency")
    @Expose
    private String trendingDealFrequency;
    @SerializedName("receive_call")
    @Expose
    private Boolean receiveCall;
    @SerializedName("min_service_provider_rating")
    @Expose
    private Integer minServiceProviderRating;
    @SerializedName("min_deal_rating")
    @Expose
    private Integer minDealRating;
    @SerializedName("receive_email")
    @Expose
    private Boolean receiveEmail;
    @SerializedName("receive_text")
    @Expose
    private Boolean receiveText;

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
     *     The recieveNotification
     */
    public Boolean getRecieveNotification() {
        return recieveNotification;
    }

    /**
     * 
     * @param recieveNotification
     *     The recieve_notification
     */
    public void setRecieveNotification(Boolean recieveNotification) {
        this.recieveNotification = recieveNotification;
    }

    /**
     * 
     * @return
     *     The day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * 
     * @param day
     *     The day
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 
     * @return
     *     The recieveTrendingDeals
     */
    public Boolean getRecieveTrendingDeals() {
        return recieveTrendingDeals;
    }

    /**
     * 
     * @param recieveTrendingDeals
     *     The recieve_trending_deals
     */
    public void setRecieveTrendingDeals(Boolean recieveTrendingDeals) {
        this.recieveTrendingDeals = recieveTrendingDeals;
    }

    /**
     * 
     * @return
     *     The repeatNotificationFrequency
     */
    public String getRepeatNotificationFrequency() {
        return repeatNotificationFrequency;
    }

    /**
     * 
     * @param repeatNotificationFrequency
     *     The repeat_notification_frequency
     */
    public void setRepeatNotificationFrequency(String repeatNotificationFrequency) {
        this.repeatNotificationFrequency = repeatNotificationFrequency;
    }

    /**
     * 
     * @return
     *     The trendingDealFrequency
     */
    public String getTrendingDealFrequency() {
        return trendingDealFrequency;
    }

    /**
     * 
     * @param trendingDealFrequency
     *     The trending_deal_frequency
     */
    public void setTrendingDealFrequency(String trendingDealFrequency) {
        this.trendingDealFrequency = trendingDealFrequency;
    }

    /**
     * 
     * @return
     *     The receiveCall
     */
    public Boolean getReceiveCall() {
        return receiveCall;
    }

    /**
     * 
     * @param receiveCall
     *     The receive_call
     */
    public void setReceiveCall(Boolean receiveCall) {
        this.receiveCall = receiveCall;
    }

    /**
     * 
     * @return
     *     The minServiceProviderRating
     */
    public Integer getMinServiceProviderRating() {
        return minServiceProviderRating;
    }

    /**
     * 
     * @param minServiceProviderRating
     *     The min_service_provider_rating
     */
    public void setMinServiceProviderRating(Integer minServiceProviderRating) {
        this.minServiceProviderRating = minServiceProviderRating;
    }

    /**
     * 
     * @return
     *     The minDealRating
     */
    public Integer getMinDealRating() {
        return minDealRating;
    }

    /**
     * 
     * @param minDealRating
     *     The min_deal_rating
     */
    public void setMinDealRating(Integer minDealRating) {
        this.minDealRating = minDealRating;
    }

    /**
     * 
     * @return
     *     The receiveEmail
     */
    public Boolean getReceiveEmail() {
        return receiveEmail;
    }

    /**
     * 
     * @param receiveEmail
     *     The receive_email
     */
    public void setReceiveEmail(Boolean receiveEmail) {
        this.receiveEmail = receiveEmail;
    }

    /**
     * 
     * @return
     *     The receiveText
     */
    public Boolean getReceiveText() {
        return receiveText;
    }

    /**
     * 
     * @param receiveText
     *     The receive_text
     */
    public void setReceiveText(Boolean receiveText) {
        this.receiveText = receiveText;
    }

}
