package world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document;

import android.location.Location;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.VariableDump;

public class XMLcreator {

    public static void createXML(String title, String note, Location location, String correctLocation, String src)
    {
            Document dom;
            Boolean isExisting = false;
            Element e = null;

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try
            {
                DocumentBuilder db = dbf.newDocumentBuilder();


                Element rootEle = null;
                dom = getDocument();
                if(dom == null)
                {
                    dom = db.newDocument();

                    // create the root element
                    rootEle = dom.createElement("cheatsheets");
                }
                else
                {
                    isExisting = true;
                    rootEle = (Element) dom.getElementsByTagName("cheatsheets").item(0);
                }

                // create data elements and place them under root
                Element subEle = dom.createElement("cheatsheet");
                rootEle.appendChild(subEle);

                e = dom.createElement("title");
                e.appendChild(dom.createTextNode(title));
                subEle.appendChild(e);

                e = dom.createElement("note");
                e.appendChild(dom.createTextNode(note));
                subEle.appendChild(e);

                e = dom.createElement("src");
                Log.i("Source", src);
                e.appendChild(dom.createTextNode(src));
                subEle.appendChild(e);

                Element locationSub = dom.createElement("location");
                subEle.appendChild(locationSub);

                e = dom.createElement("info");
                e.appendChild(dom.createTextNode(correctLocation));
                locationSub.appendChild(e);

                e = dom.createElement("longi");
                e.appendChild(dom.createTextNode(String.valueOf(location.getLongitude())));
                locationSub.appendChild(e);

                e = dom.createElement("lat");
                e.appendChild(dom.createTextNode(String.valueOf(location.getLatitude())));
                locationSub.appendChild(e);

                if(!isExisting)
                {
                    dom.appendChild(rootEle);
                }

                try {
                    Transformer tr = TransformerFactory.newInstance().newTransformer();
                    tr.setOutputProperty(OutputKeys.INDENT, "yes");
                    tr.setOutputProperty(OutputKeys.METHOD, "xml");
                    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

                    // send DOM to file
                    File file = new File(VariableDump.URL);
                    file.createNewFile();
                    tr.transform(new DOMSource(dom),new StreamResult(new FileOutputStream(file)));

                } catch (TransformerException te) {
                    System.out.println(te.getMessage());
                } catch (IOException ioe) {
                    System.out.println(ioe.getMessage());
                }
            } catch (ParserConfigurationException pce) {
                System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
            }

    }

    public static Document getDocument()
    {
        try
        {
            File fXmlFile = new File(VariableDump.URL);
            if(fXmlFile.exists())
            {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                return dBuilder.parse(fXmlFile);
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

}

