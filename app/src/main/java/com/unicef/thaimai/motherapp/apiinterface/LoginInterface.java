package com.unicef.thaimai.motherapp.apiinterface;

/**
 * Created by Suthishan on 20/1/2018.
 */

public interface LoginInterface {

    void checkPickmeId(String pickmeid) ;
    void verifyOtp(String pickmeid, String otp) ;
}
