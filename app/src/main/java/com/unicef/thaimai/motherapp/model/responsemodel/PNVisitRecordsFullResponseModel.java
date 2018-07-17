package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PNVisitRecordsFullResponseModel {

    private String status;
    private String message;
    private String title;
    private ArrayList<PNVisitRecordsSingleResponseModel> visitRecordsSingleResponseModels;

    public PNVisitRecordsFullResponseModel(){

    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }



    public ArrayList<PNVisitRecordsSingleResponseModel> getVisitRecordsSingleResponseModels(){
        return visitRecordsSingleResponseModels;
    }
    public void setVisitRecordsSingleResponseModels(ArrayList<PNVisitRecordsSingleResponseModel> visitRecordsSingleResponseModels){
        this.visitRecordsSingleResponseModels = visitRecordsSingleResponseModels;
    }


    public String getTitle(){
        return title;
    }
    public void setTitle (String title){
        this.title = title;
    }

}
