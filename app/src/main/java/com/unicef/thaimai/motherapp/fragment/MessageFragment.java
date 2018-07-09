package com.unicef.thaimai.motherapp.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unicef.thaimai.motherapp.R;
import com.unicef.thaimai.motherapp.app.RealmController;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class MessageFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.message_fragment, container, false);
        initUI(view);
        onClickListner();
        return view;

    }

    private void onClickListner() {

    }

    private void initUI(View view) {

    }
}
