package com.unicef.thaimai.motherapp.model.responsemodel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VisitRecordsFullResponseModel {




    private String status;
    private String message;
    private String title;
    private ArrayList<VisitRecordsSingleResponseModel> visitRecordsSingleResponseModels;

    public VisitRecordsFullResponseModel(){

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



    public ArrayList<VisitRecordsSingleResponseModel> getVisitRecordsSingleResponseModels(){
        return visitRecordsSingleResponseModels;
    }
    public void setVisitRecordsSingleResponseModels(ArrayList<VisitRecordsSingleResponseModel> visitRecordsSingleResponseModels){
        this.visitRecordsSingleResponseModels = visitRecordsSingleResponseModels;
    }


        public String getTitle(){
            return title;
        }
        public void setTitle (String title){
            this.title = title;
        }

}
