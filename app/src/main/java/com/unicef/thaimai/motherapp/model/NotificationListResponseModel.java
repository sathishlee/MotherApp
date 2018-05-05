package com.unicef.thaimai.motherapp.model;

import java.util.List;

/**
 * Created by sathish on 3/29/2018.
 */

public class NotificationListResponseModel {

    private List<NotificationList> notificationList;
    private String message;
    private int status;

    public List<NotificationList> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationList> notificationList) {
        this.notificationList = notificationList;
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

    public static class NotificationList {
        private String noteStartDateTime;
        private String subject;
        private String noteId;
        private String message;

        public String getNoteStartDateTime() {
            return noteStartDateTime;
        }

        public void setNoteStartDateTime(String noteStartDateTime) {
            this.noteStartDateTime = noteStartDateTime;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getNoteId() {
            return noteId;
        }

        public void setNoteId(String noteId) {
            this.noteId = noteId;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
