package com.unicef.thaimai.motherapp.model.requestmodel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class PHCSpinnerModel {

    private List<Block> block;
    private String message;
    private int status;

    public List<Block> getBlock() {
        return block;
    }

    public void setBlock(List<Block> block) {
        this.block = block;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class Block {
        private String phcStatus;
        private String f_longititute;
        private String f_latitute;
        private String f_nin_num;
        private String Dvn_Name;
        private String village;
        private String SVID;
        private String HSC;
        private String SID;
        private String f_facility_name;
        private String phid;
        private String f_sub_district_nam;
        private String bkid;
        private String talukaName;
        private String tCode;
        private String Dvn_cd;
        private String f_district_name;
        private String dCode;

        public String getPhcStatus() {
            return phcStatus;
        }

        public void setPhcStatus(String phcStatus) {
            this.phcStatus = phcStatus;
        }

        public String getF_longititute() {
            return f_longititute;
        }

        public void setF_longititute(String f_longititute) {
            this.f_longititute = f_longititute;
        }

        public String getF_latitute() {
            return f_latitute;
        }

        public void setF_latitute(String f_latitute) {
            this.f_latitute = f_latitute;
        }

        public String getF_nin_num() {
            return f_nin_num;
        }

        public void setF_nin_num(String f_nin_num) {
            this.f_nin_num = f_nin_num;
        }

        public String getDvn_Name() {
            return Dvn_Name;
        }

        public void setDvn_Name(String Dvn_Name) {
            this.Dvn_Name = Dvn_Name;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getSVID() {
            return SVID;
        }

        public void setSVID(String SVID) {
            this.SVID = SVID;
        }

        public String getHSC() {
            return HSC;
        }

        public void setHSC(String HSC) {
            this.HSC = HSC;
        }

        public String getSID() {
            return SID;
        }

        public void setSID(String SID) {
            this.SID = SID;
        }

        public String getF_facility_name() {
            return f_facility_name;
        }

        public void setF_facility_name(String f_facility_name) {
            this.f_facility_name = f_facility_name;
        }

        public String getPhid() {
            return phid;
        }

        public void setPhid(String phid) {
            this.phid = phid;
        }

        public String getF_sub_district_nam() {
            return f_sub_district_nam;
        }

        public void setF_sub_district_nam(String f_sub_district_nam) {
            this.f_sub_district_nam = f_sub_district_nam;
        }

        public String getBkid() {
            return bkid;
        }

        public void setBkid(String bkid) {
            this.bkid = bkid;
        }

        public String getTalukaName() {
            return talukaName;
        }

        public void setTalukaName(String talukaName) {
            this.talukaName = talukaName;
        }

        public String getTCode() {
            return tCode;
        }

        public void setTCode(String tCode) {
            this.tCode = tCode;
        }

        public String getDvn_cd() {
            return Dvn_cd;
        }

        public void setDvn_cd(String Dvn_cd) {
            this.Dvn_cd = Dvn_cd;
        }

        public String getF_district_name() {
            return f_district_name;
        }

        public void setF_district_name(String f_district_name) {
            this.f_district_name = f_district_name;
        }

        public String getDCode() {
            return dCode;
        }

        public void setDCode(String dCode) {
            this.dCode = dCode;
        }
    }
}
