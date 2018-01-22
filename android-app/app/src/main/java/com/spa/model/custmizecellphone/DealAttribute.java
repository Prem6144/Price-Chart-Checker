
package com.spa.model.custmizecellphone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DealAttribute {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("deal_id")
    @Expose
    private Integer dealId;
    @SerializedName("free_channels")
    @Expose
    private Integer freeChannels;
    @SerializedName("free_channels_list")
    @Expose
    private String freeChannelsList;
    @SerializedName("premium_channels")
    @Expose
    private Object premiumChannels;
    @SerializedName("premium_channels_list")
    @Expose
    private String premiumChannelsList;
    @SerializedName("hd_channels")
    @Expose
    private Integer hdChannels;
    @SerializedName("hd_channels_list")
    @Expose
    private String hdChannelsList;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("channel_ids")
    @Expose
    private String channelIds;
    @SerializedName("channel_package_ids")
    @Expose
    private Object channelPackageIds;
    @SerializedName("channel_count")
    @Expose
    private Integer channelCount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("title")
    @Expose
    private Object title;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("channel_name")
    @Expose
    private List<ChannelName_> channelName = new ArrayList<ChannelName_>();

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
     *     The dealId
     */
    public Integer getDealId() {
        return dealId;
    }

    /**
     * 
     * @param dealId
     *     The deal_id
     */
    public void setDealId(Integer dealId) {
        this.dealId = dealId;
    }

    /**
     * 
     * @return
     *     The freeChannels
     */
    public Integer getFreeChannels() {
        return freeChannels;
    }

    /**
     * 
     * @param freeChannels
     *     The free_channels
     */
    public void setFreeChannels(Integer freeChannels) {
        this.freeChannels = freeChannels;
    }

    /**
     * 
     * @return
     *     The freeChannelsList
     */
    public String getFreeChannelsList() {
        return freeChannelsList;
    }

    /**
     * 
     * @param freeChannelsList
     *     The free_channels_list
     */
    public void setFreeChannelsList(String freeChannelsList) {
        this.freeChannelsList = freeChannelsList;
    }

    /**
     * 
     * @return
     *     The premiumChannels
     */
    public Object getPremiumChannels() {
        return premiumChannels;
    }

    /**
     * 
     * @param premiumChannels
     *     The premium_channels
     */
    public void setPremiumChannels(Object premiumChannels) {
        this.premiumChannels = premiumChannels;
    }

    /**
     * 
     * @return
     *     The premiumChannelsList
     */
    public String getPremiumChannelsList() {
        return premiumChannelsList;
    }

    /**
     * 
     * @param premiumChannelsList
     *     The premium_channels_list
     */
    public void setPremiumChannelsList(String premiumChannelsList) {
        this.premiumChannelsList = premiumChannelsList;
    }

    /**
     * 
     * @return
     *     The hdChannels
     */
    public Integer getHdChannels() {
        return hdChannels;
    }

    /**
     * 
     * @param hdChannels
     *     The hd_channels
     */
    public void setHdChannels(Integer hdChannels) {
        this.hdChannels = hdChannels;
    }

    /**
     * 
     * @return
     *     The hdChannelsList
     */
    public String getHdChannelsList() {
        return hdChannelsList;
    }

    /**
     * 
     * @param hdChannelsList
     *     The hd_channels_list
     */
    public void setHdChannelsList(String hdChannelsList) {
        this.hdChannelsList = hdChannelsList;
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
     *     The channelPackageIds
     */
    public Object getChannelPackageIds() {
        return channelPackageIds;
    }

    /**
     * 
     * @param channelPackageIds
     *     The channel_package_ids
     */
    public void setChannelPackageIds(Object channelPackageIds) {
        this.channelPackageIds = channelPackageIds;
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
     *     The title
     */
    public Object getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(Object title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 
     * @param amount
     *     The amount
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * 
     * @return
     *     The channelName
     */
    public List<ChannelName_> getChannelName() {
        return channelName;
    }

    /**
     * 
     * @param channelName
     *     The channel_name
     */
    public void setChannelName(List<ChannelName_> channelName) {
        this.channelName = channelName;
    }

}
