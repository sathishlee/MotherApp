package com.unicef.thaimai.motherapp.model.responsemodel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ProfileModel {

    private EditProfile EditProfile;
    private String message;
    private int status;

    public EditProfile getEditProfile() {
        return EditProfile;
    }

    public void setEditProfile(EditProfile EditProfile) {
        this.EditProfile = EditProfile;
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

    public static class EditProfile {
        private String mDistrict;
        private String mVillage;
        private String mDOB;
        private String mHusbandMobile;
        private String mMotherMobile;
        private String mHusbandName;
        private String mHeight;
        private String mWeight;
        private String mAge;
        private String mPicmeId;
        private String mName;

        public String getMDistrict() {
            return mDistrict;
        }

        public void setMDistrict(String mDistrict) {
            this.mDistrict = mDistrict;
        }

        public String getMVillage() {
            return mVillage;
        }

        public void setMVillage(String mVillage) {
            this.mVillage = mVillage;
        }

        public String getMDOB() {
            return mDOB;
        }

        public void setMDOB(String mDOB) {
            this.mDOB = mDOB;
        }

        public String getMHusbandMobile() {
            return mHusbandMobile;
        }

        public void setMHusbandMobile(String mHusbandMobile) {
            this.mHusbandMobile = mHusbandMobile;
        }

        public String getMMotherMobile() {
            return mMotherMobile;
        }

        public void setMMotherMobile(String mMotherMobile) {
            this.mMotherMobile = mMotherMobile;
        }

        public String getMHusbandName() {
            return mHusbandName;
        }

        public void setMHusbandName(String mHusbandName) {
            this.mHusbandName = mHusbandName;
        }

        public String getMHeight() {
            return mHeight;
        }

        public void setMHeight(String mHeight) {
            this.mHeight = mHeight;
        }

        public String getMWeight() {
            return mWeight;
        }

        public void setMWeight(String mWeight) {
            this.mWeight = mWeight;
        }

        public String getMAge() {
            return mAge;
        }

        public void setMAge(String mAge) {
            this.mAge = mAge;
        }

        public String getMPicmeId() {
            return mPicmeId;
        }

        public void setMPicmeId(String mPicmeId) {
            this.mPicmeId = mPicmeId;
        }

        public String getMName() {
            return mName;
        }

        public void setMName(String mName) {
            this.mName = mName;
        }
    }
}
