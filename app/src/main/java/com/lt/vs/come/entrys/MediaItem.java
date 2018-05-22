package com.lt.vs.come.entrys;

import java.io.Serializable;

/**
 * Created by 金色VS时空 on 2017/9/26.
 */

public class MediaItem implements Serializable{
    private String name;
    private int picSouce;
    private String path;
    private String author;
    private long totaltime;

    public long getTotaltime() {
        return totaltime;
    }

    public void setTotaltime(long totaltime) {
        this.totaltime = totaltime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPicSouce() {
        return picSouce;
    }

    public void setPicSouce(int picSouce) {
        this.picSouce = picSouce;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "MediaItem{" +
                "name='" + name + '\'' +
                ", picSouce=" + picSouce +
                ", path='" + path + '\'' +
                ", author='" + author + '\'' +
                ", totaltime=" + totaltime +
                '}';
    }
}
