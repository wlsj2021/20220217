package com.wlsj2021.postwang.entity;

public class SortBean {
    private String num;
    private int img;
    private String title;
    private String flow;

    public SortBean(String num, int img, String title, String flow) {
        this.num = num;
        this.img = img;
        this.title = title;
        this.flow = flow;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFlow() {
        return flow;
    }

    public void setFlow(String flow) {
        this.flow = flow;
    }

    @Override
    public String toString() {
        return "SortBean{" +
                "num='" + num + '\'' +
                ", img=" + img +
                ", title='" + title + '\'' +
                ", flow='" + flow + '\'' +
                '}';
    }
}
