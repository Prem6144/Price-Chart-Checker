package com.spa.model.deallist;

import java.util.List;

/**
 * Created by Diwakar on 5/10/2016.
 */
public class DealListItem {
    /**
     * id : 91
     * service_category_id : 4
     * service_provider_id : 3
     * title : AT&T 5GB Plan
     * short_description : 1. Share data with all devices. Get unlimited talk and text on all phones.
     * 2. Rollover Data.
     * detail_description : 1. Talk, Text & Data: Share data with all devices. Get unlimited talk and text on all phones.
     * <p/>
     * 2. Rollover Data: The data you don't use this month rolls over to next month.
     * <p/>
     * 3. Overage rate: $20 per 300MB on 300MB plans, and $15 per 1GB on all other plans.
     * url : https://www.att.com/shop/wireless/plans/planconfigurator.html
     * start_date : 05/05/2016 00:00:00
     * end_date : 07/05/2016 00:00:00
     * is_nationwide : true
     * deal_type : residence
     * is_active : true
     * domestic_call_minutes : Unlimited
     * international_call_minutes : Unlimited
     * domestic_text :
     * international_text :
     * data_plan : 5.0
     * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1462445688/i4ciopcunbicmoahdase.jpg
     * average_rating : null
     * rating_count : 0
     * deal_price : 50.00
     * effective_price : 650.00
     * service_category_name : Cellphone
     * service_provider_name : AT&T
     * deal_additional_offers : [{"id":23,"deal_id":91,"title":"Free Self install Kit","description":"","price":10,"start_date":null,"end_date":null,"is_nationwide":true,"is_active":true,"created_at":"05/10/2016 07:09:47","updated_at":"05/10/2016 07:09:47"}]
     * deal_equipments : [{"id":1,"cellphone_deal_attribute_id":4,"model":"Apple 6s","make":"Apple","memory":32,"price":"600.0","installation":"free","activation":"free","offer":null,"is_active":null,"created_at":"05/10/2016 07:07:20","updated_at":"05/10/2016 07:07:20"}]
     */

    private List<DealEntity> deal;
    /**
     * id : 86
     * service_category_id : 5
     * service_provider_id : 61
     * title : Bronze Bundle W/ contour TV with 2 year service agreement
     * short_description : Up to 50 Mbps download speed
     * 230+ channels
     * detail_description : Price lock guarantee with 24 month of service agreement.
     * Get a free pro install and a $100 visa prepaid card.
     * Up to 50 Mbps download speed
     * 230+ channels
     * 13 calling feature
     * <p>
     * is_contract : false
     * contract_period : 0
     * url : https://www.cox.com/residential/special-offers/bundles.html
     * start_date : 04/29/2016 00:00:00
     * end_date : 07/31/2016 00:00:00
     * is_nationwide : true
     * deal_type : residence
     * is_active : true
     * effective_price : -8
     * is_sponsored : false
     * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1461930189/tgz9if6dptrqzbdeqpdk.jpg
     * average_rating : 0
     * rating_count : 0
     * deal_price : 91.99
     * service_category_name : Bundle
     * service_provider_name : Cox
     * deal_additional_offers : [{"id":15,"deal_id":86,"title":"Pre paid card $100","description":"Pre paid card $100","price":100,"start_date":"04/29/2016 00:00:00","end_date":"06/27/2016 00:00:00","is_nationwide":false,"is_active":true,"created_at":"04/29/2016 11:43:43","updated_at":"04/29/2016 11:43:43"}]
     * deal_equipments : []
     */

    private List<BundleDealsEntity> bundle_deals;

    public void setDeal(List<DealEntity> deal) {
        this.deal = deal;
    }

    public List<DealEntity> getDeal() {
        return deal;
    }

    public List<BundleDealsEntity> getBundle_deals() {
        return bundle_deals;
    }

    public void setBundle_deals(List<BundleDealsEntity> bundle_deals) {
        this.bundle_deals = bundle_deals;
    }

    public static class DealEntity {

        public String getFree_channels() {
            return free_channels;
        }

        public void setFree_channels(String free_channels) {
            this.free_channels = free_channels;
        }

        public String getCountries() {
            return countries;
        }

        public void setCountries(String countries) {
            this.countries = countries;
        }

