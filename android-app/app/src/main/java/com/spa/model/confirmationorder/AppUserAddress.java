
package com.spa.model.confirmationorder;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AppUserAddress {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("app_user_id")
    @Expose
    private Integer appUserId;
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
    @SerializedName("address_type")
    @Expose
    private Integer addressType;
    @SerializedName("contact_number")
    @Expose
    private String contactNumber;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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
     *     The appUserId
     */
    public Integer getAppUserId() {
        return appUserId;
    }

    /**
     * 
     * @param appUserId
     *     The app_user_id
     */
    public void setAppUserId(Integer appUserId) {
        this.appUserId = appUserId;
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
     *     The contactNumber
     */
    public String getContactNumber() {
        return contactNumber;
    }

    /**
     * 
     * @param contactNumber
     *     The contact_number
     */
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
