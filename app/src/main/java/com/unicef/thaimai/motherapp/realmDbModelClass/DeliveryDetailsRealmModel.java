package com.unicef.thaimai.motherapp.realmDbModelClass;

import io.realm.RealmObject;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class DeliveryDetailsRealmModel extends RealmObject {
        private String dHEPBDate;
        private String dOPVDate;
        private String dBCGDate;
        private String dSNCUOutcome;
        private String dSNCUDate;
        private String dAdmittedSNCU;
        private String dBirthHeight;
        private String dBirthWeight;
        private String dBirthDetails;
        private String dnewBorn;
        private String ddeleveryOutcomeMother;
        private String ddeleveryDetails;
        private String childGender;
        private String dInfantId;
        private String dBreastFeedingGiven;
        private String dplace;
        private String dtime;
        private String ddatetime;
        private String dpicmeId;
        private String mid;
        private String did;

        public String getDHEPBDate() {
            return dHEPBDate;
        }

        public void setDHEPBDate(String dHEPBDate) {
            this.dHEPBDate = dHEPBDate;
        }

        public String getDOPVDate() {
            return dOPVDate;
        }

        public void setDOPVDate(String dOPVDate) {
            this.dOPVDate = dOPVDate;
        }

        public String getDBCGDate() {
            return dBCGDate;
        }

        public void setDBCGDate(String dBCGDate) {
            this.dBCGDate = dBCGDate;
        }

        public String getDSNCUOutcome() {
            return dSNCUOutcome;
        }

        public void setDSNCUOutcome(String dSNCUOutcome) {
            this.dSNCUOutcome = dSNCUOutcome;
        }

        public String getDSNCUDate() {
            return dSNCUDate;
        }

        public void setDSNCUDate(String dSNCUDate) {
            this.dSNCUDate = dSNCUDate;
        }

        public String getDAdmittedSNCU() {
            return dAdmittedSNCU;
        }

        public void setDAdmittedSNCU(String dAdmittedSNCU) {
            this.dAdmittedSNCU = dAdmittedSNCU;
        }

        public String getDBirthHeight() {
            return dBirthHeight;
        }

        public void setDBirthHeight(String dBirthHeight) {
            this.dBirthHeight = dBirthHeight;
        }

        public String getDBirthWeight() {
            return dBirthWeight;
        }

        public void setDBirthWeight(String dBirthWeight) {
            this.dBirthWeight = dBirthWeight;
        }

        public String getDBirthDetails() {
            return dBirthDetails;
        }

        public void setDBirthDetails(String dBirthDetails) {
            this.dBirthDetails = dBirthDetails;
        }

        public String getDnewBorn() {
            return dnewBorn;
        }

        public void setDnewBorn(String dnewBorn) {
            this.dnewBorn = dnewBorn;
        }

        public String getDdeleveryOutcomeMother() {
            return ddeleveryOutcomeMother;
        }

        public void setDdeleveryOutcomeMother(String ddeleveryOutcomeMother) {
            this.ddeleveryOutcomeMother = ddeleveryOutcomeMother;
        }

        public String getDdeleveryDetails() {
            return ddeleveryDetails;
        }

        public void setDdeleveryDetails(String ddeleveryDetails) {
            this.ddeleveryDetails = ddeleveryDetails;
        }

        public String getChildGender() {
            return childGender;
        }

        public void setChildGender(String childGender) {
            this.childGender = childGender;
        }

        public String getDplace() {
            return dplace;
        }

        public void setDplace(String dplace) {
            this.dplace = dplace;
        }

        public String getDtime() {
            return dtime;
        }

        public void setDtime(String dtime) {
            this.dtime = dtime;
        }

        public String getDdatetime() {
            return ddatetime;
        }

        public void setDdatetime(String ddatetime) {
            this.ddatetime = ddatetime;
        }

        public String getDpicmeId() {
            return dpicmeId;
        }

        public void setDpicmeId(String dpicmeId) {
            this.dpicmeId = dpicmeId;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getDid() {
            return did;
        }

        public void setDid(String did) {
            this.did = did;
        }

    public String getdInfantId() {
        return dInfantId;
    }

    public void setdInfantId(String dInfantId) {
        this.dInfantId = dInfantId;
    }

    public String getdBreastFeedingGiven() {
        return dBreastFeedingGiven;
    }

    public void setdBreastFeedingGiven(String dBreastFeedingGiven) {
        this.dBreastFeedingGiven = dBreastFeedingGiven;
    }
}
