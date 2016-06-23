package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import world.eu.ch.zh.bbw.cheatsheet.cheatsheet.document.XMLcreator;


public class SaveActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Serializable {
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = SaveActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private Location location;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String correctLocation;

    private EditText title;
    private EditText note;
    private String imagePath = null;
    private Button action_photo;
    private String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    private XMLcreator xmlc = new XMLcreator();


    //Foto erstellen
    //https://developer.android.com/training/camera/photobasics.html

    //Odner erstellen
    //https://developer.android.com/training/basics/data-storage/files.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        title = (EditText) findViewById(R.id.title);
        note = (EditText) findViewById(R.id.note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        action_photo = (Button) findViewById(R.id.action_photo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "No permissions!");
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            Log.i(TAG, "location is null");
        }
        else
        {
            try {
                handleNewLocation(location);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void onClickOrt(View v)
    {
        Log.d(TAG, "Click Ort!");
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("Latitude", location.getLatitude());
        intent.putExtra("Longitude", location.getLongitude());
        startActivityForResult(intent, 0);
    }

    private void handleNewLocation(Location location) throws IOException {
        Log.d(TAG + ": Latitude", String.valueOf(location.getLatitude()));
        Log.d(TAG + ": Longitude", String.valueOf(location.getLongitude()));

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
        if (addresses.size() > 0)
            Log.d(TAG + ": City", addresses.get(0).getLocality());
            TextView place = (TextView) findViewById(R.id.place);

            DateFormat format = new SimpleDateFormat("dd. MMM yyyy HH:mm");
            Date date = new Date(location.getTime());
            String formatted = format.format(date);

            correctLocation = addresses.get(0).getLocality() + ", " + formatted;
            place.setText(correctLocation);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(!action_photo.isEnabled())
        {
            Context context = getApplicationContext();
            CharSequence text = "Der Knopf ist gesperrt.";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else
        {
            try
            {
                Uri picPath = getImageDirectory();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, picPath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                action_photo.setEnabled(false);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private Uri getImageDirectory() throws IOException {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        path = path + "/CheatSheet/pictures/JPEG_" + timeStamp + ".jpg";
        imagePath = path;
        return Uri.fromFile(new File(path));
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public void saveNote(View view){

        String titleString = title.getText().toString();
        String noteString = note.getText().toString();

        if(titleString.matches("") || noteString.matches("")){
            Toast.makeText(this,"Es fehlen noch Eingaben. Alles überprüft?",Toast.LENGTH_SHORT).show();
        } else {
            xmlc.createXML(titleString, noteString, location, correctLocation, imagePath);
        }
    }
}
