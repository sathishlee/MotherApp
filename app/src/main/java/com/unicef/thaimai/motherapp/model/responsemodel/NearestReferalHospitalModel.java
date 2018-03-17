package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.List;

/**
 * Created by sathish on 3/17/2018.
 */

public class NearestReferalHospitalModel {

    private List<NearestHospitals> nearestHospitals;
    private String message;
    private String status;

    public List<NearestHospitals> getNearestHospitals() {
        return nearestHospitals;
    }

    public void setNearestHospitals(List<NearestHospitals> nearestHospitals) {
        this.nearestHospitals = nearestHospitals;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class NearestHospitals {
        private String distance;
        private String phcCode;
        private String phcId;

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getPhcCode() {
            return phcCode;
        }

        public void setPhcCode(String phcCode) {
            this.phcCode = phcCode;
        }

        public String getPhcId() {
            return phcId;
        }

        public void setPhcId(String phcId) {
            this.phcId = phcId;
        }
    }
}
