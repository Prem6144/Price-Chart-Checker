
package com.spa.model.cellphoneservice;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class CellphoneDetail_ {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cellphone_name")
    @Expose
    private String cellphoneName;
    @SerializedName("image")
    @Expose
    private Image image;

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
     *     The cellphoneName
     */
    public String getCellphoneName() {
        return cellphoneName;
    }

    /**
     * 
     * @param cellphoneName
     *     The cellphone_name
     */
    public void setCellphoneName(String cellphoneName) {
        this.cellphoneName = cellphoneName;
    }

    /**
     * 
     * @return
     *     The image
     */
    public Image getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Image image) {
        this.image = image;
    }

}
