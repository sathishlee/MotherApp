package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by sathish on 3/29/2018.
 */

public interface NotificationInteractor {
    void getNotificationCount(String mid);
    void getNotificationList(String picmeId, String mid);
    void getNotificationDetails(String mid);
}
