package com.unicef.thaimai.motherapp.interactor;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface ProfileInteractor {

   void getMotherProfile(String mid,String picmeId);
   void sendMotherProfile(String mid, String picmeId, String number, String hus_number);
}
