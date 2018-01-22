
package com.spa.model.orderdetail;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class DealEquipment {

    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("memory")
    @Expose
    private String memory;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("installation")
    @Expose
    private String installation;
    @SerializedName("activation")
    @Expose
    private String activation;
    @SerializedName("offer")
    @Expose
    private String offer;

    /**
     * 
     * @return
     *     The model
     */
    public String getModel() {
        return model;
    }

    /**
     * 
     * @param model
     *     The model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * 
     * @return
     *     The memory
     */
    public String getMemory() {
        return memory;
    }

    /**
     * 
     * @param memory
     *     The memory
     */
    public void setMemory(String memory) {
        this.memory = memory;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The make
     */
    public String getMake() {
        return make;
    }

    /**
     * 
     * @param make
     *     The make
     */
    public void setMake(String make) {
        this.make = make;
    }

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The installation
     */
    public String getInstallation() {
        return installation;
    }

    /**
     * 
     * @param installation
     *     The installation
     */
    public void setInstallation(String installation) {
        this.installation = installation;
    }

    /**
     * 
     * @return
     *     The activation
     */
    public String getActivation() {
        return activation;
    }

    /**
     * 
     * @param activation
     *     The activation
     */
    public void setActivation(String activation) {
        this.activation = activation;
    }

    /**
     * 
     * @return
     *     The offer
     */
    public String getOffer() {
        return offer;
    }

    /**
     * 
     * @param offer
     *     The offer
     */
    public void setOffer(String offer) {
        this.offer = offer;
    }

}
