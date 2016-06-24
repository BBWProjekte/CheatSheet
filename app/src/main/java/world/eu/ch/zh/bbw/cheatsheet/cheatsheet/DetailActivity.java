package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.HashMap;

/**
 * Created by janes_000 on 23.06.2016.
 */
public class DetailActivity extends AppCompatActivity {

    private EditText title;
    private EditText note;
    private ImageView photo;
    File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        HashMap<String, String> data = (HashMap<String, String>)getIntent().getSerializableExtra("Data");

        photoFile = new File(data.get(VariableDump.KEY_PICTURE));

        title = (EditText) findViewById(R.id.title);
        title.setText(data.get(VariableDump.KEY_TITLE));

        note = (EditText) findViewById(R.id.note);
        note.setText(data.get(VariableDump.KEY_TEXT));

        photo = (ImageView) findViewById(R.id.photo);

        try {
            if (photoFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                photo.setImageBitmap(myBitmap);
            } else {
                Toast.makeText(this, getResources().getString(R.string.photo_not_found), Toast.LENGTH_LONG).show();
                photo.setImageDrawable(getDrawable(R.drawable.placeholder));
            }
        } catch (Exception e) {
            Toast.makeText(this, getResources().getString(R.string.error_fatal), Toast.LENGTH_LONG).show();
            photo.setImageDrawable(getDrawable(R.drawable.placeholder));
        }

    }
}
