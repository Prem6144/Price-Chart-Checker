
package com.spa.model.equipments;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CellphoneEquipments {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("equipments")
    @Expose
    private List<Equipment> equipments = new ArrayList<Equipment>();

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
     *     The equipments
     */
    public List<Equipment> getEquipments() {
        return equipments;
    }

    /**
     * 
     * @param equipments
     *     The equipments
     */
    public void setEquipments(List<Equipment> equipments) {
        this.equipments = equipments;
    }

}
