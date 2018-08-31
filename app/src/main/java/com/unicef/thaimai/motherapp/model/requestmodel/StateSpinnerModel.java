package com.unicef.thaimai.motherapp.model.requestmodel;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class StateSpinnerModel {

    private String message;
    private int status;
    private List<Alldist> alldists;

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


    public static class Alldist {
        private String dst_name;
        private String dst_code;

        public String getDst_name() {
            return dst_name;
        }

        public void setDst_name(String dst_name) {
            this.dst_name = dst_name;
        }

        public String getDst_code() {
            return dst_code;
        }

        public void setDst_code(String dst_code) {
            this.dst_code = dst_code;
        }
    }
}
