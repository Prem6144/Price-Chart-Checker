
package com.spa.model.deallist;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Deallist {

    @SerializedName("deal")
    @Expose
    private List<Deal> deal = new ArrayList<Deal>();

    /**
     * 
     * @return
     *     The deal
     */
    public List<Deal> getDeal() {
        return deal;
    }

    /**
     * 
     * @param deal
     *     The deal
     */
    public void setDeal(List<Deal> deal) {
        this.deal = deal;
    }

}
