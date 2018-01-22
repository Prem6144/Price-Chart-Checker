package com.spa.model.placeorder;

import java.util.List;

/**
 * Created by Diwakar on 10/20/2016.
 */

public class CustemCellphoneEquipmentsAdditionalOffers {
    /**
     * phonecolor :
     * phoneprice :
     * phonename :
     * phoneid :
     * phonebrand : English Language,Mathematics,Economics,Commerce
     */

    private List<DevicelistEntity> devicelist;

    public List<DevicelistEntity> getDevicelist() {
        return devicelist;
    }

    public void setDevicelist(List<DevicelistEntity> devicelist) {
        this.devicelist = devicelist;
    }

    public static class DevicelistEntity {
        private String phonecolor;
        private String phoneprice;
        private String phonename;
        private String phoneImage;
        private String phoneid;
        private String phonebrand;

//        public String getEquipementCellphoneDealId() {
//            return EquipementCellphoneDealId;
//        }
//
//        public void setEquipementCellphoneDealId(String equipementCellphoneDealId) {
//            EquipementCellphoneDealId = equipementCellphoneDealId;
//        }


        public String getPhoneImage() {
            return phoneImage;
        }

        public void setPhoneImage(String phoneImage) {
            this.phoneImage = phoneImage;
        }


        public String getPhonecolor() {
            return phonecolor;
        }

        public void setPhonecolor(String phonecolor) {
            this.phonecolor = phonecolor;
        }

        public String getPhoneprice() {
            return phoneprice;
        }

        public void setPhoneprice(String phoneprice) {
            this.phoneprice = phoneprice;
        }

        public String getPhonename() {
            return phonename;
        }

        public void setPhonename(String phonename) {
            this.phonename = phonename;
        }

        public String getPhoneid() {
            return phoneid;
        }

        public void setPhoneid(String phoneid) {
            this.phoneid = phoneid;
        }

        public String getPhonebrand() {
            return phonebrand;
        }

        public void setPhonebrand(String phonebrand) {
            this.phonebrand = phonebrand;
        }
    }
}
