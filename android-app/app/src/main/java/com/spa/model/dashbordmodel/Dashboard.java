package com.spa.model.dashbordmodel;

import java.util.List;

/**
 * Created by Diwakar on 5/3/2016.
 */
public class Dashboard {
    /**
     * you_save_text : 220.01
     * contract_fee : 250.0
     * service_provider_name : AT&T
     * service_category_id : 5
     * service_category_name : Bundle
     * advertisement : null
     * trending_deal : {"id":29,"service_category_id":5,"service_provider_id":15,"title":"$44.99 TV, Internet Package","short_description":"20+ channels\r\nSpeeds up to 50Mbps\r\n$44.99/mo for 12 months","detail_description":"Includes:\r\nDigital Adapter: A small device that allows digital signals to be displayed on your analog TV.\r\n\r\nTWC TV® and Start Over®: Watch live TV on your tablet, computer or smartphone at home or on the go.\r\n\r\nTWC WiFi® Hotspots: Get fast and reliable wireless Internet access when you\u2019re on-the-go at any TWC WiFi Hotspot.","is_contract":true,"contract_period":12,"url":"http://www.timewarnercable.com/en/plans-packages/cable-internet.html?iid=hppromostrip:1:1:shop-offers","start_date":"04/20/2016 00:00:00","end_date":"06/18/2016 00:00:00","is_nationwide":true,"deal_type":"residence","is_active":true,"deal_image_url":"http://res.cloudinary.com/softwareassurance/image/upload/v1461141058/xvalsm9cfxymhtbiinxw.jpg","average_rating":5,"rating_count":2,"deal_price":"44.99","effective_price":"54.99"}
     * best_deal : {"id":79,"service_category_id":5,"service_provider_id":107,"title":"Triple Play Select","short_description":"60 Mbps Internet.\r\n125+ Channels, Unlimited Nationwide Calling.","detail_description":"1. Thousands of On Demand choices\r\n2. Take TV on the go with the Charter App or mobile channel apps.\r\n3. Blazing-fast 60 Mbps Internet speeds \u2013 20x faster than DSL.\r\n4. Unlimited local and long distance calling.\r\n5. Free HD, Free internet modem, Free Security Suite.","is_contract":false,"contract_period":0,"url":"https://www.charter.com/browse/content/lfo-7n?v=LFOSEM&affpn=1-877-426-8845&cmp=pds_lfo&o=sem&kob=false&lfokp=charter%20package&TransID=55670094725::charter%20package::b::Google::c&gclid=CO6Z8Zyds8wCFZY1aQodbMABcQ&gclsrc=aw.ds","start_date":"04/29/2016 00:00:00","end_date":"06/15/2016 00:00:00","is_nationwide":true,"deal_type":"residence","is_active":true,"deal_image_url":"http://res.cloudinary.com/softwareassurance/image/upload/v1461923995/zx3xwvuia67tyzroyl8x.png","average_rating":5,"rating_count":1,"deal_price":"29.99","effective_price":0}
     * order_deal : {"id":80,"service_category_id":5,"service_provider_id":107,"title":"Triple Play Silver","short_description":"175+ Channels, 60 Mbps Internet\r\nUnlimited Nationwide Calling","detail_description":"1. Thousands of On Demand choices\r\n2. Take TV on the go with the Charter App.\r\n3. Blazing-fast 60 Mbps Internet speeds \u2013 20x faster than DSL\r\n4. Unlimited local and long distance calling with NO added fees.\r\n5. Free HD, Free internet modem, Free Security Suite.","is_contract":false,"contract_period":0,"url":"https://www.charter.com/browse/content/lfo-7n?v=LFOSEM&affpn=1-877-426-8845&cmp=pds_lfo&o=sem&kob=false&lfokp=charter%20package&TransID=55670094725::charter%20package::b::Google::c&gclid=CO6Z8Zyds8wCFZY1aQodbMABcQ&gclsrc=aw.ds","start_date":"04/29/2016 00:00:00","end_date":"06/15/2016 00:00:00","is_nationwide":true,"deal_type":"residence","is_active":true,"deal_image_url":"http://res.cloudinary.com/softwareassurance/image/upload/v1461925583/ybn08evxwxfdvdrvmosw.png","average_rating":0,"rating_count":0,"deal_price":"49.99","effective_price":0}
     */

    private List<DashboardDataEntity> dashboard_data;

