package com.unicef.thaimai.motherapp.realmDbModelClass;

import io.realm.RealmObject;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImmunizationRealmModel extends RealmObject {

    private String immIpvStatus;
    private String immRotaStatus;
    private String immPentanvalentStatus;
    private String immOpvStatus;
    private String immCarePovidedDate;
    private String immDueDate;
    private String immDoseIDValue;
    private String immDoseId;
    private String immDoseNumber;
    private String picmeId;
    private String mid;
    private String immId;

    public String getImmIpvStatus() {
        return immIpvStatus;
    }

    public void setImmIpvStatus(String immIpvStatus) {
        this.immIpvStatus = immIpvStatus;
    }

    public String getImmRotaStatus() {
        return immRotaStatus;
    }

    public void setImmRotaStatus(String immRotaStatus) {
        this.immRotaStatus = immRotaStatus;
    }

    public String getImmPentanvalentStatus() {
        return immPentanvalentStatus;
    }

    public void setImmPentanvalentStatus(String immPentanvalentStatus) {
        this.immPentanvalentStatus = immPentanvalentStatus;
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

    public String getImmDoseIDValue() {
        return immDoseIDValue;
    }

    public void setImmDoseIDValue(String immDoseIDValue) {
        this.immDoseIDValue = immDoseIDValue;
    }

    public String getImmDoseId() {
        return immDoseId;
    }

    public void setImmDoseId(String immDoseId) {
        this.immDoseId = immDoseId;
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

    public String getImmId() {
        return immId;
    }

    public void setImmId(String immId) {
        this.immId = immId;
    }
}
