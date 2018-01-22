
package com.spa.model.custemizedealdetail;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("org.jsonschema2pojo")
public class AvailableColor {


    @SerializedName("color_name")
    @Expose
    private String colorName;



    public String getColorName() {
        return colorName;
    }

    /**
     * 
     * @param colorName
     *     The color_name
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

}
