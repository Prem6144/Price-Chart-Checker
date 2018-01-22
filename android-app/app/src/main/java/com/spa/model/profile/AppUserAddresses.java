
package com.spa.model.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AppUserAddresses {
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("business_id")
    @Expose
    private Integer businessId;
    @SerializedName("address_type")
    @Expose
    private Integer addressType;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @SerializedName("city")
    @Expose
    private String city;
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
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

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
    public Object getContactNumber() {
        return contactNumber;
    }

    /**
     * @param contactNumber The contact_number
     */
    public void setContactNumber(Object contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * @return The managerName
     */
    public Object getManagerName() {
        return managerName;
    }

    /**
     * @param managerName The manager_name
     */
    public void setManagerName(Object managerName) {
        this.managerName = managerName;
    }

    /**
     * @return The managerContact
     */
    public Object getManagerContact() {
        return managerContact;
    }

    /**
     * @param managerContact The manager_contact
     */
    public void setManagerContact(Object managerContact) {
        this.managerContact = managerContact;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
