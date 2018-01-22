
package com.spa.model.servicemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

public class InternetCompare {

    @SerializedName("internetService")
    @Expose
    private List<InternetService> internetService = new ArrayList<InternetService>();

    /**
     * 
     * @return
     *     The internetService
     */
    public List<InternetService> getInternetService() {
        return internetService;
    }

    /**
     * 
     * @param internetService
     *     The internetService
     */
    public void setInternetService(List<InternetService> internetService) {
        this.internetService = internetService;
    }

}
