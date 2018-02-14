package com.unicef.thaimai.motherapp.model;

/**
 * Created by sathish on 2/14/2018.
 */

public class VisitModel {
    private String date;
    private String visit_type;
    private String facility;
    private String Complaints;


    public VisitModel(String date, String visit_type, String facility, String complaints) {
        this.date = date;
        this.visit_type = visit_type;
        this.facility = facility;
        Complaints = complaints;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public void setVisit_type(String visit_type) {
        this.visit_type = visit_type;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getComplaints() {
        return Complaints;
    }

    public void setComplaints(String complaints) {
        Complaints = complaints;
    }




}