        public String getFeatures() {
            return features;
        }

        public void setFeatures(String features) {
            this.features = features;
        }

        public String getCall_minutes() {
            return call_minutes;
        }

        public void setCall_minutes(String call_minutes) {
            this.call_minutes = call_minutes;
        }

        public String getText_messages() {
            return text_messages;
        }

        public void setText_messages(String text_messages) {
            this.text_messages = text_messages;
        }

        public String getFree_channels_list() {
            return free_channels_list;
        }

        public void setFree_channels_list(String free_channels_list) {
            this.free_channels_list = free_channels_list;
        }

        public String getPremium_channels_list() {
            return premium_channels_list;
        }

        public void setPremium_channels_list(String premium_channels_list) {
            if (null != premium_channels_list)
                this.premium_channels_list = premium_channels_list;
        }

        public String getDownload_speed() {
            return download_speed;
        }

        public void setDownload_speed(String download_speed) {
            this.download_speed = download_speed;
        }

        public String getUpload_speed() {
            return upload_speed;
        }

        public void setUpload_speed(String upload_speed) {
            if (null != upload_speed)
                this.upload_speed = upload_speed;
        }

        public String getIs_nationwide() {
            return is_nationwide;
        }

        public String getIs_active() {
            return is_active;
        }

        private String countries = "";
        private String features = "";
        private String call_minutes = "";
        private String text_messages = "";
        private String free_channels = "";

        public String getContract_period() {
            return contract_period;
        }

        public void setContract_period(String contract_period) {
            this.contract_period = contract_period;
        }

        private String contract_period = "";

        public String getPremium_channels() {
            return premium_channels;
        }

        public void setPremium_channels(String premium_channels) {
            if (null != premium_channels)
                this.premium_channels = premium_channels;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }

        private String share_url;

        public String getNo_of_lines() {
            return no_of_lines;
        }

        public void setNo_of_lines(String no_of_lines) {
            this.no_of_lines = no_of_lines;
        }

        private String no_of_lines = "";
        private String premium_channels = "";
        private String free_channels_list = "";
        private String premium_channels_list = "";
        private String download_speed = "";
        private String upload_speed = "";


        private int id;
        private String service_category_id;
        private String service_provider_id;
        private String title;

        public boolean is_order_available() {
            return is_order_available;
        }

        public void setIs_order_available(boolean is_order_available) {
            this.is_order_available = is_order_available;
        }

        private boolean is_order_available;
        private String short_description;
        private String detail_description;
        private String url;
        private String start_date;
        private String end_date;
        private String is_nationwide;
        private String deal_type;
        private String is_active;
        private String domestic_call_minutes = "";
        private String international_call_minutes = "";
        private String domestic_text;
        private String international_text;
        private String data_plan;
        private String deal_image_url;
        private String average_rating;
        private String rating_count;
        private String deal_price;
        private String effective_price;
        private String service_category_name;
        private String service_provider_name;
        private Boolean customisable;


        public Boolean getCustomisable() {
            return customisable;
        }

        public void setCustomisable(Boolean customisable) {
            this.customisable = customisable;
        }


        /**
         * id : 23
         * deal_id : 91
         * title : Free Self install Kit
         * description :
         * price : 10.0
         * start_date : null
         * end_date : null
         * is_nationwide : true
         * is_active : true
         * created_at : 05/10/2016 07:09:47
         * updated_at : 05/10/2016 07:09:47
         */

        private List<DealAdditionalOffersEntity> deal_additional_offers;
        /**
         * id : 1
         * cellphone_deal_attribute_id : 4
         * model : Apple 6s
         * make : Apple
         * memory : 32
         * price : 600.0
         * installation : free
         * activation : free
         * offer : null
         * is_active : null
         * created_at : 05/10/2016 07:07:20
         * updated_at : 05/10/2016 07:07:20
         */

        private List<DealEquipmentsEntity> deal_equipments;

        public void setId(int id) {
            this.id = id;
        }

        public void setService_category_id(String service_category_id) {
            this.service_category_id = service_category_id;
        }

