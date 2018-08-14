package com.unicef.thaimai.motherapp.realmDbModelClass;

import io.realm.RealmObject;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImageFragmentRealmModel extends RealmObject {

    private String health_pic;
    private String nearByVhnId;
    private String noteId;
    private String noteType;
    private String noteStatus;
    private String noteClosedBy;
    private String message;
    private String subject;
    private String noteCloseDateTime;
    private String noteStartDateTime;
    private String awwId;
    private String phcId;
    private String vhnId;
    private int clickHeremId;
    private String migratedmId;
    private String mid;
    private String picmeId;

    public String getHealth_pic() {
        return health_pic;
    }

    public void setHealth_pic(String health_pic) {
        this.health_pic = health_pic;
    }

    public String getNearByVhnId() {
        return nearByVhnId;
    }

    public void setNearByVhnId(String nearByVhnId) {
        this.nearByVhnId = nearByVhnId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNoteType() {
        return noteType;
    }

    public void setNoteType(String noteType) {
        this.noteType = noteType;
    }

    public String getNoteStatus() {
        return noteStatus;
    }

    public void setNoteStatus(String noteStatus) {
        this.noteStatus = noteStatus;
    }

    public String getNoteClosedBy() {
        return noteClosedBy;
    }

    public void setNoteClosedBy(String noteClosedBy) {
        this.noteClosedBy = noteClosedBy;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNoteCloseDateTime() {
        return noteCloseDateTime;
    }

    public void setNoteCloseDateTime(String noteCloseDateTime) {
        this.noteCloseDateTime = noteCloseDateTime;
    }

    public String getNoteStartDateTime() {
        return noteStartDateTime;
    }

    public void setNoteStartDateTime(String noteStartDateTime) {
        this.noteStartDateTime = noteStartDateTime;
    }

    public String getAwwId() {
        return awwId;
    }

    public void setAwwId(String awwId) {
        this.awwId = awwId;
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

    public int getClickHeremId() {
        return clickHeremId;
    }

    public void setClickHeremId(int clickHeremId) {
        this.clickHeremId = clickHeremId;
    }

    public String getMigratedmId() {
        return migratedmId;
    }

    public void setMigratedmId(String migratedmId) {
        this.migratedmId = migratedmId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getPicmeId() {
        return picmeId;
    }

    public void setPicmeId(String picmeId) {
        this.picmeId = picmeId;
    }
}
