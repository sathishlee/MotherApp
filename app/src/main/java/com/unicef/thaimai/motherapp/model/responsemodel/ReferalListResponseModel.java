package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.List;

/**
 * Created by sathish on 3/18/2018.
 */

public class ReferalListResponseModel {

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
        private String referalStatus;
        private String rUpdateAdmitted;
        private String rUpdateInLabour;
        private String rUpdateReceivingFacility;
        private String rUpdateReceivedBy;
        private String rUpdateActualDateTime;
        private String rFacilityReferredToId;
        private String rFacilityReferringId;
        private String rUpdateTime;
        private String rUpdateDate;
        private String rReferalReason;
        private String rDiagonosis;
        private String rFacilityReferredTo_code;
        private String rFacilityReferredTo;
        private String rFacilityReferring_code;
        private String rFacilityReferring;
        private String rReferredBy;
        private String rActualReferalTime;
        private String rReferalTime;
        private String rReferalDate;
        private String picmeId;
        private String phcId;
        private String vhnId;
        private String mid;
        private String rid;

        public String getReferalStatus() {
            return referalStatus;
        }

        public void setReferalStatus(String referalStatus) {
            this.referalStatus = referalStatus;
        }

        public String getRUpdateAdmitted() {
            return rUpdateAdmitted;
        }

        public void setRUpdateAdmitted(String rUpdateAdmitted) {
            this.rUpdateAdmitted = rUpdateAdmitted;
        }


        public String getRUpdateInLabour() {
            return rUpdateInLabour;
        }

        public void setRUpdateInLabour(String rUpdateInLabour) {
            this.rUpdateInLabour = rUpdateInLabour;
        }

        public String getRUpdateReceivingFacility() {
            return rUpdateReceivingFacility;
        }

        public void setRUpdateReceivingFacility(String rUpdateReceivingFacility) {
            this.rUpdateReceivingFacility = rUpdateReceivingFacility;
        }

        public String getRUpdateReceivedBy() {
            return rUpdateReceivedBy;
        }

        public void setRUpdateReceivedBy(String rUpdateReceivedBy) {
            this.rUpdateReceivedBy = rUpdateReceivedBy;
        }

        public String getRUpdateActualDateTime() {
            return rUpdateActualDateTime;
        }

        public void setRUpdateActualDateTime(String rUpdateActualDateTime) {
            this.rUpdateActualDateTime = rUpdateActualDateTime;
        }

        public String getRFacilityReferredToId() {
            return rFacilityReferredToId;
        }

        public void setRFacilityReferredToId(String rFacilityReferredToId) {
            this.rFacilityReferredToId = rFacilityReferredToId;
        }

        public String getRFacilityReferringId() {
            return rFacilityReferringId;
        }

        public void setRFacilityReferringId(String rFacilityReferringId) {
            this.rFacilityReferringId = rFacilityReferringId;
        }

        public String getRUpdateTime() {
            return rUpdateTime;
        }

        public void setRUpdateTime(String rUpdateTime) {
            this.rUpdateTime = rUpdateTime;
        }

        public String getRUpdateDate() {
            return rUpdateDate;
        }

        public void setRUpdateDate(String rUpdateDate) {
            this.rUpdateDate = rUpdateDate;
        }

        public String getRReferalReason() {
            return rReferalReason;
        }

        public void setRReferalReason(String rReferalReason) {
            this.rReferalReason = rReferalReason;
        }

        public String getRDiagonosis() {
            return rDiagonosis;
        }

        public void setRDiagonosis(String rDiagonosis) {
            this.rDiagonosis = rDiagonosis;
        }

        public String getRFacilityReferredTo_code() {
            return rFacilityReferredTo_code;
        }

        public void setRFacilityReferredTo_code(String rFacilityReferredTo_code) {
            this.rFacilityReferredTo_code = rFacilityReferredTo_code;
        }

        public String getRFacilityReferredTo() {
            return rFacilityReferredTo;
        }

        public void setRFacilityReferredTo(String rFacilityReferredTo) {
            this.rFacilityReferredTo = rFacilityReferredTo;
        }

        public String getRFacilityReferring_code() {
            return rFacilityReferring_code;
        }

        public void setRFacilityReferring_code(String rFacilityReferring_code) {
            this.rFacilityReferring_code = rFacilityReferring_code;
        }

        public String getRFacilityReferring() {
            return rFacilityReferring;
        }

        public void setRFacilityReferring(String rFacilityReferring) {
            this.rFacilityReferring = rFacilityReferring;
        }

        public String getRReferredBy() {
            return rReferredBy;
        }

        public void setRReferredBy(String rReferredBy) {
            this.rReferredBy = rReferredBy;
        }

        public String getRActualReferalTime() {
            return rActualReferalTime;
        }

        public void setRActualReferalTime(String rActualReferalTime) {
            this.rActualReferalTime = rActualReferalTime;
        }

        public String getRReferalTime() {
            return rReferalTime;
        }

        public void setRReferalTime(String rReferalTime) {
            this.rReferalTime = rReferalTime;
        }

        public String getRReferalDate() {
            return rReferalDate;
        }

        public void setRReferalDate(String rReferalDate) {
            this.rReferalDate = rReferalDate;
        }

        public String getPicmeId() {
            return picmeId;
        }

        public void setPicmeId(String picmeId) {
            this.picmeId = picmeId;
        }

        public String getPhcId() {
            return phcId;
        }

        public void setPhcId(String phcId) {
            this.phcId = phcId;
        }

        public String getVhnId() {
            return vhnId;
        }

        public void setVhnId(String vhnId) {
            this.vhnId = vhnId;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getRid() {
            return rid;
        }

        public void setRid(String rid) {
            this.rid = rid;
        }
    }
}
