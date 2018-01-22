package com.spa.model.referral;

/**
 * Created by Diwakar on 5/27/2016.
 */
public class ReferralCode {
    /**
     * success : true
     * app_user_code : F2G6
     * refer_status : false
     */

    private boolean success;
    private String app_user_code;
    private boolean refer_status;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getApp_user_code() {
        return app_user_code;
    }

    public void setApp_user_code(String app_user_code) {
        this.app_user_code = app_user_code;
    }

    public boolean isRefer_status() {
        return refer_status;
    }

    public void setRefer_status(boolean refer_status) {
        this.refer_status = refer_status;
    }
}
