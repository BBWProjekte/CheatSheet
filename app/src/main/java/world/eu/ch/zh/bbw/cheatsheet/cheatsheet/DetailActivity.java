package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by janes_000 on 23.06.2016.
 */
public class DetailActivity extends AppCompatActivity {

    private EditText title;
    private EditText note;
    private ImageView photo;
    File photoFile; /*= new  File("/storage/emulated/0/Gallery/Trump/takeme.jpg");*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(photoFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            photo = (ImageView) findViewById(R.id.photo);
            photo.setImageBitmap(myBitmap);
        }

    }
}
