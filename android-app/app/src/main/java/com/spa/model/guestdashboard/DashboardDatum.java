
package com.spa.model.guestdashboard;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DashboardDatum {

    @SerializedName("you_save_text")
    @Expose
    private String youSaveText;
    @SerializedName("contract_fee")
    @Expose
    private String contractFee;
    @SerializedName("service_provider_name")
    @Expose
    private String serviceProviderName;
    @SerializedName("service_category_name")
    @Expose
    private String serviceCategoryName;
    @SerializedName("trending_deal")
    @Expose
    private TrendingDeal trendingDeal;

    /**
     * 
     * @return
     *     The youSaveText
     */
    public String getYouSaveText() {
        return youSaveText;
    }

    /**
     * 
     * @param youSaveText
     *     The you_save_text
     */
    public void setYouSaveText(String youSaveText) {
        this.youSaveText = youSaveText;
    }

    /**
     * 
     * @return
     *     The contractFee
     */
    public String getContractFee() {
        return contractFee;
    }

    /**
     * 
     * @param contractFee
     *     The contract_fee
     */
    public void setContractFee(String contractFee) {
        this.contractFee = contractFee;
    }

    /**
     * 
     * @return
     *     The serviceProviderName
     */
    public String getServiceProviderName() {
        return serviceProviderName;
    }

    /**
     * 
     * @param serviceProviderName
     *     The service_provider_name
     */
    public void setServiceProviderName(String serviceProviderName) {
        this.serviceProviderName = serviceProviderName;
    }

    /**
     * 
     * @return
     *     The serviceCategoryName
     */
    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    /**
     * 
     * @param serviceCategoryName
     *     The service_category_name
     */
    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    /**
     * 
     * @return
     *     The trendingDeal
     */
    public TrendingDeal getTrendingDeal() {
        return trendingDeal;
    }

    /**
     * 
     * @param trendingDeal
     *     The trending_deal
     */
    public void setTrendingDeal(TrendingDeal trendingDeal) {
        this.trendingDeal = trendingDeal;
    }

}
