package com.gun0912.tedpicker;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ImageUploadInteractor {
    void uploadVisitReportsPhoto(String picmeId, ArrayList<Bitmap> bitmap);
}