    public List<DashboardDataEntity> getDashboard_data() {
        return dashboard_data;
    }

    public void setDashboard_data(List<DashboardDataEntity> dashboard_data) {
        this.dashboard_data = dashboard_data;
    }

    public static class DashboardDataEntity {
        private String you_save_text;
        private String contract_fee;
        private String service_provider_name;
        private int service_category_id;

        public boolean isBest_deal_flag() {
            return best_deal_flag;
        }

        public void setBest_deal_flag(boolean best_deal_flag) {
            this.best_deal_flag = best_deal_flag;
        }

        boolean best_deal_flag;
        private String service_category_name;
        private Object advertisement;
        /**
         * id : 29
         * service_category_id : 5
         * service_provider_id : 15
         * title : $44.99 TV, Internet Package
         * short_description : 20+ channels
         Speeds up to 50Mbps
         $44.99/mo for 12 months
         * detail_description : Includes:
         Digital Adapter: A small device that allows digital signals to be displayed on your analog TV.

         TWC TV® and Start Over®: Watch live TV on your tablet, computer or smartphone at home or on the go.

         TWC WiFi® Hotspots: Get fast and reliable wireless Internet access when you’re on-the-go at any TWC WiFi Hotspot.
         * is_contract : true
         * contract_period : 12
         * url : http://www.timewarnercable.com/en/plans-packages/cable-internet.html?iid=hppromostrip:1:1:shop-offers
         * start_date : 04/20/2016 00:00:00
         * end_date : 06/18/2016 00:00:00
         * is_nationwide : true
         * deal_type : residence
         * is_active : true
         * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1461141058/xvalsm9cfxymhtbiinxw.jpg
         * average_rating : 5.0
         * rating_count : 2
         * deal_price : 44.99
         * effective_price : 54.99
         */

        private TrendingDealEntity trending_deal;
        /**
         * id : 79
         * service_category_id : 5
         * service_provider_id : 107
         * title : Triple Play Select
         * short_description : 60 Mbps Internet.
         125+ Channels, Unlimited Nationwide Calling.
         * detail_description : 1. Thousands of On Demand choices
         2. Take TV on the go with the Charter App or mobile channel apps.
         3. Blazing-fast 60 Mbps Internet speeds – 20x faster than DSL.
         4. Unlimited local and long distance calling.
         5. Free HD, Free internet modem, Free Security Suite.
         * is_contract : false
         * contract_period : 0
         * url : https://www.charter.com/browse/content/lfo-7n?v=LFOSEM&affpn=1-877-426-8845&cmp=pds_lfo&o=sem&kob=false&lfokp=charter%20package&TransID=55670094725::charter%20package::b::Google::c&gclid=CO6Z8Zyds8wCFZY1aQodbMABcQ&gclsrc=aw.ds
         * start_date : 04/29/2016 00:00:00
         * end_date : 06/15/2016 00:00:00
         * is_nationwide : true
         * deal_type : residence
         * is_active : true
         * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1461923995/zx3xwvuia67tyzroyl8x.png
         * average_rating : 5.0
         * rating_count : 1
         * deal_price : 29.99
         * effective_price : 0
         */

        private BestDealEntity best_deal;
        /**
         * id : 80
         * service_category_id : 5
         * service_provider_id : 107
         * title : Triple Play Silver
         * short_description : 175+ Channels, 60 Mbps Internet
         Unlimited Nationwide Calling
         * detail_description : 1. Thousands of On Demand choices
         2. Take TV on the go with the Charter App.
         3. Blazing-fast 60 Mbps Internet speeds – 20x faster than DSL
         4. Unlimited local and long distance calling with NO added fees.
         5. Free HD, Free internet modem, Free Security Suite.
         * is_contract : false
         * contract_period : 0
         * url : https://www.charter.com/browse/content/lfo-7n?v=LFOSEM&affpn=1-877-426-8845&cmp=pds_lfo&o=sem&kob=false&lfokp=charter%20package&TransID=55670094725::charter%20package::b::Google::c&gclid=CO6Z8Zyds8wCFZY1aQodbMABcQ&gclsrc=aw.ds
         * start_date : 04/29/2016 00:00:00
         * end_date : 06/15/2016 00:00:00
         * is_nationwide : true
         * deal_type : residence
         * is_active : true
         * deal_image_url : http://res.cloudinary.com/softwareassurance/image/upload/v1461925583/ybn08evxwxfdvdrvmosw.png
         * average_rating : 0
         * rating_count : 0
         * deal_price : 49.99
         * effective_price : 0
         */

