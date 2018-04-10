package com.unicef.thaimai.motherapp.model;

import java.util.List;

/**
 * Created by Suthishan on 20/1/2018.
 */

public class VideoModel {

    private List<VideoList> videoList;
    private int status;
    private String LMPDate;
    private String message;

    public List<VideoList> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoList> videoList) {
        this.videoList = videoList;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLMPDate() {
        return LMPDate;
    }

    public void setLMPDate(String LMPDate) {
        this.LMPDate = LMPDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class VideoList {
        private String title;
        private String video;
        private String id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

//    private String videoUrl, visitNo, videoCategory;
//
//    public String getVisitNo() {
//        return visitNo;
//    }
//
//    public void setVisitNo(String visitNo) {
//        this.visitNo = visitNo;
//    }
//
//    public String getVideoCategory() {
//        return videoCategory;
//    }
//
//    public void setVideoCategory(String videoCategory) {
//        this.videoCategory = videoCategory;
//    }
//
//
//
//        public VideoModel(String videoUrl) {
//            this.videoUrl = videoUrl;
//        }
//
//        public String getVideoUrl() {
//            return videoUrl;
//        }
//
//        public void setVideoUrl(String videoUrl) {
//            this.videoUrl = videoUrl;
//        }
//
//
//
//    public VideoModel(String videoUrl, String visitNo, String videoCategory) {
//
//        this.videoUrl = videoUrl;
//        this.visitNo = visitNo;
//        this.videoCategory = videoCategory;
//    }


}
