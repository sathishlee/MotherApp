package com.unicef.thaimai.motherapp.listener;

import com.unicef.thaimai.motherapp.model.VideoModel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface AdapterVideoActionListener extends AdapterActionListener {
    void onPlayVideoListener(int position, VideoModel.VideoList list);
}
