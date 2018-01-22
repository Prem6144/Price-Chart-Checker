
package com.spa.model.guestdashboard;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class GuestDashboard {

    @SerializedName("dashboard_data")
    @Expose
    private List<DashboardDatum> dashboardData = new ArrayList<DashboardDatum>();

    /**
     * 
     * @return
     *     The dashboardData
     */
    public List<DashboardDatum> getDashboardData() {
        return dashboardData;
    }

    /**
     * 
     * @param dashboardData
     *     The dashboard_data
     */
    public void setDashboardData(List<DashboardDatum> dashboardData) {
        this.dashboardData = dashboardData;
    }

}
