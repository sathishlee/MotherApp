package com.unicef.thaimai.motherapp.model.requestmodel;

import com.unicef.thaimai.motherapp.model.responsemodel.NearHospitalResponseModel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImmunizationEntryRequestModel {


    private String immDoseIDValue;
    private String immDoseId;
    private String immIpvStatus;
    private String immPentanvalentStatus;
    private String immRotaStatus;
    private String immOpvStatus;
    private String immCarePovidedDate;
    private String immDueDate;
    private String immDoseNumber;
    private String picmeId;
    private String mid;


    public void setImmDoseIDValue(String immDoseIDValue) {
        this.immDoseIDValue = immDoseIDValue;
    }
    public String getImmDoseIDValue(){
        return immDoseIDValue;
    }

    public String getImmDoseId() {
        return immDoseId;
    }

    public void setImmDoseId(String immDoseId) {
        this.immDoseId = immDoseId;
    }

    public String getImmIpvStatus() {
        return immIpvStatus;
    }

    public void setImmIpvStatus(String immIpvStatus) {
        this.immIpvStatus = immIpvStatus;
    }

    public String getImmPentanvalentStatus() {
        return immPentanvalentStatus;
    }

    public void setImmPentanvalentStatus(String immPentanvalentStatus) {
        this.immPentanvalentStatus = immPentanvalentStatus;
    }

    public String getImmRotaStatus() {
        return immRotaStatus;
    }

    public void setImmRotaStatus(String immRotaStatus) {
        this.immRotaStatus = immRotaStatus;
    }

    public String getImmOpvStatus() {
        return immOpvStatus;
    }

    public void setImmOpvStatus(String immOpvStatus) {
        this.immOpvStatus = immOpvStatus;
    }

    public String getImmCarePovidedDate() {
        return immCarePovidedDate;
    }

    public void setImmCarePovidedDate(String immCarePovidedDate) {
        this.immCarePovidedDate = immCarePovidedDate;
    }

    public String getImmDueDate() {
        return immDueDate;
    }

    public void setImmDueDate(String immDueDate) {
        this.immDueDate = immDueDate;
    }

    public String getImmDoseNumber() {
        return immDoseNumber;
    }

    public void setImmDoseNumber(String immDoseNumber) {
        this.immDoseNumber = immDoseNumber;
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
}

