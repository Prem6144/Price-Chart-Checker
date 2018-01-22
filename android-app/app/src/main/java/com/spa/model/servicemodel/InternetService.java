
package com.spa.model.servicemodel;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InternetService {

    @SerializedName("contract")
    @Expose
    private String contract;
    @SerializedName("download")
    @Expose
    private String download;
    @SerializedName("upload")
    @Expose
    private String upload;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("modem")
    @Expose
    private String modem;
    @SerializedName("offer")
    @Expose
    private String offer;
    @SerializedName("prepaid_card")
    @Expose
    private String prepaidCard;

    /**
     * 
     * @return
     *     The contract
     */
    public String getContract() {
        return contract;
    }

    /**
     * 
     * @param contract
     *     The contract
     */
    public void setContract(String contract) {
        this.contract = contract;
    }

    /**
     * 
     * @return
     *     The download
     */
    public String getDownload() {
        return download;
    }

    /**
     * 
     * @param download
     *     The download
     */
    public void setDownload(String download) {
        this.download = download;
    }

    /**
     * 
     * @return
     *     The upload
     */
    public String getUpload() {
        return upload;
    }

    /**
     * 
     * @param upload
     *     The upload
     */
    public void setUpload(String upload) {
        this.upload = upload;
    }

    /**
     * 
     * @return
     *     The data
     */
    public String getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The modem
     */
    public String getModem() {
        return modem;
    }

    /**
     * 
     * @param modem
     *     The modem
     */
    public void setModem(String modem) {
        this.modem = modem;
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

    /**
     * 
     * @return
     *     The prepaidCard
     */
    public String getPrepaidCard() {
        return prepaidCard;
    }

    /**
     * 
     * @param prepaidCard
     *     The prepaid_card
     */
    public void setPrepaidCard(String prepaidCard) {
        this.prepaidCard = prepaidCard;
    }

}
