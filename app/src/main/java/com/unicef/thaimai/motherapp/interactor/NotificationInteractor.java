package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by sathish on 3/29/2018.
 */

public interface NotificationInteractor {
    void getNotificationCount(String mid, String picmeId);
    void getNotificationList(String mid, String picmeId);
    void getNotificationDetails(String mid);
}
