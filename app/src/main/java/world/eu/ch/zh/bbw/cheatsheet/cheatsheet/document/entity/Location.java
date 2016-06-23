package world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by janes_000 on 23.06.2016.
 */
@XmlRootElement
public class Location implements Serializable {

    private double longi;
    private double lat;
    private String info;

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
