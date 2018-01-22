
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AvailableColor {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("color_name")
    @Expose
    private String colorName;



    @SerializedName("color_code")
    @Expose
    private String colorCode;
    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The colorName
     */
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

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

}
