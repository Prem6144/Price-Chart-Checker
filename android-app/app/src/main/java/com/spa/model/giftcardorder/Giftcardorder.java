
package com.spa.model.giftcardorder;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Giftcardorder {

    @SerializedName("success")
    @Expose
    private Boolean success;
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
