package com.unicef.thaimai.motherapp.utility;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class Util {


    public static int dpToPx(Context c, int dp) {
        return (int) (dp * c.getResources().getSystem().getDisplayMetrics().density);
    }


    public static void toast(Fragment fragment, String message) {
        toast(fragment.getActivity(), message);
    }

    public static void toast(Context context, String message) {
        if (context == null)
            return;
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static void toast(Context context, String message, int time) {
        if (context == null)
            return;

        Toast.makeText(context, message, time).show();
    }


    public static int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }


    public static Bitmap rotate(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth() / 2,
                    (float) bitmap.getHeight() / 2);

            try {
                Bitmap converted = Bitmap.createBitmap(bitmap, 0, 0,
                        bitmap.getWidth(), bitmap.getHeight(), m, true);
                if (bitmap != converted) {
                    bitmap.recycle();
                    bitmap = converted;
                }
            } catch (OutOfMemoryError ex) {

            }
        }
        return bitmap;
    }

}
