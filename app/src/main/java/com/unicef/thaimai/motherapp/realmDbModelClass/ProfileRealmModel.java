package com.unicef.thaimai.motherapp.realmDbModelClass;

import io.realm.RealmObject;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ProfileRealmModel extends RealmObject {

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
    private String mAddress;
    private String mPhoto;
    private String blockname;

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

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getBlockname() {
        return blockname;
    }

    public void setBlockname(String blockname) {
        this.blockname = blockname;
    }
}
