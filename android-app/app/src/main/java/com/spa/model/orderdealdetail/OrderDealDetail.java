
package com.spa.model.orderdealdetail;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spa.model.orderdetail.Business;

@Generated("org.jsonschema2pojo")
public class OrderDealDetail {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("order")
    @Expose
    private Order order;
    @SerializedName("order_addresses")
    @Expose
    private List<OrderAddress> orderAddresses = new ArrayList<OrderAddress>();
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();
    @SerializedName("app_user")
    @Expose
    private AppUser appUser;
    @SerializedName("order_attributes")
    @Expose
    private OrderAttributes orderAttributes;
    @SerializedName("order_extra_services")
    @Expose
    private OrderExtraServices orderExtraServices;
    @SerializedName("order_equipments")
    @Expose
    private List<OrderEquipment> orderEquipments = new ArrayList<OrderEquipment>();

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @SerializedName("business")
    @Expose
    private Business business;
    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * 
     * @param order
     *     The order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * 
     * @return
     *     The orderAddresses
     */
    public List<OrderAddress> getOrderAddresses() {
        return orderAddresses;
    }

    /**
     * 
     * @param orderAddresses
     *     The order_addresses
     */
    public void setOrderAddresses(List<OrderAddress> orderAddresses) {
        this.orderAddresses = orderAddresses;
    }

    /**
     * 
     * @return
     *     The orderItems
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * 
     * @param orderItems
     *     The order_items
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * 
     * @return
     *     The appUser
     */
    public AppUser getAppUser() {
        return appUser;
    }

    /**
     * 
     * @param appUser
     *     The app_user
     */
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    /**
     * 
     * @return
     *     The orderAttributes
     */
    public OrderAttributes getOrderAttributes() {
        return orderAttributes;
    }

    /**
     * 
     * @param orderAttributes
     *     The order_attributes
     */
    public void setOrderAttributes(OrderAttributes orderAttributes) {
        this.orderAttributes = orderAttributes;
    }

    /**
     * 
     * @return
     *     The orderExtraServices
     */
    public OrderExtraServices getOrderExtraServices() {
        return orderExtraServices;
    }

    /**
     * 
     * @param orderExtraServices
     *     The order_extra_services
     */
    public void setOrderExtraServices(OrderExtraServices orderExtraServices) {
        this.orderExtraServices = orderExtraServices;
    }

    /**
     * 
     * @return
     *     The orderEquipments
     */
    public List<OrderEquipment> getOrderEquipments() {
        return orderEquipments;
    }

    /**
     * 
     * @param orderEquipments
     *     The order_equipments
     */
    public void setOrderEquipments(List<OrderEquipment> orderEquipments) {
        this.orderEquipments = orderEquipments;
    }

}
