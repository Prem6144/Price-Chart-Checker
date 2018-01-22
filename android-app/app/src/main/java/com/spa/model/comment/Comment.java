
package com.spa.model.comment;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("rating_point")
    @Expose
    private Double ratingPoint;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("comment_title")
    @Expose
    private String commentTitle;
    @SerializedName("comment_text")
    @Expose
    private String commentText;
    @SerializedName("app_user_name")
    @Expose
    private String appUserName;
    @SerializedName("app_user_image_url")
    @Expose
    private String appUserImageUrl;
    @SerializedName("comment_date")
    @Expose
    private String commentDate;

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
     *     The ratingPoint
     */
    public Double getRatingPoint() {
        return ratingPoint;
    }

    /**
     * 
     * @param ratingPoint
     *     The rating_point
     */
    public void setRatingPoint(Double ratingPoint) {
        this.ratingPoint = ratingPoint;
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
     *     The commentTitle
     */
    public String getCommentTitle() {
        return commentTitle;
    }

    /**
     * 
     * @param commentTitle
     *     The comment_title
     */
    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    /**
     * 
     * @return
     *     The commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * 
     * @param commentText
     *     The comment_text
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * 
     * @return
     *     The appUserName
     */
    public String getAppUserName() {
        return appUserName;
    }

    /**
     * 
     * @param appUserName
     *     The app_user_name
     */
    public void setAppUserName(String appUserName) {
        this.appUserName = appUserName;
    }

    /**
     * 
     * @return
     *     The appUserImageUrl
     */
    public String getAppUserImageUrl() {
        return appUserImageUrl;
    }

    /**
     * 
     * @param appUserImageUrl
     *     The app_user_image_url
     */
    public void setAppUserImageUrl(String appUserImageUrl) {
        this.appUserImageUrl = appUserImageUrl;
    }

    /**
     * 
     * @return
     *     The commentDate
     */
    public String getCommentDate() {
        return commentDate;
    }

    /**
     * 
     * @param commentDate
     *     The comment_date
     */
    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

}
