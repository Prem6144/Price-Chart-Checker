
package com.spa.model.placeorder.residence;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AppUsers {
    public String getPrimary_id() {
        return primary_id;
    }

    public void setPrimary_id(String primary_id) {
        this.primary_id = primary_id;
    }

    public String getSecondary_id() {
        return secondary_id;
    }

    public void setSecondary_id(String secondary_id) {
        this.secondary_id = secondary_id;
    }

    public String getPrimary_id_number() {
        return primary_id_number;
    }

    public void setPrimary_id_number(String primary_id_number) {
        this.primary_id_number = primary_id_number;
    }

    public String getSecondary_id_number() {
        return secondary_id_number;
    }

    public void setSecondary_id_number(String secondary_id_number) {
        this.secondary_id_number = secondary_id_number;
    }

    @SerializedName("primary_id")
    @Expose
    private String primary_id;
    @SerializedName("secondary_id")
    @Expose
    private String secondary_id;
    @SerializedName("primary_id_number")
    @Expose
    private String primary_id_number;
    @SerializedName("secondary_id_number")
    @Expose
    private String secondary_id_number;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("business_name")
    @Expose
    private String businessName;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("gcm_id")
    @Expose
    private String gcmId;
    @SerializedName("avatar")
    @Expose
    private Avatar avatar;
    @SerializedName("unhashed_password")
    @Expose
    private String unhashedPassword;
    @SerializedName("device_flag")
    @Expose
    private String deviceFlag;
    @SerializedName("active")
    @Expose
    private Boolean active;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("refer_status")
    @Expose
    private String referStatus;
    @SerializedName("total_amount")
    @Expose
    private Float totalAmount;
    @SerializedName("customer_service_account")
    @Expose
    private String customerServiceAccount;
    @SerializedName("customer_status")
    @Expose
    private String customerStatus;
    @SerializedName("credit_worthy")
    @Expose
    private String creditWorthy;
    @SerializedName("customer_contract")
    @Expose
    private String customerContract;
    @SerializedName("mobile")
    @Expose
    private String mobile;

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
     *     The userType
     */
    public String getUserType() {
        return userType;
    }

    /**
     * 
     * @param userType
     *     The user_type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * 
     * @return
     *     The businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 
     * @param businessName
     *     The business_name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     * 
     * @return
     *     The firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * 
     * @param firstName
     *     The first_name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @return
     *     The lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @param lastName
     *     The last_name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
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
     *     The gcmId
     */
    public String getGcmId() {
        return gcmId;
    }

    /**
     * 
     * @param gcmId
     *     The gcm_id
     */
    public void setGcmId(String gcmId) {
        this.gcmId = gcmId;
    }

    /**
     * 
     * @return
     *     The avatar
     */
    public Avatar getAvatar() {
        return avatar;
    }

    /**
     * 
     * @param avatar
     *     The avatar
     */
    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    /**
     * 
     * @return
     *     The unhashedPassword
     */
    public String getUnhashedPassword() {
        return unhashedPassword;
    }

    /**
     * 
     * @param unhashedPassword
     *     The unhashed_password
     */
    public void setUnhashedPassword(String unhashedPassword) {
        this.unhashedPassword = unhashedPassword;
    }

    /**
     * 
     * @return
     *     The deviceFlag
     */
    public String getDeviceFlag() {
        return deviceFlag;
    }

    /**
     * 
     * @param deviceFlag
     *     The device_flag
     */
    public void setDeviceFlag(String deviceFlag) {
        this.deviceFlag = deviceFlag;
    }

    /**
     * 
     * @return
     *     The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * 
     * @return
     *     The referralCode
     */
    public String getReferralCode() {
        return referralCode;
    }

    /**
     * 
     * @param referralCode
     *     The referral_code
     */
    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    /**
     * 
     * @return
     *     The referStatus
     */
    public String getReferStatus() {
        return referStatus;
    }

    /**
     * 
     * @param referStatus
     *     The refer_status
     */
    public void setReferStatus(String referStatus) {
        this.referStatus = referStatus;
    }

    /**
     * 
     * @return
     *     The totalAmount
     */
    public Float getTotalAmount() {
        return totalAmount;
    }

    /**
     * 
     * @param totalAmount
     *     The total_amount
     */
    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 
     * @return
     *     The customerServiceAccount
     */
    public String getCustomerServiceAccount() {
        return customerServiceAccount;
    }

    /**
     * 
     * @param customerServiceAccount
     *     The customer_service_account
     */
    public void setCustomerServiceAccount(String customerServiceAccount) {
        this.customerServiceAccount = customerServiceAccount;
    }

    /**
     * 
     * @return
     *     The customerStatus
     */
    public String getCustomerStatus() {
        return customerStatus;
    }

    /**
     * 
     * @param customerStatus
     *     The customer_status
     */
    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    /**
     * 
     * @return
     *     The creditWorthy
     */
    public String getCreditWorthy() {
        return creditWorthy;
    }

    /**
     * 
     * @param creditWorthy
     *     The credit_worthy
     */
    public void setCreditWorthy(String creditWorthy) {
        this.creditWorthy = creditWorthy;
    }

    /**
     * 
     * @return
     *     The customerContract
     */
    public String getCustomerContract() {
        return customerContract;
    }

    /**
     * 
     * @param customerContract
     *     The customer_contract
     */
    public void setCustomerContract(String customerContract) {
        this.customerContract = customerContract;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
