package com.unicef.thaimai.motherapp.model;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VideoModel {

        private String videoUrl, visitNo, videoCategory;


    public String getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(String visitNo) {
        this.visitNo = visitNo;
    }

    public String getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(String videoCategory) {
        this.videoCategory = videoCategory;
    }



        public VideoModel(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }



    public VideoModel(String videoUrl, String visitNo, String videoCategory) {

        this.videoUrl = videoUrl;
        this.visitNo = visitNo;
        this.videoCategory = videoCategory;
    }

}
