
package com.spa.model.state;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class PrimaryAndSecondaryId {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("primary_ids")
    @Expose
    private List<String> primaryIds = new ArrayList<String>();
    @SerializedName("secondary_ids")
    @Expose
    private List<String> secondaryIds = new ArrayList<String>();

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
     *     The primaryIds
     */
    public List<String> getPrimaryIds() {
        return primaryIds;
    }

    /**
     * 
     * @param primaryIds
     *     The primary_ids
     */
    public void setPrimaryIds(List<String> primaryIds) {
        this.primaryIds = primaryIds;
    }

    /**
     * 
     * @return
     *     The secondaryIds
     */
    public List<String> getSecondaryIds() {
        return secondaryIds;
    }

    /**
     * 
     * @param secondaryIds
     *     The secondary_ids
     */
    public void setSecondaryIds(List<String> secondaryIds) {
        this.secondaryIds = secondaryIds;
    }

}
