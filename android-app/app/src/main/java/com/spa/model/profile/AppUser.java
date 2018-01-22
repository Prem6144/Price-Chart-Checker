
package com.spa.model.profile;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class AppUser {

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
    private Integer totalAmount;
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
    @SerializedName("is_service_address_same")
    @Expose
    private String isServiceAddressSame;
    @SerializedName("is_shipping_address_same")
    @Expose
    private String isShippingAddressSame;
    @SerializedName("primary_id")
    @Expose
    private String primaryId;
    @SerializedName("secondary_id")
    @Expose
    private String secondaryId;
    @SerializedName("primary_id_number")
    @Expose
    private String primaryIdNumber;
    @SerializedName("secondary_id_number")
    @Expose
    private String secondaryIdNumber;
    @SerializedName("email_verification_token")
    @Expose
    private String emailVerificationToken;
    @SerializedName("email_verified")
    @Expose
    private Boolean emailVerified;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;

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
    public Integer getTotalAmount() {
        return totalAmount;
    }

    /**
     * 
     * @param totalAmount
     *     The total_amount
     */
    public void setTotalAmount(Integer totalAmount) {
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

    /**
     * 
     * @return
     *     The isServiceAddressSame
     */
    public String getIsServiceAddressSame() {
        return isServiceAddressSame;
    }

    /**
     * 
     * @param isServiceAddressSame
     *     The is_service_address_same
     */
    public void setIsServiceAddressSame(String isServiceAddressSame) {
        this.isServiceAddressSame = isServiceAddressSame;
    }

    /**
     * 
     * @return
     *     The isShippingAddressSame
     */
    public String getIsShippingAddressSame() {
        return isShippingAddressSame;
    }

    /**
     * 
     * @param isShippingAddressSame
     *     The is_shipping_address_same
     */
    public void setIsShippingAddressSame(String isShippingAddressSame) {
        this.isShippingAddressSame = isShippingAddressSame;
    }

    /**
     * 
     * @return
     *     The primaryId
     */
    public String getPrimaryId() {
        return primaryId;
    }

    /**
     * 
     * @param primaryId
     *     The primary_id
     */
    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    /**
     * 
     * @return
     *     The secondaryId
     */
    public String getSecondaryId() {
        return secondaryId;
    }

    /**
     * 
     * @param secondaryId
     *     The secondary_id
     */
    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }

    /**
     * 
     * @return
     *     The primaryIdNumber
     */
    public String getPrimaryIdNumber() {
        return primaryIdNumber;
    }

    /**
     * 
     * @param primaryIdNumber
     *     The primary_id_number
     */
    public void setPrimaryIdNumber(String primaryIdNumber) {
        this.primaryIdNumber = primaryIdNumber;
    }

    /**
     * 
     * @return
     *     The secondaryIdNumber
     */
    public String getSecondaryIdNumber() {
        return secondaryIdNumber;
    }

    /**
     * 
     * @param secondaryIdNumber
     *     The secondary_id_number
     */
    public void setSecondaryIdNumber(String secondaryIdNumber) {
        this.secondaryIdNumber = secondaryIdNumber;
    }

    /**
     * 
     * @return
     *     The emailVerificationToken
     */
    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    /**
     * 
     * @param emailVerificationToken
     *     The email_verification_token
     */
    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    /**
     * 
     * @return
     *     The emailVerified
     */
    public Boolean getEmailVerified() {
        return emailVerified;
    }

    /**
     * 
     * @param emailVerified
     *     The email_verified
     */
    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     * 
     * @return
     *     The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     * 
     * @param avatarUrl
     *     The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
