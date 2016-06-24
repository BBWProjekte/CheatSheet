package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
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

import java.util.ArrayList;
import java.util.HashMap;

import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.ListView.LazyAdapter;
import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.ListView.XMLParser;
import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.XMLcreator;

public class MainActivity extends AppCompatActivity {

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
                Intent intent = new Intent(view.getContext(), SaveActivity.class);
                startActivityForResult(intent,0);
            }
        });

        ArrayList<HashMap<String, String>> notesList = new ArrayList<HashMap<String, String>>();

        Document doc = XMLcreator.getDocument();

        XMLParser parser = new XMLParser();

        if(doc != null)
        {
            NodeList nl = doc.getElementsByTagName(VariableDump.KEY_NOTE);
            // looping through all song nodes &lt;song&gt;
            for (int i = 0; i < nl.getLength(); i++) {
                // creating new HashMap
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);
                // adding each child node to HashMap key =&gt; value
                map.put(VariableDump.KEY_TITLE, parser.getValue(e, VariableDump.KEY_TITLE));

                NodeList nlLocation = doc.getElementsByTagName(VariableDump.KEY_LOCATION);
                Element eLoc = (Element)nlLocation.item(0);
                map.put(VariableDump.KEY_LATITUDE, parser.getValue(eLoc, VariableDump.KEY_LATITUDE));
                map.put(VariableDump.KEY_LONGITUDE, parser.getValue(eLoc, VariableDump.KEY_LONGITUDE));
                map.put(VariableDump.KEY_LOCATIONANDDATE, parser.getValue(eLoc, VariableDump.KEY_LOCATIONANDDATE));
                map.put(VariableDump.KEY_NOTE, parser.getValue(e, VariableDump.KEY_NOTE));

                map.put(VariableDump.KEY_PICTURE, parser.getValue(e, VariableDump.KEY_PICTURE));

                // adding HashList to ArrayList
                notesList.add(map);
            }

            list=(ListView)findViewById(R.id.list);

            // Getting adapter by passing xml data ArrayList
            adapter=new LazyAdapter(this, notesList);
            list.setAdapter(adapter);

            // Click event for single list row
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    Intent intent = new Intent(view.getContext(), DetailActivity.class);
                    int itemId = (int) parent.getSelectedItemId();
                    intent.putExtra("Data", ((LazyAdapter)parent.getAdapter()).getData(itemId));
                    startActivityForResult(intent,0);
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
