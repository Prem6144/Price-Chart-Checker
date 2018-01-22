
package com.spa.model.dashbordmodel;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashboardDatum {

    @SerializedName("you_save_text")
    @Expose
    private String youSaveText;
    @SerializedName("contract_fee")
    @Expose
    private Float contractFee;
    @SerializedName("service_provider_name")
    @Expose
    private String serviceProviderName;
    @SerializedName("service_category_name")
    @Expose
    private String serviceCategoryName;
    @SerializedName("advertisement")
    @Expose
    private List<Object> advertisement = new ArrayList<Object>();
    @SerializedName("trending_deal")
    @Expose
    private List<TrendingDeal> trendingDeal = new ArrayList<TrendingDeal>();
    @SerializedName("best_deal")
    @Expose
    private List<BestDeal> bestDeal = new ArrayList<BestDeal>();
    @SerializedName("preferred_deal")
    @Expose
    private List<Object> preferredDeal = new ArrayList<Object>();

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
    public Float getContractFee() {
        return contractFee;
    }

    /**
     * 
     * @param contractFee
     *     The contract_fee
     */
    public void setContractFee(Float contractFee) {
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
     *     The advertisement
     */
    public List<Object> getAdvertisement() {
        return advertisement;
    }

    /**
     * 
     * @param advertisement
     *     The advertisement
     */
    public void setAdvertisement(List<Object> advertisement) {
        this.advertisement = advertisement;
    }

    /**
     * 
     * @return
     *     The trendingDeal
     */
    public List<TrendingDeal> getTrendingDeal() {
        return trendingDeal;
    }

    /**
     * 
     * @param trendingDeal
     *     The trending_deal
     */
    public void setTrendingDeal(List<TrendingDeal> trendingDeal) {
        this.trendingDeal = trendingDeal;
    }

    /**
     * 
     * @return
     *     The bestDeal
     */
    public List<BestDeal> getBestDeal() {
        return bestDeal;
    }

    /**
     * 
     * @param bestDeal
     *     The best_deal
     */
    public void setBestDeal(List<BestDeal> bestDeal) {
        this.bestDeal = bestDeal;
    }

    /**
     * 
     * @return
     *     The preferredDeal
     */
    public List<Object> getPreferredDeal() {
        return preferredDeal;
    }

    /**
     * 
     * @param preferredDeal
     *     The preferred_deal
     */
    public void setPreferredDeal(List<Object> preferredDeal) {
        this.preferredDeal = preferredDeal;
    }

}
