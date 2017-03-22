package com.example.demo.testgradle.bean;

/**
 * Created by idea on 2017/3/2.
 */

public class ListViewBean {

    public  int customerId;
    public  String distance;
    public  String album;
    public  int fansCnt;
    public  int isCertified;
    public  String nickName;
    public  int online;
    public  int opCustomerId;
    public  String photo;
    public  String postTitle;
    public  int relationType;
    public  int sex;
    public  String videoFrame;

    @Override
    public String toString() {
        return "ListViewBean{" +
                "customerId=" + customerId +
                ", distance='" + distance + '\'' +
                ", album='" + album + '\'' +
                ", fansCnt=" + fansCnt +
                ", isCertified=" + isCertified +
                ", nickName='" + nickName + '\'' +
                ", online=" + online +
                ", opCustomerId=" + opCustomerId +
                ", photo='" + photo + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", relationType=" + relationType +
                ", sex=" + sex +
                ", videoFrame='" + videoFrame + '\'' +
                '}';
    }
}
