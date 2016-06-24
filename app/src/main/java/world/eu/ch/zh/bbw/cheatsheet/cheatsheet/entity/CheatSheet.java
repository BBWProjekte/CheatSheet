package world.eu.ch.zh.bbw.cheatsheet.cheatsheet.entity;

import java.io.Serializable;

/**
 * Created by Janes Thomas on 24.06.2016.
 */

public class CheatSheet implements Serializable{

    private String title;
    private String note;
    private String src;
    private double longi;
    private double lat;
    private String info;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public double getLongi() {
        return longi;
    }

    public void setLongi(double longi) {
        this.longi = longi;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
