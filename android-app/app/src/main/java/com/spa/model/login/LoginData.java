package com.spa.model.login;

/**
 * Created by Diwakar on 5/27/2016.
 */
public class LoginData {
    /**
     * success : true
     * info : Logged in
     * data : {"id":7,"user_type":"business","business_name":"Type","first_name":"Diwakar","last_name":"Gupta","email":"diwakar@gmail.com","address":"","state":"","city":"delhi","zip":"75025","gcm_id":"APA91bEADOcyE3lV0xEqZ9P0TzR4pE7jwUiPv0yqybt3zPpFcsGIOe4fzDsR_qXDNEQv20w2gPn3cq4gsKQyv9Xkfj733AQRysoxogidsB0sLLMq74b_0mNmNOuu7VmX5E-HvOTJypVe","unhashed_password":"12345678","device_flag":"android","active":true,"referral_code":"E7UM","avatar_url":""}
     * user_preference : true
     */

    private boolean success;
    private String info;
    /**
     * id : 7
     * user_type : business
     * business_name : Type
     * first_name : Diwakar
     * last_name : Gupta
     * email : diwakar@gmail.com
     * address :
     * state :
     * city : delhi
     * zip : 75025
     * gcm_id : APA91bEADOcyE3lV0xEqZ9P0TzR4pE7jwUiPv0yqybt3zPpFcsGIOe4fzDsR_qXDNEQv20w2gPn3cq4gsKQyv9Xkfj733AQRysoxogidsB0sLLMq74b_0mNmNOuu7VmX5E-HvOTJypVe
     * unhashed_password : 12345678
     * device_flag : android
     * active : true
     * referral_code : E7UM
     * avatar_url :
     */

    private DataEntity data;
    private boolean user_preference;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public boolean isUser_preference() {
        return user_preference;
    }

    public void setUser_preference(boolean user_preference) {
        this.user_preference = user_preference;
    }

    public static class DataEntity {
        private int id;
        private String user_type;
        private String business_name;
        private String first_name;
        private String last_name;
        private String email;
        private String address;
        private String state;
        private String city;
        private String zip;
        private String gcm_id;
        private String unhashed_password;
        private String device_flag;
        private boolean active;
        private String referral_code;
        private String avatar_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getBusiness_name() {
            return business_name;
        }

        public void setBusiness_name(String business_name) {
            this.business_name = business_name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getGcm_id() {
            return gcm_id;
        }

        public void setGcm_id(String gcm_id) {
            this.gcm_id = gcm_id;
        }

        public String getUnhashed_password() {
            return unhashed_password;
        }

        public void setUnhashed_password(String unhashed_password) {
            this.unhashed_password = unhashed_password;
        }

        public String getDevice_flag() {
            return device_flag;
        }

        public void setDevice_flag(String device_flag) {
            this.device_flag = device_flag;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getReferral_code() {
            return referral_code;
        }

        public void setReferral_code(String referral_code) {
            this.referral_code = referral_code;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }
    }
}
