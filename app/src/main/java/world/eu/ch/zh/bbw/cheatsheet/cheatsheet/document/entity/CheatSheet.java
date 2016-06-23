package world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by janes_000 on 23.06.2016.
 */

@XmlRootElement
public class CheatSheet implements Serializable {

    private String title;
    private String note;
    private Location loc;
    private String src;

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

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
