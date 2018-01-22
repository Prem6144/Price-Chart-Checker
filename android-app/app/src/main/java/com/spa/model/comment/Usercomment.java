
package com.spa.model.comment;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usercomment {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user_comment")
    @Expose
    private Object userComment;
    @SerializedName("comment")
    @Expose
    private List<Comment> comment = new ArrayList<Comment>();
    @SerializedName("average_rating")
    @Expose
    private String averageRating;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;

    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The userComment
     */
    public Object getUserComment() {
        return userComment;
    }

    /**
     * 
     * @param userComment
     *     The user_comment
     */
    public void setUserComment(Object userComment) {
        this.userComment = userComment;
    }

    /**
     * 
     * @return
     *     The comment
     */
    public List<Comment> getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The averageRating
     */
    public String getAverageRating() {
        return averageRating;
    }

    /**
     * 
     * @param averageRating
     *     The average_rating
     */
    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    /**
     * 
     * @return
     *     The commentCount
     */
    public Integer getCommentCount() {
        return commentCount;
    }

    /**
     * 
     * @param commentCount
     *     The comment_count
     */
    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

}
