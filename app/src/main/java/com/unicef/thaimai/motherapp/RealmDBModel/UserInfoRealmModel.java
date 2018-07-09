package com.unicef.thaimai.motherapp.RealmDBModel;

import io.realm.RealmObject;


public class UserInfoRealmModel extends RealmObject {

//    public PNnextVisit PNnextVisit;
    public String deleveryStatus;
    public String ANnextVisit;
    public int mGesWeek;
    public int mAge;
    public String dBirthWeight;
    public String ddeleveryDetails;
    public String deleveryDate;
    public String mPhoto;
    public String mDOB;
    public String motherStatus;
    public String phcMobile;
    public String phcName;
    public String awwMobile;
    public String awwName;
    public String vhnMobile;
    public String vhnName;
    public String mHusbandMobile;
    public String mHusbandName;
    public String mWeight;
    public String mRiskStatus;
    public String mEDD;
    public String mLMP;
    public String picmeId;



    public String childWeight;
    public String deleveryType;
    public String meaturityWeeks;
//    public String deleveryDate;
    public String pnVisit;

    /*public PNnextVisit getPNnextVisit() {
        return PNnextVisit;
    }

    public void setPNnextVisit(PNnextVisit PNnextVisit) {
        this.PNnextVisit = PNnextVisit;
    }*/

    public String getDeleveryStatus() {
        return deleveryStatus;
    }

    public void setDeleveryStatus(String deleveryStatus) {
        this.deleveryStatus = deleveryStatus;
    }

    public String getANnextVisit() {
        return ANnextVisit;
    }

    public void setANnextVisit(String ANnextVisit) {
        this.ANnextVisit = ANnextVisit;
    }

    public int getmGesWeek() {
        return mGesWeek;
    }

    public void setmGesWeek(int mGesWeek) {
        this.mGesWeek = mGesWeek;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public String getdBirthWeight() {
        return dBirthWeight;
    }

    public void setdBirthWeight(String dBirthWeight) {
        this.dBirthWeight = dBirthWeight;
    }

    public String getDdeleveryDetails() {
        return ddeleveryDetails;
    }

    public void setDdeleveryDetails(String ddeleveryDetails) {
        this.ddeleveryDetails = ddeleveryDetails;
    }

    public String getDeleveryDate() {
        return deleveryDate;
    }

    public void setDeleveryDate(String deleveryDate) {
        this.deleveryDate = deleveryDate;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmDOB() {
        return mDOB;
    }

    public void setmDOB(String mDOB) {
        this.mDOB = mDOB;
    }

    public String getMotherStatus() {
        return motherStatus;
    }

    public void setMotherStatus(String motherStatus) {
        this.motherStatus = motherStatus;
    }

    public String getPhcMobile() {
        return phcMobile;
    }

    public void setPhcMobile(String phcMobile) {
        this.phcMobile = phcMobile;
    }

    public String getPhcName() {
        return phcName;
    }

    public void setPhcName(String phcName) {
        this.phcName = phcName;
    }

    public String getAwwMobile() {
        return awwMobile;
    }

    public void setAwwMobile(String awwMobile) {
        this.awwMobile = awwMobile;
    }

    public String getAwwName() {
        return awwName;
    }

    public void setAwwName(String awwName) {
        this.awwName = awwName;
    }

    public String getVhnMobile() {
        return vhnMobile;
    }

    public void setVhnMobile(String vhnMobile) {
        this.vhnMobile = vhnMobile;
    }

    public String getVhnName() {
        return vhnName;
    }

    public void setVhnName(String vhnName) {
        this.vhnName = vhnName;
    }

    public String getmHusbandMobile() {
        return mHusbandMobile;
    }

    public void setmHusbandMobile(String mHusbandMobile) {
        this.mHusbandMobile = mHusbandMobile;
    }

    public String getmHusbandName() {
        return mHusbandName;
    }

    public void setmHusbandName(String mHusbandName) {
        this.mHusbandName = mHusbandName;
    }

    public String getmWeight() {
        return mWeight;
    }

    public void setmWeight(String mWeight) {
        this.mWeight = mWeight;
    }

    public String getmRiskStatus() {
        return mRiskStatus;
    }

    public void setmRiskStatus(String mRiskStatus) {
        this.mRiskStatus = mRiskStatus;
    }

    public String getmEDD() {
        return mEDD;
    }

    public void setmEDD(String mEDD) {
        this.mEDD = mEDD;
    }

    public String getmLMP() {
        return mLMP;
    }

    public void setmLMP(String mLMP) {
        this.mLMP = mLMP;
    }

    public String getPicmeId() {
        return picmeId;
    }

    public void setPicmeId(String picmeId) {
        this.picmeId = picmeId;
    }
    public String getChildWeight() {
        return childWeight;
    }

    public void setChildWeight(String childWeight) {
        this.childWeight = childWeight;
    }

    public String getDeleveryType() {
        return deleveryType;
    }

    public void setDeleveryType(String deleveryType) {
        this.deleveryType = deleveryType;
    }

    public String getMeaturityWeeks() {
        return meaturityWeeks;
    }

    public void setMeaturityWeeks(String meaturityWeeks) {
        this.meaturityWeeks = meaturityWeeks;
    }

  /*  public String getDeleveryDate() {
        return deleveryDate;
    }

    public void setDeleveryDate(String deleveryDate) {
        this.deleveryDate = deleveryDate;
    }*/

    public String getPnVisit() {
        return pnVisit;
    }

    public void setPnVisit(String pnVisit) {
        this.pnVisit = pnVisit;
    }
    /*public static class PNnextVisit {
        public String childWeight;
        public String deleveryType;
        public String meaturityWeeks;
        public String deleveryDate;
        public String pnVisit;

        public String getChildWeight() {
            return childWeight;
        }

        public void setChildWeight(String childWeight) {
            this.childWeight = childWeight;
        }

        public String getDeleveryType() {
            return deleveryType;
        }

        public void setDeleveryType(String deleveryType) {
            this.deleveryType = deleveryType;
        }

        public String getMeaturityWeeks() {
            return meaturityWeeks;
        }

        public void setMeaturityWeeks(String meaturityWeeks) {
            this.meaturityWeeks = meaturityWeeks;
        }

        public String getDeleveryDate() {
            return deleveryDate;
        }

        public void setDeleveryDate(String deleveryDate) {
            this.deleveryDate = deleveryDate;
        }

        public String getPnVisit() {
            return pnVisit;
        }

        public void setPnVisit(String pnVisit) {
            this.pnVisit = pnVisit;
        }
    }*/
}
