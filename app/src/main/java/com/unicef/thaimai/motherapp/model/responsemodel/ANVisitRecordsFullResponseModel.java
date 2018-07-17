package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.ArrayList;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class ANVisitRecordsFullResponseModel {

    private String status;
    private String message;
    private String title;
    private ArrayList<ANVisitRecordsSingleResponseModel> visitRecordsSingleResponseModels;

    public ANVisitRecordsFullResponseModel(){

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



    public ArrayList<ANVisitRecordsSingleResponseModel> getVisitRecordsSingleResponseModels(){
        return visitRecordsSingleResponseModels;
    }
    public void setVisitRecordsSingleResponseModels(ArrayList<ANVisitRecordsSingleResponseModel> visitRecordsSingleResponseModels){
        this.visitRecordsSingleResponseModels = visitRecordsSingleResponseModels;
    }


        public String getTitle(){
            return title;
        }
        public void setTitle (String title){
            this.title = title;
        }

}
