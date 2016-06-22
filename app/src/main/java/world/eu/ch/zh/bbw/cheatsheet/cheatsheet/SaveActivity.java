package world.eu.ch.zh.bbw.cheatsheet.cheatsheet;

import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class SaveActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, Serializable {
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = SaveActivity.class.getSimpleName();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private Location location;


    //Foto erstellen
    //https://developer.android.com/training/camera/photobasics.html

    //Odner erstellen
    //https://developer.android.com/training/basics/data-storage/files.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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

            place.setText(addresses.get(0).getLocality() + ", " + formatted);
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
}
