
package com.spa.model.cellphoneservice;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CellphoneDetail {

    @SerializedName("cellphone_details")
    @Expose
    private List<CellphoneDetail_> cellphoneDetails = new ArrayList<CellphoneDetail_>();

    /**
     * 
     * @return
     *     The cellphoneDetails
     */
    public List<CellphoneDetail_> getCellphoneDetails() {
        return cellphoneDetails;
    }

    /**
     * 
     * @param cellphoneDetails
     *     The cellphone_details
     */
    public void setCellphoneDetails(List<CellphoneDetail_> cellphoneDetails) {
        this.cellphoneDetails = cellphoneDetails;
    }

}
