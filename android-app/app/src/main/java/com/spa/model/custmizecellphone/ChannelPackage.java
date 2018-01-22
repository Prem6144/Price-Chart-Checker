
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class ChannelPackage {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("package_code")
    @Expose
    private String packageCode;
    @SerializedName("channel_count")
    @Expose
    private Integer channelCount;
    @SerializedName("channel_ids")
    @Expose
    private String channelIds;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("service_provider_id")
    @Expose
    private Integer serviceProviderId;
    @SerializedName("channel_name")
    @Expose
    private List<ChannelName> channelName = new ArrayList<ChannelName>();

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
     *     The packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * 
     * @param packageName
     *     The package_name
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 
     * @return
     *     The packageCode
     */
    public String getPackageCode() {
        return packageCode;
    }

    /**
     * 
     * @param packageCode
     *     The package_code
     */
    public void setPackageCode(String packageCode) {
        this.packageCode = packageCode;
    }

    /**
     * 
     * @return
     *     The channelCount
     */
    public Integer getChannelCount() {
        return channelCount;
    }

    /**
     * 
     * @param channelCount
     *     The channel_count
     */
    public void setChannelCount(Integer channelCount) {
        this.channelCount = channelCount;
    }

    /**
     * 
     * @return
     *     The channelIds
     */
    public String getChannelIds() {
        return channelIds;
    }

    /**
     * 
     * @param channelIds
     *     The channel_ids
     */
    public void setChannelIds(String channelIds) {
        this.channelIds = channelIds;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The image
     */
    public Object getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(Object image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Boolean status) {
        this.status = status;
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

    /**
     * 
     * @return
     *     The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * 
     * @param price
     *     The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * 
     * @return
     *     The serviceProviderId
     */
    public Integer getServiceProviderId() {
        return serviceProviderId;
    }

    /**
     * 
     * @param serviceProviderId
     *     The service_provider_id
     */
    public void setServiceProviderId(Integer serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    /**
     * 
     * @return
     *     The channelName
     */
    public List<ChannelName> getChannelName() {
        return channelName;
    }

    /**
     * 
     * @param channelName
     *     The channel_name
     */
    public void setChannelName(List<ChannelName> channelName) {
        this.channelName = channelName;
    }

}
