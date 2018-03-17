package com.unicef.thaimai.motherapp.model.requestmodel;

/**
 * Created by sathish on 3/17/2018.
 */

public class AddReferalRequestModel {
    /*picmeId:1000000000001
mid:1
vhnId:1
phcId:1
rReferalDate:17-12-2018
rReferalTime:10:00:00
rFacilityReferring:PHC
rFacilityReferredTo:GH
rDiagonosis:Heavy bleeding
rReferalReason:doctor not available
rReferredBy:SBC
*/
    private String picmeId,mid,vhnId,phcId,rReferalDate,rReferalTime,rFacilityReferring,rFacilityReferredTo,rDiagonosis, rReferalReason,rReferredBy;

    /*public AddReferalRequestModel(String picmeId, String mid, String vhnId, String phcId, String rReferalDate, String rReferalTime, String rFacilityReferring, String rFacilityReferredTo, String rDiagonosis, String rReferalReason, String rReferredBy) {
        this.picmeId = picmeId;
        this.mid = mid;
        this.vhnId = vhnId;
        this.phcId = phcId;
        this.rReferalDate = rReferalDate;
        this.rReferalTime = rReferalTime;
        this.rFacilityReferring = rFacilityReferring;
        this.rFacilityReferredTo = rFacilityReferredTo;
        this.rDiagonosis = rDiagonosis;
        this.rReferalReason = rReferalReason;
        this.rReferredBy = rReferredBy;
    }*/

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

    public String getVhnId() {
        return vhnId;
    }

    public void setVhnId(String vhnId) {
        this.vhnId = vhnId;
    }

    public String getPhcId() {
        return phcId;
    }

    public void setPhcId(String phcId) {
        this.phcId = phcId;
    }

    public String getrReferalDate() {
        return rReferalDate;
    }

    public void setrReferalDate(String rReferalDate) {
        this.rReferalDate = rReferalDate;
    }

    public String getrReferalTime() {
        return rReferalTime;
    }

    public void setrReferalTime(String rReferalTime) {
        this.rReferalTime = rReferalTime;
    }

    public String getrFacilityReferring() {
        return rFacilityReferring;
    }

    public void setrFacilityReferring(String rFacilityReferring) {
        this.rFacilityReferring = rFacilityReferring;
    }

    public String getrFacilityReferredTo() {
        return rFacilityReferredTo;
    }

    public void setrFacilityReferredTo(String rFacilityReferredTo) {
        this.rFacilityReferredTo = rFacilityReferredTo;
    }

    public String getrDiagonosis() {
        return rDiagonosis;
    }

    public void setrDiagonosis(String rDiagonosis) {
        this.rDiagonosis = rDiagonosis;
    }

    public String getrReferalReason() {
        return rReferalReason;
    }

    public void setrReferalReason(String rReferalReason) {
        this.rReferalReason = rReferalReason;
    }

    public String getrReferredBy() {
        return rReferredBy;
    }

    public void setrReferredBy(String rReferredBy) {
        this.rReferredBy = rReferredBy;
    }

}
