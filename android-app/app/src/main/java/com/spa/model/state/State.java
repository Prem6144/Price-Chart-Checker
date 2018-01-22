
package com.spa.model.state;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class State {

    @SerializedName("states")
    @Expose
    private List<String> states = new ArrayList<String>();

    /**
     * 
     * @return
     *     The states
     */
    public List<String> getStates() {
        return states;
    }

    /**
     * 
     * @param states
     *     The states
     */
    public void setStates(List<String> states) {
        this.states = states;
    }

}
