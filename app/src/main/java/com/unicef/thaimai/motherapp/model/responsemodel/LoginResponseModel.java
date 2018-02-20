package com.unicef.thaimai.motherapp.model.responsemodel;


/**
 * Created by Suthishan on 20/1/2018.
 */

public class LoginResponseModel {

    public boolean status;
    public String message;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


