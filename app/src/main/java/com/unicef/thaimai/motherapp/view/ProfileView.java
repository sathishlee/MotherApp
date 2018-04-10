package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ProfileView {
    void showProgress();

    void hideProgress();

    void successViewProfile(String response);

    void errorViewProfile(String response);

}
