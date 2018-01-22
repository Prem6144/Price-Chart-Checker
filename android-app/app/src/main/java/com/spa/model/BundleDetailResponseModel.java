package com.spa.model;

import java.util.List;

/**
 * Created by Prakash on 8/26/2016.
 */
public class BundleDetailResponseModel {
    /**
     * success : true
     * deals : {"id":49,"service_category_id":5,"service_provider_id":12,"title":"Internet+DirecTV+Digital Home Phone","short_description":"1. Upto 6mbps internet speeds.\r\n2. DirecTV select Package.\r\n3. Digital Home Phone.","detail_description":"BUNDLE PRICE includes SELECT™ All Included Package, ($50 per month) U-verse® High Speed Internet 6.0 ($30 per month) and U-verse® Voice Unlimited North America ($9.99 per month) and monthly fees for Wi-Fi Gateway and HD DVR + up to 3 additional receivers.\r\n\r\nDIRECTV SERVICE TERMS: Subject to Equipment Lease & Customer Agreements. Must maintain a minimum base TV package of $29.99 per month.\r\n\r\nU-VERSE VOICE including 911 dialing, will not function during a power outage without battery backup power.","is_contract":true,"contract_period":12,"url":"https://www.att.com/internet/","start_date":"04/23/2016 00:00:00","end_date":"09/13/2016 00:00:00","is_nationwide":true,"deal_type":"residence","is_active":true,"is_sponsored":false,"effective_price":"90.83","deal_image_url":"http://res.cloudinary.com/softwareassurance/image/upload/v1461410261/wd4czaew495b7d51doex.jpg","average_rating":0,"rating_count":0,"deal_price":"89.99","service_category_name":"Bundle","service_provider_name":"AT&T","deal_additional_offers":[{"id":11,"deal_id":49,"title":"Reward Cards","description":"","price":4.16,"start_date":null,"end_date":null,"is_nationwide":true,"is_active":true,"created_at":"04/23/2016 11:18:03","updated_at":"05/20/2016 10:33:00"}],"deal_equipments":[{"id":16,"bundle_deal_attribute_id":11,"name":"TiVo HD DVR","make":"","price":"5.0","installation":"Standard professional installation included","activation":"","offer":"","is_active":false,"created_at":"05/20/2016 10:33:00","updated_at":"05/20/2016 10:33:00","deal_id":49}],"bundle_deal_attributes":[{"id":11,"deal_id":49,"bundle_combo":"Internet,Telephone and Cable","download":6,"upload":null,"data":null,"static_ip":null,"domestic_call_minutes":"Unlimited","international_call_minutes":"Unlimited","free_channels":0,"free_channels_list":"","premium_channels":null,"premium_channels_list":"","hd_channels":null,"hd_channels_list":"","created_at":"04/23/2016 11:18:03","updated_at":"04/23/2016 11:18:03"}],"bundle_equipments":[{"id":16,"bundle_deal_attribute_id":11,"name":"TiVo HD DVR","make":"","price":"5.0","installation":"Standard professional installation included","activation":"","offer":"","is_active":false,"created_at":"05/20/2016 10:33:00","updated_at":"05/20/2016 10:33:00","deal_id":49}]}
     */

