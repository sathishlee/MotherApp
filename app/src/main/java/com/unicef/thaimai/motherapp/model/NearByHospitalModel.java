package com.unicef.thaimai.motherapp.model;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class NearByHospitalModel {

    private String f_district_name, f_health_facility, f_facility_name, distance,f_nin_num;

    public String getF_district_name() {
        return f_district_name;
    }

    public void setF_district_name(String f_district_name) {
        this.f_district_name = f_district_name;
    }

    public String getF_health_facility() {
        return f_health_facility;
    }

    public void setF_health_facility(String f_health_facility) {
        this.f_health_facility = f_health_facility;
    }

    public String getF_facility_name() {
        return f_facility_name;
    }

    public void setF_facility_name(String f_facility_name) {
        this.f_facility_name = f_facility_name;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getF_nin_num() {
        return f_nin_num;
    }

    public void setF_nin_num(String f_nin_num) {
        this.f_nin_num = f_nin_num;
    }


    public NearByHospitalModel(String f_district_name, String f_health_facility, String f_facility_name, String distance, String f_nin_num) {
        this.f_district_name = f_district_name;
        this.f_health_facility = f_health_facility;
        this.f_facility_name = f_facility_name;
        this.distance = distance;
        this.f_nin_num = f_nin_num;
    }


}
