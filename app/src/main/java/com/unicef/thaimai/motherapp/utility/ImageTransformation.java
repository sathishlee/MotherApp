package com.unicef.thaimai.motherapp.utility;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Transformation;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ImageTransformation {
    public static Transformation getTransformation(final ImageView imageView) {
        return new Transformation() {

            @Override
            public Bitmap transform(Bitmap source) {
                int targetWidth = imageView.getWidth();

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
                    source.recycle();
                }
                return result;
            }

            @Override
            public String key() {
                return "transformation" + " desiredWidth";
            }
        };
    }

}
