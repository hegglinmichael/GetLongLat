package com.example.michael.getlatlng;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //debugger tag
    private static final String TAG = "MainActivity";
    //holds the code for the request
    private final int REQUEST_FINE_ACCESS_CODE = 42562;

    //creates a locationManager
    protected LocationManager locationManager = null;
    //creates a location
    protected Location location = null;

    //creates textViews
    private TextView latView = null;
    private TextView lngView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this connects the textViews
        latView = (TextView) findViewById(R.id.latView);
        lngView = (TextView) findViewById(R.id.lngView);

        //calls the getLocation method
        //location = getLocation(location);
    }

    //gets the location of the user
    @TargetApi(Build.VERSION_CODES.M)
    public Location getLocation(Location location) {
        //accessing location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //tests to see if the application has access
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //request the permission of access_fine_location
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_FINE_ACCESS_CODE);
            Log.i(TAG, "Application does not have access according to the if statement");
            Log.i(TAG, "Location Manager Holds : "+locationManager);
            Log.i(TAG, "Location Object Holds : "+location);
        } else {
            //gets the location
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            //sets the textViews
            latView.setText("Latitude : " + getLat(location));
            lngView.setText("Longitude : " + getLng(location));
        }

        //returns a location
        return location;
    }

    //gets the access fine location permission answer from the user
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_ACCESS_CODE:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    //gets the location
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    //sets the textViews
                    latView.setText("Latitude : " + getLat(location));
                    lngView.setText("Longitude : " + getLng(location));
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "WRITE_CONTACTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //returns latitude
    public double getLat(Location location)
    {
        Log.i(TAG, "getLat called");
        return location.getLatitude();
    }

    //returns longitude
    public double getLng(Location location)
    {
        Log.i(TAG, "getLng called");
        return location.getLongitude();
    }
}
