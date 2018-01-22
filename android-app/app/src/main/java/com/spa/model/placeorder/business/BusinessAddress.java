
package com.spa.model.placeorder.business;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class BusinessAddress {
    @SerializedName("state")
    @Expose
    private String State;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("business_id")
    @Expose
    private Integer businessId;
    @SerializedName("address_type")
    @Expose
    private Integer addressType;
    @SerializedName("address_name")
    @Expose
    private String addressName;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @SerializedName("city")
    @Expose
    private String city;
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
    private String contactNumber;
    @SerializedName("manager_name")
    @Expose
    private String managerName;
    @SerializedName("manager_contact")
    @Expose
    private String managerContact;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The businessId
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * @param businessId The business_id
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * @return The addressType
     */
    public Integer getAddressType() {
        return addressType;
    }

    /**
     * @param addressType The address_type
     */
    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    /**
     * @return The addressName
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * @param addressName The address_name
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * @return The State
     */
    public String getState() {
        return State;
    }

    /**
     * @param State The state
     */
    public void setState(String State) {
        this.State = State;
    }

    /**
     * @return The zip
     */
    public String getZip() {
        return zip;
    }

    /**
     * @param zip The zip
     */
    public void setZip(String zip) {
        this.zip = zip;
    }

    /**
     * @return The address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 The address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return The address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 The address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return The contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber The contact_number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return The managerName
     */
    public String getManagerName() {
        return managerName;
    }

    /**
     * @param managerName The manager_name
     */
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    /**
     * @return The managerContact
     */
    public String getManagerContact() {
        return managerContact;
    }

    /**
     * @param managerContact The manager_contact
     */
    public void setManagerContact(String managerContact) {
        this.managerContact = managerContact;
    }

}
