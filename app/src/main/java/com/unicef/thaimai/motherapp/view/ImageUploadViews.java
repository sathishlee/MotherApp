package com.unicef.thaimai.motherapp.view;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImageUploadViews {
    void showProgress();

    void hideProgress();

    void successUploadPhoto(String response);

    void errorUploadPhoto(String response);
}
