
package com.spa.model.orderdealdetail;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class OrderAddress {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("address_type")
    @Expose
    private Integer addressType;
    @SerializedName("address_name")
    @Expose
    private String addressName;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("contact_number")
    @Expose
    private Object contactNumber;
    @SerializedName("manager_name")
    @Expose
    private Object managerName;
    @SerializedName("manager_contact")
    @Expose
    private Object managerContact;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;

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
     *     The orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 
     * @param orderId
     *     The order_id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     * @return
     *     The addressType
     */
    public Integer getAddressType() {
        return addressType;
    }

    /**
     * 
     * @param addressType
     *     The address_type
     */
    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    /**
     * 
     * @return
     *     The addressName
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * 
     * @param addressName
     *     The address_name
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * 
     * @return
     *     The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * 
     * @param zip
     *     The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * 
     * @return
     *     The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     *     The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 
     * @return
     *     The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * 
     * @param address2
     *     The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * 
     * @return
     *     The contactNumber
     */
    public Object getContactNumber() {
        return contactNumber;
    }

    /**
     * 
     * @param contactNumber
     *     The contact_number
     */
    public void setContactNumber(Object contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 
     * @return
     *     The managerName
     */
    public Object getManagerName() {
        return managerName;
    }

    /**
     * 
     * @param managerName
     *     The manager_name
     */
    public void setManagerName(Object managerName) {
        this.managerName = managerName;
    }

    /**
     * 
     * @return
     *     The managerContact
     */
    public Object getManagerContact() {
        return managerContact;
    }

    /**
     * 
     * @param managerContact
     *     The manager_contact
     */
    public void setManagerContact(Object managerContact) {
        this.managerContact = managerContact;
    }

    /**
     * 
     * @return
     *     The state
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     *     The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

}
