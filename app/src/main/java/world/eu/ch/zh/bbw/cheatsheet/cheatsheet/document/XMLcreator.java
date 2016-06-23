package world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document;

import android.location.Location;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.MainActivity;
import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.entity.CheatSheet;

/**
 * Created by janes_000 on 23.06.2016.
 */
public class XMLcreator {

    public void createXML(String title, String note, Location location, String correctLocation, String src) {

        CheatSheet cs = new CheatSheet();
        world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.entity.Location loc = new world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.entity.Location();

        loc.setLongi(location.getLongitude());
        loc.setLat(location.getLatitude());
        loc.setInfo(correctLocation);

        cs.setTitle(title);
        cs.setNote(note);
        cs.setLoc(loc);
        cs.setSrc(src);

        try {

            File file = new File(MainActivity.URL);
            JAXBContext jaxbContext = JAXBContext.newInstance(CheatSheet.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(cs, file);
            jaxbMarshaller.marshal(cs, System.out);

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

}

