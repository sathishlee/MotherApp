/*
package com.unicef.thaimai.motherapp.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.utility.ImageConstants;

import javax.sql.DataSource;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

*/
/**
 * Created by Suthishan on 20/1/2018.
 *//*


public class FullImageViewActivity extends PopupWindow{
    View view;
    Context mContext;
    PhotoView photoView;
    ProgressBar loading;
    ViewGroup parent;

    private static FullImageViewActivity instance = null;

    public FullImageViewActivity(Context context, int layout, View view, String imageUrl, Bitmap bitmap){
        super(((LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_full_image_view, null), ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        if (Build.VERSION.SDK_INT >= 21) {
            setElevation(5.0f);
        }
        this.mContext = context;
        this.view = getContentView();
        ImageButton closeButton = (ImageButton) this.view.findViewById(R.id.ib_close);
        setOutsideTouchable(true);

        setFocusable(true);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                dismiss();
            }
        });

        photoView = (PhotoView) view.findViewById(R.id.image);
        loading = (ProgressBar) view.findViewById(R.id.loading);
        photoView.setMaximumScale(6);
        parent = (ViewGroup) photoView.getParent();

        if (bitmap != null) {
            loading.setVisibility(View.GONE);
            if (Build.VERSION.SDK_INT >= 16) {
                parent.setBackground(new BitmapDrawable(mContext.getResources(), ImageConstants.fastblur(Bitmap.createScaledBitmap(bitmap, 50, 50, true))));// ));
            } else {
                onPalette(Palette.from(bitmap).generate());

            }
            photoView.setImageBitmap(bitmap);
        }else {
            loading.setIndeterminate(true);
            loading.setVisibility(View.VISIBLE);
            Glide.with(context).asBitmap()
                    .load(imageUrl)
//                    .error(R.drawable.no_image)
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            loading.setIndeterminate(false);
                            loading.setBackgroundColor(Color.LTGRAY);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            if (Build.VERSION.SDK_INT >= 16) {
                                parent.setBackground(new BitmapDrawable(mContext.getResources(), ImageConstants.fastblur(Bitmap.createScaledBitmap(resource, 50, 50, true))));// ));
                            } else {
                                onPalette(Palette.from(resource).generate());

                            }
                            photoView.setImageBitmap(resource);

                            loading.setVisibility(View.GONE);
                            return false;
                        }
                    })



//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(photoView);

            showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }



    public void onPalette(Palette palette) {
        if (null != palette) {
            ViewGroup parent = (ViewGroup) photoView.getParent().getParent();
            parent.setBackgroundColor(palette.getDarkVibrantColor(Color.GRAY));
        }
    }

}
*/
