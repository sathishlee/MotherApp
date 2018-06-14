package com.unicef.thaimai.motherapp.interactor;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImageUploadInteractor {
    void uploadUserProfilePhoto(String picmeId, Bitmap bitmap);

    void uploadVisitReportsPhoto(String picmeId, String visitId, String mid, ArrayList<Bitmap> bitmap);

}