        private OrderDealEntity order_deal;

        public String getYou_save_text() {
            return you_save_text;
        }

        public void setYou_save_text(String you_save_text) {
            this.you_save_text = you_save_text;
        }

        public String getContract_fee() {
            return contract_fee;
        }

        public void setContract_fee(String contract_fee) {
            this.contract_fee = contract_fee;
        }

        public String getService_provider_name() {
            return service_provider_name;
        }

        public void setService_provider_name(String service_provider_name) {
            this.service_provider_name = service_provider_name;
        }

        public int getService_category_id() {
            return service_category_id;
        }

        public void setService_category_id(int service_category_id) {
            this.service_category_id = service_category_id;
        }

        public String getService_category_name() {
            return service_category_name;
        }

        public void setService_category_name(String service_category_name) {
            this.service_category_name = service_category_name;
        }

        public Object getAdvertisement() {
            return advertisement;
        }

        public void setAdvertisement(Object advertisement) {
            this.advertisement = advertisement;
        }

        public TrendingDealEntity getTrending_deal() {
            return trending_deal;
        }

        public void setTrending_deal(TrendingDealEntity trending_deal) {
            this.trending_deal = trending_deal;
        }

        public BestDealEntity getBest_deal() {
            return best_deal;
        }

        public void setBest_deal(BestDealEntity best_deal) {
            this.best_deal = best_deal;
        }

        public OrderDealEntity getOrder_deal() {
            return order_deal;
        }

        public void setOrder_deal(OrderDealEntity order_deal) {
            this.order_deal = order_deal;
        }

        public static class TrendingDealEntity {
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
            private String deal_image_url;
            private String average_rating;
            private int rating_count;
            private String deal_price;
            private String effective_price;

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

            public int getRating_count() {
                return rating_count;
            }

            public void setRating_count(int rating_count) {
                this.rating_count = rating_count;
            }

            public String getDeal_price() {
                return deal_price;
            }

            public void setDeal_price(String deal_price) {
                this.deal_price = deal_price;
            }

            public String getEffective_price() {
                return effective_price;
            }

            public void setEffective_price(String effective_price) {
                this.effective_price = effective_price;
            }
        }

        public static class BestDealEntity {
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
            private String deal_image_url;
            private String average_rating;
            private int rating_count;
            private String deal_price;
            private String effective_price;

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

            public int getRating_count() {
                return rating_count;
            }

            public void setRating_count(int rating_count) {
                this.rating_count = rating_count;
            }

            public String getDeal_price() {
                return deal_price;
            }

            public void setDeal_price(String deal_price) {
                this.deal_price = deal_price;
            }

            public String getEffective_price() {
                return effective_price;
            }

            public void setEffective_price(String effective_price) {
                this.effective_price = effective_price;
            }
        }

        public static class OrderDealEntity {
            private int id;
            private int service_category_id;
            private int service_provider_id;

            public String getOrder_status() {
                return order_status;
            }

            public void setOrder_status(String order_status) {
                this.order_status = order_status;
            }

            private String order_status;
            private String title;
            private String short_description;
            private String detail_description;
            private boolean is_contract;

            public int getOrder_id() {
                return order_id;
            }

            public void setOrder_id(int order_id) {
                this.order_id = order_id;
            }

            private int order_id;
            private int contract_period;
            private String url;
            private String start_date;
            private String end_date;
            private boolean is_nationwide;
            private String deal_type;
            private boolean is_active;
            private String deal_image_url;
            private int average_rating;
            private int rating_count;
            private String deal_price;
            private String effective_price;

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

            public String getDeal_image_url() {
                return deal_image_url;
            }

            public void setDeal_image_url(String deal_image_url) {
                this.deal_image_url = deal_image_url;
            }

            public int getAverage_rating() {
                return average_rating;
            }

            public void setAverage_rating(int average_rating) {
                this.average_rating = average_rating;
            }

            public int getRating_count() {
                return rating_count;
            }

            public void setRating_count(int rating_count) {
                this.rating_count = rating_count;
            }

            public String getDeal_price() {
                return deal_price;
            }

            public void setDeal_price(String deal_price) {
                this.deal_price = deal_price;
            }

            public String getEffective_price() {
                return effective_price;
            }

            public void setEffective_price(String effective_price) {
                this.effective_price = effective_price;
            }
        }
    }
}