    private boolean success;
    /**
     * id : 49
     * service_category_id : 5
     * service_provider_id : 12
     * title : Internet+DirecTV+Digital Home Phone
     * short_description : 1. Upto 6mbps internet speeds.
     2. DirecTV select Package.
     3. Digital Home Phone.
     * detail_description : BUNDLE PRICE includes SELECT™ All Included Package, ($50 per month) U-verse® High Speed Internet 6.0 ($30 per month) and U-verse® Voice Unlimited North America ($9.99 per month) and monthly fees for Wi-Fi Gateway and HD DVR + up to 3 additional receivers.

     DIRECTV SERVICE TERMS: Subject to Equipment Lease & Customer Agreements. Must maintain a minimum base TV package of $29.99 per month.

     U-VERSE VOICE including 911 dialing, will not function during a power outage without battery backup power.
     * is_contract : true
     * contract_period : 12
     * url : https://www.att.com/internet/
     * start_date : 04/23/2016 00:00:00
     * end_date : 09/13/2016 00:00:00
     * is_nationwide : true
     * deal_type : residence
     * is_active : true
     * is_sponsored : false
     * effective_price : 90.83
     * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1461410261/wd4czaew495b7d51doex.jpg
     * average_rating : 0
     * rating_count : 0
     * deal_price : 89.99
     * service_category_name : Bundle
     * service_provider_name : AT&T
     * deal_additional_offers : [{"id":11,"deal_id":49,"title":"Reward Cards","description":"","price":4.16,"start_date":null,"end_date":null,"is_nationwide":true,"is_active":true,"created_at":"04/23/2016 11:18:03","updated_at":"05/20/2016 10:33:00"}]
     * deal_equipments : [{"id":16,"bundle_deal_attribute_id":11,"name":"TiVo HD DVR","make":"","price":"5.0","installation":"Standard professional installation included","activation":"","offer":"","is_active":false,"created_at":"05/20/2016 10:33:00","updated_at":"05/20/2016 10:33:00","deal_id":49}]
     * bundle_deal_attributes : [{"id":11,"deal_id":49,"bundle_combo":"Internet,Telephone and Cable","download":6,"upload":null,"data":null,"static_ip":null,"domestic_call_minutes":"Unlimited","international_call_minutes":"Unlimited","free_channels":0,"free_channels_list":"","premium_channels":null,"premium_channels_list":"","hd_channels":null,"hd_channels_list":"","created_at":"04/23/2016 11:18:03","updated_at":"04/23/2016 11:18:03"}]
     * bundle_equipments : [{"id":16,"bundle_deal_attribute_id":11,"name":"TiVo HD DVR","make":"","price":"5.0","installation":"Standard professional installation included","activation":"","offer":"","is_active":false,"created_at":"05/20/2016 10:33:00","updated_at":"05/20/2016 10:33:00","deal_id":49}]
     */

    private DealsBean deals;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DealsBean getDeals() {
        return deals;
    }

    public void setDeals(DealsBean deals) {
        this.deals = deals;
    }

    public static class DealsBean {
        private int id;
        private int service_category_id;
        private int service_provider_id;
        private String title;
        private String short_description;
        private String detail_description;
        private boolean is_contract;
        private int contract_period;
        private String url;
        private String start_date;
        private String end_date;
        private boolean is_nationwide;
        private String deal_type;
        private boolean is_active;
        private boolean is_sponsored;
        private String effective_price;
        private String deal_image_url;
        private String average_rating;
        private String rating_count;
        private String deal_price;
        private String service_category_name;
        private String service_provider_name;
        private Boolean isOrderAvailable;
        public Boolean getOrderAvailable() {
            return isOrderAvailable;
        }

        public void setOrderAvailable(Boolean orderAvailable) {
            isOrderAvailable = orderAvailable;
        }


        /**
         * id : 11
         * deal_id : 49
         * title : Reward Cards
         * description :
         * price : 4.16
         * start_date : null
         * end_date : null
         * is_nationwide : true
         * is_active : true
         * created_at : 04/23/2016 11:18:03
         * updated_at : 05/20/2016 10:33:00
         */

        private List<DealAdditionalOffersBean> deal_additional_offers;
        /**
         * id : 16
         * bundle_deal_attribute_id : 11
         * name : TiVo HD DVR
         * make :
         * price : 5.0
         * installation : Standard professional installation included
         * activation :
         * offer :
         * is_active : false
         * created_at : 05/20/2016 10:33:00
         * updated_at : 05/20/2016 10:33:00
         * deal_id : 49
         */

        private List<DealEquipmentsBean> deal_equipments;
        /**
         * id : 11
         * deal_id : 49
         * bundle_combo : Internet,Telephone and Cable
         * download : 6.0
         * upload : null
         * data : null
         * static_ip : null
         * domestic_call_minutes : Unlimited
         * international_call_minutes : Unlimited
         * free_channels : 0
         * free_channels_list :
         * premium_channels : null
         * premium_channels_list :
         * hd_channels : null
         * hd_channels_list :
         * created_at : 04/23/2016 11:18:03
         * updated_at : 04/23/2016 11:18:03
         */

        private List<BundleDealAttributesBean> bundle_deal_attributes;
        /**
         * id : 16
         * bundle_deal_attribute_id : 11
         * name : TiVo HD DVR
         * make :
         * price : 5.0
         * installation : Standard professional installation included
         * activation :
         * offer :
         * is_active : false
         * created_at : 05/20/2016 10:33:00
         * updated_at : 05/20/2016 10:33:00
         * deal_id : 49
         */

        private List<BundleEquipmentsBean> bundle_equipments;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getService_category_id() {
            return service_category_id;
        }

        public void setService_category_id(int service_category_id) {
            this.service_category_id = service_category_id;
        }

        public int getService_provider_id() {
            return service_provider_id;
        }

