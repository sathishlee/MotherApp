package com.unicef.thaimai.motherapp.model.responsemodel;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class LoginResponseModel {

    Boolean success;
    String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoginResponseModel(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
//    public static final String TAG_SUCCESS = "success";
//    public static final String TAG_MESSAGE = "message";

}


