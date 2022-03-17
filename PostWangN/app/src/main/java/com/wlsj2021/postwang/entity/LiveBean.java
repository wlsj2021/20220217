package com.wlsj2021.postwang.entity;

public class LiveBean {
    private int imgId;
    private String title;

    public LiveBean(int imgId, String title) {
        this.imgId = imgId;
        this.title = title;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "LiveBean{" +
                "imgId=" + imgId +
                ", title='" + title + '\'' +
                '}';
    }
}
