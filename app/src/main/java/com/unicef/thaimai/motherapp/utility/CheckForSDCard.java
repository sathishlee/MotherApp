package com.unicef.thaimai.motherapp.utility;

import android.os.Environment;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class CheckForSDCard {

    public boolean isSDCardPresent() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}
