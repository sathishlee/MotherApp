package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NotificationResponseModel {

    private List<Result> result;
    private String message;
    private int status;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
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

    public static class Result {
        private String picmeId;
        private String mPhoto;
        private String mid;
        private String mLMP;
        private String noteStartDateTime;
        private String message;

        public String getPicmeId() {
            return picmeId;
        }

        public void setPicmeId(String picmeId) {
            this.picmeId = picmeId;
        }

        public String getMPhoto() {
            return mPhoto;
        }

        public void setMPhoto(String mPhoto) {
            this.mPhoto = mPhoto;
        }

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getMLMP() {
            return mLMP;
        }

        public void setMLMP(String mLMP) {
            this.mLMP = mLMP;
        }

        public String getNoteStartDateTime() {
            return noteStartDateTime;
        }

        public void setNoteStartDateTime(String noteStartDateTime) {
            this.noteStartDateTime = noteStartDateTime;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
