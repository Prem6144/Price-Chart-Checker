
package com.spa.model.giftcard;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GiftCard {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("order_message")
    @Expose
    private String orderMessage;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("message_status")
    @Expose
    private Boolean messageStatus;
    @SerializedName("gifts")
    @Expose
    private List<Gift> gifts = new ArrayList<Gift>();

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
     *     The orderMessage
     */
    public String getOrderMessage() {
        return orderMessage;
    }

    /**
     * 
     * @param orderMessage
     *     The order_message
     */
    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    /**
     * 
     * @return
     *     The message
     */
    public String getMessage() {
        return message;
    }

    /**
     * 
     * @param message
     *     The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 
     * @return
     *     The messageStatus
     */
    public Boolean getMessageStatus() {
        return messageStatus;
    }

    /**
     * 
     * @param messageStatus
     *     The message_status
     */
    public void setMessageStatus(Boolean messageStatus) {
        this.messageStatus = messageStatus;
    }

    /**
     * 
     * @return
     *     The gifts
     */
    public List<Gift> getGifts() {
        return gifts;
    }

    /**
     * 
     * @param gifts
     *     The gifts
     */
    public void setGifts(List<Gift> gifts) {
        this.gifts = gifts;
    }

}
