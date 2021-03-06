package com.unicef.thaimai.motherapp;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.unicef.thaimai.motherapp.adapter.BaseRecyclerViewAdapter;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNAdapter_selectedPhoto extends BaseRecyclerViewAdapter<Uri, PNAdapter_selectedPhoto.SelectedPhotoHolder> {
    int closeImageRes;

    PNImageSelectedActivity pnImageSelectedActivity;

    public PNAdapter_selectedPhoto(PNImageSelectedActivity pnImageSelectedActivity, int closeImageRes) {
        super(pnImageSelectedActivity);
        this.pnImageSelectedActivity = pnImageSelectedActivity;
        this.closeImageRes = closeImageRes;

    }

    @Override
    public void onBindView(PNAdapter_selectedPhoto.SelectedPhotoHolder holder, int position) {

        Uri uri = getItem(position);
        Glide.with(pnImageSelectedActivity)
                .load(uri.toString())
                //   .override(selected_bottom_size, selected_bottom_size)
                .dontAnimate()
                .centerCrop()
                .error(R.drawable.no_image)
                .into(holder.selected_photo);
        holder.iv_close.setTag(uri);
        holder.iv_close.setVerticalScrollbarPosition(position);

    }

    @Override
    public SelectedPhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(getContext());
        View view = mInflater.inflate(R.layout.picker_list_item_selected_thumbnail, parent, false);
        return new SelectedPhotoHolder(view);
    }

    class SelectedPhotoHolder extends RecyclerView.ViewHolder {
        ImageView selected_photo;
        ImageView iv_close;

        public SelectedPhotoHolder(View itemView) {
            super(itemView);
            selected_photo = (ImageView) itemView.findViewById(R.id.selected_photo);
            iv_close = (ImageView) itemView.findViewById(R.id.iv_close);
            iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = (Uri) view.getTag();
                    pnImageSelectedActivity.removeImage(uri,view.getVerticalScrollbarPosition());
                }
            });

        }
    }
}
