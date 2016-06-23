package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.ListView.LazyAdapter;
import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.ListView.XMLParser;

public class MainActivity extends AppCompatActivity {
    // All static variables
    public static final String URL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CheatSheet/cheatsheets.xml";
    // XML node keys
    public static final String KEY_NOTE = "cheatsheet"; // parent node
    public static final String KEY_TITLE = "title";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LONGITUDE = "longi";
    public static final String KEY_LATITUDE = "lat";
    public static final String KEY_LOCATIONANDDATE = "info";
    public static final String KEY_PICTURE = "src";

    ListView list;
    LazyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

        Document doc = null;
        try
        {
            File fXmlFile = new File(URL);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(fXmlFile);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        XMLParser parser = new XMLParser();

        if(doc != null)
        {
            NodeList nl = doc.getElementsByTagName(KEY_NOTE);
            // looping through all song nodes &lt;song&gt;
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key =&gt; value
                map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));

                NodeList nlLocation = doc.getElementsByTagName(KEY_LOCATION);
                Element eLoc = (Element)nlLocation.item(0);
                map.put(KEY_LATITUDE, parser.getValue(eLoc, KEY_LATITUDE));
                map.put(KEY_LONGITUDE, parser.getValue(eLoc, KEY_LONGITUDE));
                map.put(KEY_LOCATIONANDDATE, parser.getValue(eLoc, KEY_LOCATIONANDDATE));

                map.put(KEY_PICTURE, parser.getValue(e, KEY_PICTURE));

                // adding HashList to ArrayList
                songsList.add(map);
            }

            list=(ListView)findViewById(R.id.list);

            // Getting adapter by passing xml data ArrayList
            adapter=new LazyAdapter(this, songsList);
            list.setAdapter(adapter);

            // Click event for single list row
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Log.i("Click", "Aids");
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
