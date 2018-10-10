package com.unicef.thaimai.motherapp.Interface;


import android.graphics.Bitmap;

import com.unicef.thaimai.motherapp.adapter.ChildDevQuestionAdapter;

public interface CaptureImageInterface {
       void OpenCamera(ChildDevQuestionAdapter.ViewHolder holder, int postion);
       void NextQuestion(int postion);

       void SendQuestion(ChildDevQuestionAdapter.ViewHolder holder, int postion, Bitmap bitmap);

}
