package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.List;

/**
 * Created by sathish on 3/13/2018.
 */

public class PnHbncVisitRecordsModel {

    private List<Visit_Records> Visit_Records;
    private String message;
    private int status;

    public List<Visit_Records> getVisit_Records() {
        return Visit_Records;
    }

    public void setVisit_Records(List<Visit_Records> Visit_Records) {
        this.Visit_Records = Visit_Records;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Visit_Records {
        private String pnOutCome;
        private String pnBreastExamination;
        private String pnBreastFeedingReason;
        private String pnBreastFeeding;
        private String pnPVDischarge;
        private String pnEpistomyTear;
        private String pnTemp;
        private String pnPulseRate;
        private String pnBPDiastolic;
        private String pnBPSystolic;
        private String pnAnyComplaints;
        private String pnPlace;
        private String pnCareProvidedDate;
        private String pnDueDate;
        private String pnVisitNo;
        private String picmeId;
        private String mid;
        private String pnId;

        public String getPnOutCome() {
            return pnOutCome;
        }

        public void setPnOutCome(String pnOutCome) {
            this.pnOutCome = pnOutCome;
        }

        public String getPnBreastExamination() {
            return pnBreastExamination;
        }

        public void setPnBreastExamination(String pnBreastExamination) {
            this.pnBreastExamination = pnBreastExamination;
        }

        public String getPnBreastFeedingReason() {
            return pnBreastFeedingReason;
        }

        public void setPnBreastFeedingReason(String pnBreastFeedingReason) {
            this.pnBreastFeedingReason = pnBreastFeedingReason;
        }

        public String getPnBreastFeeding() {
            return pnBreastFeeding;
        }

        public void setPnBreastFeeding(String pnBreastFeeding) {
            this.pnBreastFeeding = pnBreastFeeding;
        }

        public String getPnPVDischarge() {
            return pnPVDischarge;
        }

        public void setPnPVDischarge(String pnPVDischarge) {
            this.pnPVDischarge = pnPVDischarge;
        }

        public String getPnEpistomyTear() {
            return pnEpistomyTear;
        }

        public void setPnEpistomyTear(String pnEpistomyTear) {
            this.pnEpistomyTear = pnEpistomyTear;
        }

        public String getPnTemp() {
            return pnTemp;
        }

        public void setPnTemp(String pnTemp) {
            this.pnTemp = pnTemp;
        }

        public String getPnPulseRate() {
            return pnPulseRate;
        }

        public void setPnPulseRate(String pnPulseRate) {
            this.pnPulseRate = pnPulseRate;
        }

        public String getPnBPDiastolic() {
            return pnBPDiastolic;
        }

        public void setPnBPDiastolic(String pnBPDiastolic) {
            this.pnBPDiastolic = pnBPDiastolic;
        }

        public String getPnBPSystolic() {
            return pnBPSystolic;
        }

        public void setPnBPSystolic(String pnBPSystolic) {
            this.pnBPSystolic = pnBPSystolic;
        }

        public String getPnAnyComplaints() {
            return pnAnyComplaints;
        }

        public void setPnAnyComplaints(String pnAnyComplaints) {
            this.pnAnyComplaints = pnAnyComplaints;
        }

        public String getPnPlace() {
            return pnPlace;
        }

        public void setPnPlace(String pnPlace) {
            this.pnPlace = pnPlace;
        }

        public String getPnCareProvidedDate() {
            return pnCareProvidedDate;
        }

        public void setPnCareProvidedDate(String pnCareProvidedDate) {
            this.pnCareProvidedDate = pnCareProvidedDate;
        }

        public String getPnDueDate() {
            return pnDueDate;
        }

        public void setPnDueDate(String pnDueDate) {
            this.pnDueDate = pnDueDate;
        }

        public String getPnVisitNo() {
            return pnVisitNo;
        }

        public void setPnVisitNo(String pnVisitNo) {
            this.pnVisitNo = pnVisitNo;
        }

        public String getPicmeId() {
            return picmeId;
        }

        public void setPicmeId(String picmeId) {
            this.picmeId = picmeId;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getPnId() {
            return pnId;
        }

        public void setPnId(String pnId) {
            this.pnId = pnId;
        }
    }
}
