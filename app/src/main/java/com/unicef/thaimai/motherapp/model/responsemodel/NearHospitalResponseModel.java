package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NearHospitalResponseModel {

    private List<Nearby> nearby;

    public List<Nearby> getNearby() {
        return nearby;
    }

    public void setNearby(List<Nearby> nearby) {
        this.nearby = nearby;
    }

    public static class Nearby {
        private String distance;
        private String phcStatus;
        private String phcMobile;
        private String f_level;
        private String f_longititute;
        private String f_latitute;
        private String f_location;
        private String f_facility_name;
        private String f_nin_num;
        private String phcCode;
        private String phcName;
        private String f_sub_district_nam;
        private String f_district_name;
        private String phcId;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPhcStatus() {
            return phcStatus;
        }

        public void setPhcStatus(String phcStatus) {
            this.phcStatus = phcStatus;
        }

        public String getPhcMobile() {
            return phcMobile;
        }

        public void setPhcMobile(String phcMobile) {
            this.phcMobile = phcMobile;
        }

        public String getF_level() {
            return f_level;
        }

        public void setF_level(String f_level) {
            this.f_level = f_level;
        }

        public String getF_longititute() {
            return f_longititute;
        }

        public void setF_longititute(String f_longititute) {
            this.f_longititute = f_longititute;
        }

        public String getF_latitute() {
            return f_latitute;
        }

        public void setF_latitute(String f_latitute) {
            this.f_latitute = f_latitute;
        }

        public String getF_location() {
            return f_location;
        }

        public void setF_location(String f_location) {
            this.f_location = f_location;
        }

        public String getF_facility_name() {
            return f_facility_name;
        }

        public void setF_facility_name(String f_facility_name) {
            this.f_facility_name = f_facility_name;
        }

        public String getF_nin_num() {
            return f_nin_num;
        }

        public void setF_nin_num(String f_nin_num) {
            this.f_nin_num = f_nin_num;
        }

        public String getPhcCode() {
            return phcCode;
        }

        public void setPhcCode(String phcCode) {
            this.phcCode = phcCode;
        }

        public String getPhcName() {
            return phcName;
        }

        public void setPhcName(String phcName) {
            this.phcName = phcName;
        }

        public String getF_sub_district_nam() {
            return f_sub_district_nam;
        }

        public void setF_sub_district_nam(String f_sub_district_nam) {
            this.f_sub_district_nam = f_sub_district_nam;
        }

        public String getF_district_name() {
            return f_district_name;
        }

        public void setF_district_name(String f_district_name) {
            this.f_district_name = f_district_name;
        }

        public String getPhcId() {
            return phcId;
        }

        public void setPhcId(String phcId) {
            this.phcId = phcId;
        }
    }
}
