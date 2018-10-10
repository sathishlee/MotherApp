package com.unicef.thaimai.motherapp.model.ChildDevelopmentModdel;

import android.graphics.Bitmap;

public class ChildDevQuestionModel  {

    private String setquestion;
    private String setquestionNo;
    private String setImageUri;
    private String setanswer;
    private Bitmap setphoto;
    private String domain;

    public String getSetquestionNo() {
        return setquestionNo;
    }

    public void setSetquestionNo(String setquestionNo) {
        this.setquestionNo = setquestionNo;
    }


    public String getSetImageUri() {
        return setImageUri;
    }

    public void setSetImageUri(String setImageUri) {
        this.setImageUri = setImageUri;
    }

    public String getSetquestion() {
        return setquestion;
    }

    public void setSetquestion(String setquestion) {
        this.setquestion = setquestion;
    }

    public String getSetanswer() {
        return setanswer;
    }

    public void setSetanswer(String setanswer) {
        this.setanswer = setanswer;
    }

    public Bitmap getSetphoto() {
        return setphoto;
    }

    public void setSetphoto(Bitmap setphoto) {
        this.setphoto = setphoto;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


}
