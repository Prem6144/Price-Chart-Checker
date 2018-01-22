package com.spa.model.custemizedealdetail;

import java.util.List;

/**
 * Created by Diwakar on 10/19/2016.
 */

public class EquipmentSelected {
    /**
     * Equipmentsposition : A
     * devicelist : [{"phonecolor":"","phoneprice":"","phonename":"","phonebrand":"English Language,Mathematics,Economics,Commerce"}]
     */

    private List<ListDeviceEntity> ListDevice;

    public List<ListDeviceEntity> getListDevice() {
        return ListDevice;
    }

    public void setListDevice(List<ListDeviceEntity> ListDevice) {
        this.ListDevice = ListDevice;
    }

    public static class ListDeviceEntity {
        private String Equipmentsposition;
        /**
         * phonecolor :
         * phoneprice :
         * phonename :
         * phonebrand : English Language,Mathematics,Economics,Commerce
         */

        private List<DevicelistEntity> devicelist;

        public String getEquipmentsposition() {
            return Equipmentsposition;
        }

        public void setEquipmentsposition(String Equipmentsposition) {
            this.Equipmentsposition = Equipmentsposition;
        }

        public List<DevicelistEntity> getDevicelist() {
            return devicelist;
        }

        public void setDevicelist(List<DevicelistEntity> devicelist) {
            this.devicelist = devicelist;
        }

        public static class DevicelistEntity {
            private String phonecolor;
            private String phoneprice;

            public String getPhoneImageUrl() {
                return phoneImageUrl;
            }

            public void setPhoneImageUrl(String phoneImageUrl) {
                this.phoneImageUrl = phoneImageUrl;
            }

            private String phoneImageUrl;
            private String phonename;
            private String phonebrand;

            public String getPhoneid() {
                return phoneid;
            }

            public void setPhoneid(String phoneid) {
                this.phoneid = phoneid;
            }

            private String phoneid;

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

            public String getPhonebrand() {
                return phonebrand;
            }

            public void setPhonebrand(String phonebrand) {
                this.phonebrand = phonebrand;
            }
        }
    }
}
