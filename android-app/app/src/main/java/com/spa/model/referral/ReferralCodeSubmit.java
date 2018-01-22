package com.spa.model.referral;

/**
 * Created by Diwakar on 5/27/2016.
 */
public class ReferralCodeSubmit {
    /**
     * success : false
     * message : Referral Id is already used.
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
