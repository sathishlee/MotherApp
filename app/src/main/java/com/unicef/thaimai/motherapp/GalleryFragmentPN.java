package com.unicef.thaimai.motherapp;

import android.*;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.gun0912.tedpicker.view.CustomSquareFrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class GalleryFragmentPN extends Fragment {
    public static ImageGalleryAdapter mGalleryAdapter;
    public static PNImageSelectedActivity mActivity;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    List<Uri> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.picker_fragment_gallery, container, false);
        GridView galleryGridView = (GridView) rootView.findViewById(R.id.gallery_grid);
        mActivity = ((PNImageSelectedActivity) getActivity());
        images =new ArrayList<>();
        boolean result = checkPermission();
        if (result) {
            images = getImagesFromGallary(getActivity());
        }
        mGalleryAdapter = new ImageGalleryAdapter(getActivity(), images);

        galleryGridView.setAdapter(mGalleryAdapter);
        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Uri mUri = mGalleryAdapter.getItem(i);

                if (!mActivity.containsImage(mUri)) {
                    mActivity.addImage(mUri);
                    Log.d("galary image path", String.valueOf(mUri));
                } else {
                    mActivity.removeImage(mUri,view.getVerticalScrollbarPosition());
                }
                mGalleryAdapter.notifyDataSetChanged();
            }
        });
        return rootView;
    }

    private boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(mActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mActivity);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions(mActivity, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void refreshGallery(Context context) {

        List<Uri> images = getImagesFromGallary(context);

        if (mGalleryAdapter == null) {

            mGalleryAdapter = new ImageGalleryAdapter(context, images);
        } else {
            mGalleryAdapter.clear();
            mGalleryAdapter.addAll(images);
            mGalleryAdapter.notifyDataSetChanged();

        }
    }

    public List<Uri> getImagesFromGallary(Context context) {

        List<Uri> images = new ArrayList<Uri>();


        Cursor imageCursor = null;
        try {
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.ImageColumns.ORIENTATION};
            final String orderBy = MediaStore.Images.Media.DATE_ADDED + " DESC";


            imageCursor = context.getApplicationContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);
            while (imageCursor.moveToNext()) {
                Uri uri = Uri.parse(imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                images.add(uri);


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageCursor != null && !imageCursor.isClosed()) {
                imageCursor.close();
            }
        }
        return images;
    }

    class ViewHolder {
        CustomSquareFrameLayout root;

        ImageView mThumbnail;
        Uri uri;

        public ViewHolder(View view) {
            root = (CustomSquareFrameLayout) view.findViewById(R.id.root);
            mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
        }
    }
    public class ImageGalleryAdapter extends ArrayAdapter<Uri> {

        Context context;


        public ImageGalleryAdapter(Context context, List<Uri> images) {
            super(context, 0, images);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.picker_grid_item_gallery_thumbnail, null);
                holder = new ViewHolder(convertView);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final Uri mUri = getItem(position);
            boolean isSelected = mActivity.containsImage(mUri);
            if(holder.root instanceof FrameLayout){
                ((FrameLayout)holder.root).setForeground(isSelected ? ResourcesCompat.getDrawable(getResources(),R.drawable.gallery_photo_selected,null) : null);
            }
            if (holder.uri == null || !holder.uri.equals(mUri)) {
                Glide.with(context)
                        .load(mUri.toString())
                        .thumbnail(0.1f)
                        //.fit()
                        .dontAnimate()
                        //   .override(holder.mThumbnail.getWidth(), holder.mThumbnail.getWidth())
                        //  .override(holder.root.getWidth(), holder.root.getWidth())
                        .centerCrop()
                        .placeholder(R.drawable.place_holder_gallery)
                        .error(R.drawable.no_image)

                        .into(holder.mThumbnail);
                holder.uri = mUri;
            }
            return convertView;
        }
    }

}