        public void setService_provider_id(int service_provider_id) {
            this.service_provider_id = service_provider_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShort_description() {
            return short_description;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public String getDetail_description() {
            return detail_description;
        }

        public void setDetail_description(String detail_description) {
            this.detail_description = detail_description;
        }

        public boolean isIs_contract() {
            return is_contract;
        }

        public void setIs_contract(boolean is_contract) {
            this.is_contract = is_contract;
        }

        public int getContract_period() {
            return contract_period;
        }

        public void setContract_period(int contract_period) {
            this.contract_period = contract_period;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public boolean isIs_nationwide() {
            return is_nationwide;
        }

        public void setIs_nationwide(boolean is_nationwide) {
            this.is_nationwide = is_nationwide;
        }

        public String getDeal_type() {
            return deal_type;
        }

        public void setDeal_type(String deal_type) {
            this.deal_type = deal_type;
        }

        public boolean isIs_active() {
            return is_active;
        }

        public void setIs_active(boolean is_active) {
            this.is_active = is_active;
        }

        public boolean isIs_sponsored() {
            return is_sponsored;
        }

        public void setIs_sponsored(boolean is_sponsored) {
            this.is_sponsored = is_sponsored;
        }

        public String getEffective_price() {
            return effective_price;
        }

        public void setEffective_price(String effective_price) {
            this.effective_price = effective_price;
        }

        public String getDeal_image_url() {
            return deal_image_url;
        }

        public void setDeal_image_url(String deal_image_url) {
            this.deal_image_url = deal_image_url;
        }

        public String getAverage_rating() {
            return average_rating;
        }

        public void setAverage_rating(String average_rating) {
            this.average_rating = average_rating;
        }

        public String getRating_count() {
            return rating_count;
        }

        public void setRating_count(String rating_count) {
            this.rating_count = rating_count;
        }

        public String getDeal_price() {
            return deal_price;
        }

        public void setDeal_price(String deal_price) {
            this.deal_price = deal_price;
        }

        public String getService_category_name() {
            return service_category_name;
        }

        public void setService_category_name(String service_category_name) {
            this.service_category_name = service_category_name;
        }

        public String getService_provider_name() {
            return service_provider_name;
        }

        public void setService_provider_name(String service_provider_name) {
            this.service_provider_name = service_provider_name;
        }

        public List<DealAdditionalOffersBean> getDeal_additional_offers() {
            return deal_additional_offers;
        }

        public void setDeal_additional_offers(List<DealAdditionalOffersBean> deal_additional_offers) {
            this.deal_additional_offers = deal_additional_offers;
        }

        public List<DealEquipmentsBean> getDeal_equipments() {
            return deal_equipments;
        }

        public void setDeal_equipments(List<DealEquipmentsBean> deal_equipments) {
            this.deal_equipments = deal_equipments;
        }

        public List<BundleDealAttributesBean> getBundle_deal_attributes() {
            return bundle_deal_attributes;
        }

        public void setBundle_deal_attributes(List<BundleDealAttributesBean> bundle_deal_attributes) {
            this.bundle_deal_attributes = bundle_deal_attributes;
        }

        public List<BundleEquipmentsBean> getBundle_equipments() {
            return bundle_equipments;
        }

        public void setBundle_equipments(List<BundleEquipmentsBean> bundle_equipments) {
            this.bundle_equipments = bundle_equipments;
        }

        public static class DealAdditionalOffersBean {
            private int id;
            private int deal_id;
            private String title;
            private String description;
            private double price;
            private Object start_date;
            private Object end_date;
            private boolean is_nationwide;
            private boolean is_active;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(int deal_id) {
                this.deal_id = deal_id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public Object getStart_date() {
                return start_date;
            }

            public void setStart_date(Object start_date) {
                this.start_date = start_date;
            }

            public Object getEnd_date() {
                return end_date;
            }

            public void setEnd_date(Object end_date) {
                this.end_date = end_date;
            }

            public boolean isIs_nationwide() {
                return is_nationwide;
            }

            public void setIs_nationwide(boolean is_nationwide) {
                this.is_nationwide = is_nationwide;
            }

            public boolean isIs_active() {
                return is_active;
            }

            public void setIs_active(boolean is_active) {
                this.is_active = is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class DealEquipmentsBean {
            private int id;
            private int bundle_deal_attribute_id;
            private String name;
            private String make;
            private String price;
            private String installation;
            private String activation;
            private String offer;
            private boolean is_active;
            private String created_at;
            private String updated_at;
            private int deal_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBundle_deal_attribute_id() {
                return bundle_deal_attribute_id;
            }

            public void setBundle_deal_attribute_id(int bundle_deal_attribute_id) {
                this.bundle_deal_attribute_id = bundle_deal_attribute_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMake() {
                return make;
            }

            public void setMake(String make) {
                this.make = make;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getInstallation() {
                return installation;
            }

            public void setInstallation(String installation) {
                this.installation = installation;
            }

            public String getActivation() {
                return activation;
            }

            public void setActivation(String activation) {
                this.activation = activation;
            }

            public String getOffer() {
                return offer;
            }

            public void setOffer(String offer) {
                this.offer = offer;
            }

            public boolean isIs_active() {
                return is_active;
            }

            public void setIs_active(boolean is_active) {
                this.is_active = is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(int deal_id) {
                this.deal_id = deal_id;
            }
        }

        public static class BundleDealAttributesBean {
            private int id;
            private int deal_id;
            private String bundle_combo;
            private double download;
            private Object upload;
            private Object data;
            private Object static_ip;
            private String domestic_call_minutes;
            private String international_call_minutes;
            private int free_channels;
            private String free_channels_list;
            private Object premium_channels;
            private String premium_channels_list;
            private Object hd_channels;
            private String hd_channels_list;
            private String created_at;
            private String updated_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(int deal_id) {
                this.deal_id = deal_id;
            }

            public String getBundle_combo() {
                return bundle_combo;
            }

            public void setBundle_combo(String bundle_combo) {
                this.bundle_combo = bundle_combo;
            }

            public double getDownload() {
                return download;
            }

            public void setDownload(double download) {
                this.download = download;
            }

            public Object getUpload() {
                return upload;
            }

            public void setUpload(Object upload) {
                this.upload = upload;
            }

            public Object getData() {
                return data;
            }

            public void setData(Object data) {
                this.data = data;
            }

            public Object getStatic_ip() {
                return static_ip;
            }

            public void setStatic_ip(Object static_ip) {
                this.static_ip = static_ip;
            }

            public String getDomestic_call_minutes() {
                return domestic_call_minutes;
            }

            public void setDomestic_call_minutes(String domestic_call_minutes) {
                this.domestic_call_minutes = domestic_call_minutes;
            }

            public String getInternational_call_minutes() {
                return international_call_minutes;
            }

            public void setInternational_call_minutes(String international_call_minutes) {
                this.international_call_minutes = international_call_minutes;
            }

            public int getFree_channels() {
                return free_channels;
            }

            public void setFree_channels(int free_channels) {
                this.free_channels = free_channels;
            }

            public String getFree_channels_list() {
                return free_channels_list;
            }

            public void setFree_channels_list(String free_channels_list) {
                this.free_channels_list = free_channels_list;
            }

            public Object getPremium_channels() {
                return premium_channels;
            }

            public void setPremium_channels(Object premium_channels) {
                this.premium_channels = premium_channels;
            }

            public String getPremium_channels_list() {
                return premium_channels_list;
            }

            public void setPremium_channels_list(String premium_channels_list) {
                this.premium_channels_list = premium_channels_list;
            }

            public Object getHd_channels() {
                return hd_channels;
            }

            public void setHd_channels(Object hd_channels) {
                this.hd_channels = hd_channels;
            }

            public String getHd_channels_list() {
                return hd_channels_list;
            }

            public void setHd_channels_list(String hd_channels_list) {
                this.hd_channels_list = hd_channels_list;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }
        }

        public static class BundleEquipmentsBean {
            private int id;
            private int bundle_deal_attribute_id;
            private String name;
            private String make;
            private String price;
            private String installation;
            private String activation;
            private String offer;
            private boolean is_active;
            private String created_at;
            private String updated_at;
            private int deal_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBundle_deal_attribute_id() {
                return bundle_deal_attribute_id;
            }

            public void setBundle_deal_attribute_id(int bundle_deal_attribute_id) {
                this.bundle_deal_attribute_id = bundle_deal_attribute_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMake() {
                return make;
            }

            public void setMake(String make) {
                this.make = make;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getInstallation() {
                return installation;
            }

            public void setInstallation(String installation) {
                this.installation = installation;
            }

            public String getActivation() {
                return activation;
            }

            public void setActivation(String activation) {
                this.activation = activation;
            }

            public String getOffer() {
                return offer;
            }

            public void setOffer(String offer) {
                this.offer = offer;
            }

            public boolean isIs_active() {
                return is_active;
            }

            public void setIs_active(boolean is_active) {
                this.is_active = is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getDeal_id() {
                return deal_id;
            }

            public void setDeal_id(int deal_id) {
                this.deal_id = deal_id;
            }
        }
    }
}