        public void setService_provider_id(String service_provider_id) {
            this.service_provider_id = service_provider_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setShort_description(String short_description) {
            this.short_description = short_description;
        }

        public void setDetail_description(String detail_description) {
            this.detail_description = detail_description;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public void setIs_nationwide(String is_nationwide) {
            this.is_nationwide = is_nationwide;
        }

        public void setDeal_type(String deal_type) {
            this.deal_type = deal_type;
        }

        public void setIs_active(String is_active) {
            this.is_active = is_active;
        }

        public void setDomestic_call_minutes(String domestic_call_minutes) {
            this.domestic_call_minutes = domestic_call_minutes;
        }

        public void setInternational_call_minutes(String international_call_minutes) {
            if (null != international_call_minutes)
                this.international_call_minutes = international_call_minutes;
        }

        public void setDomestic_text(String domestic_text) {
            this.domestic_text = domestic_text;
        }

        public void setInternational_text(String international_text) {
            this.international_text = international_text;
        }

        public void setData_plan(String data_plan) {
            this.data_plan = data_plan;
        }

        public void setDeal_image_url(String deal_image_url) {
            this.deal_image_url = deal_image_url;
        }

        public void setAverage_rating(String average_rating) {
            this.average_rating = average_rating;
        }

        public void setRating_count(String rating_count) {
            this.rating_count = rating_count;
        }

        public void setDeal_price(String deal_price) {
            this.deal_price = deal_price;
        }

        public void setEffective_price(String effective_price) {
            this.effective_price = effective_price;
        }

        public void setService_category_name(String service_category_name) {
            this.service_category_name = service_category_name;
        }

        public void setService_provider_name(String service_provider_name) {
            this.service_provider_name = service_provider_name;
        }

        public void setDeal_additional_offers(List<DealAdditionalOffersEntity> deal_additional_offers) {
            this.deal_additional_offers = deal_additional_offers;
        }

        public void setDeal_equipments(List<DealEquipmentsEntity> deal_equipments) {
            this.deal_equipments = deal_equipments;
        }

        public int getId() {
            return id;
        }

        public String getService_category_id() {
            return service_category_id;
        }

        public String getService_provider_id() {
            return service_provider_id;
        }

        public String getTitle() {
            return title;
        }

        public String getShort_description() {
            return short_description;
        }

        public String getDetail_description() {
            return detail_description;
        }

        public String getUrl() {
            return url;
        }

        public String getStart_date() {
            return start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public String isIs_nationwide() {
            return is_nationwide;
        }

        public String getDeal_type() {
            return deal_type;
        }

        public String isIs_active() {
            return is_active;
        }

        public String getDomestic_call_minutes() {
            return domestic_call_minutes;
        }

        public String getInternational_call_minutes() {
            return international_call_minutes;
        }

        public String getDomestic_text() {
            return domestic_text;
        }

        public String getInternational_text() {
            return international_text;
        }

        public String getData_plan() {
            return data_plan;
        }

        public String getDeal_image_url() {
            return deal_image_url;
        }

        public String getAverage_rating() {
            return average_rating;
        }

        public String getRating_count() {
            return rating_count;
        }

        public String getDeal_price() {
            return deal_price;
        }

        public String getEffective_price() {
            return effective_price;
        }

        public String getService_category_name() {
            return service_category_name;
        }

        public String getService_provider_name() {
            return service_provider_name;
        }

        public List<DealAdditionalOffersEntity> getDeal_additional_offers() {
            return deal_additional_offers;
        }

        public List<DealEquipmentsEntity> getDeal_equipments() {
            return deal_equipments;
        }

        public static class DealAdditionalOffersEntity {
            private String id;
            private String deal_id;
            private String title;
            private String description;
            private String price;
            private String start_date;
            private String end_date;
            private String is_nationwide;
            private String is_active;
            private String created_at;
            private String updated_at;

            public void setId(String id) {
                this.id = id;
            }

            public void setDeal_id(String deal_id) {
                this.deal_id = deal_id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setStart_date(String start_date) {
                this.start_date = start_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public void setIs_nationwide(String is_nationwide) {
                this.is_nationwide = is_nationwide;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getId() {
                return id;
            }

            public String getDeal_id() {
                return deal_id;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }

            public String getPrice() {
                return price;
            }

            public String getStart_date() {
                return start_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public String isIs_nationwide() {
                return is_nationwide;
            }

            public String isIs_active() {
                return is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }

        public static class DealEquipmentsEntity {
            private String id;
            private String cellphone_deal_attribute_id;
            private String model;
            private String make;
            private String memory;
            private String price;
            private String installation;
            private String activation;
            private String offer;
            private String is_active;
            private String created_at;
            private String updated_at;

            public void setId(String id) {
                this.id = id;
            }

            public void setCellphone_deal_attribute_id(String cellphone_deal_attribute_id) {
                this.cellphone_deal_attribute_id = cellphone_deal_attribute_id;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setMake(String make) {
                this.make = make;
            }

            public void setMemory(String memory) {
                this.memory = memory;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setInstallation(String installation) {
                this.installation = installation;
            }

            public void setActivation(String activation) {
                this.activation = activation;
            }

            public void setOffer(String offer) {
                this.offer = offer;
            }

            public void setIs_active(String is_active) {
                this.is_active = is_active;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getId() {
                return id;
            }

            public String getCellphone_deal_attribute_id() {
                return cellphone_deal_attribute_id;
            }

            public String getModel() {
                return model;
            }

            public String getMake() {
                return make;
            }

            public String getMemory() {
                return memory;
            }

            public String getPrice() {
                return price;
            }

            public String getInstallation() {
                return installation;
            }

            public String getActivation() {
                return activation;
            }

            public String getOffer() {
                return offer;
            }

            public String getIs_active() {
                return is_active;
            }

            public String getCreated_at() {
                return created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }
        }
    }

    public static class BundleDealsEntity {
        private String id;
        private String service_category_id;
        private String service_provider_id;
        private String title;

        public String getBundle_combo() {
            return bundle_combo;
        }

        public void setBundle_combo(String bundle_combo) {
            this.bundle_combo = bundle_combo;
        }

        private String bundle_combo;
        private String short_description;
        private String detail_description;
        private boolean is_contract;
        private String contract_period;
        private String url;

        public boolean is_order_available() {
            return is_order_available;
        }

        public void setIs_order_available(boolean is_order_available) {
            this.is_order_available = is_order_available;
        }

        private boolean is_order_available;
        private String start_date;
        private String end_date;
        private boolean is_nationwide;
        private String deal_type;
        private boolean is_active;
        private String effective_price;
        private boolean is_sponsored;
        private String deal_image_url;
        private String average_rating;
        private String rating_count;
        private String deal_price;
        private String service_category_name;
        private String service_provider_name;
        /**
         * id : 15
         * deal_id : 86
         * title : Pre paid card $100
         * description : Pre paid card $100
         * price : 100
         * start_date : 04/29/2016 00:00:00
         * end_date : 06/27/2016 00:00:00
         * is_nationwide : false
         * is_active : true
         * created_at : 04/29/2016 11:43:43
         * updated_at : 04/29/2016 11:43:43
         */

        private List<DealAdditionalOffersEntity> deal_additional_offers;
        private List<?> deal_equipments;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getService_category_id() {
            return service_category_id;
        }

        public void setService_category_id(String service_category_id) {
            this.service_category_id = service_category_id;
        }

        public String getService_provider_id() {
            return service_provider_id;
        }

        public void setService_provider_id(String service_provider_id) {
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

        public String getContract_period() {
            return contract_period;
        }

        public void setContract_period(String contract_period) {
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

        public String getEffective_price() {
            return effective_price;
        }

        public void setEffective_price(String effective_price) {
            this.effective_price = effective_price;
        }

        public boolean isIs_sponsored() {
            return is_sponsored;
        }

        public void setIs_sponsored(boolean is_sponsored) {
            this.is_sponsored = is_sponsored;
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

        public List<DealAdditionalOffersEntity> getDeal_additional_offers() {
            return deal_additional_offers;
        }

        public void setDeal_additional_offers(List<DealAdditionalOffersEntity> deal_additional_offers) {
            this.deal_additional_offers = deal_additional_offers;
        }

        public List<?> getDeal_equipments() {
            return deal_equipments;
        }

        public void setDeal_equipments(List<?> deal_equipments) {
            this.deal_equipments = deal_equipments;
        }

        public static class DealAdditionalOffersEntity {
            private int id;
            private int deal_id;
            private String title;
            private String description;
            private int price;
            private String start_date;
            private String end_date;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
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
    }
}
