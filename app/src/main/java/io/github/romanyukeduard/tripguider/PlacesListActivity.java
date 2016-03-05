package io.github.romanyukeduard.tripguider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class PlacesListActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private String city;
    private String city_id;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView myLocation;
    private static double lat;
    private static double lng;

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.placeslist_layout);

        Intent intent = getIntent();

        city = intent.getStringExtra("title");
        city_id = intent.getStringExtra("city_id");

        myLocation = (TextView) findViewById(R.id.location);

        initToolbar();
        initDrawer();
        initFAB();
        getLocation();
    }

    private void getLocation() {

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }

    }

    private void displayLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocation = LocationServices.FusedLocationApi
                .getLastLocation(mGoogleApiClient);

        if (mLocation != null) {
            lat = mLocation.getLatitude();
            lng = mLocation.getLongitude();

            myLocation.setText(lat + ", " + lng);

        } else {
            myLocation.setText("Couldn't get the location. Make sure location is enabled on the device");

        }
    }


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }


    @Override
    public void onConnectionFailed(ConnectionResult result) {}

    @Override
    public void onConnected(Bundle arg0) {
        displayLocation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    public void onClickLocationSettings(View view) {
        startActivity(new Intent(
                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    private void initFAB() {
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Do something
            }
        });
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(city);
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        AccountHeader mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .withCompactStyle(true)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_places_all).withIcon(R.drawable.ic_map_marker).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_places_fav).withIcon(R.drawable.ic_fav).withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_places_cat),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_food).withIcon(R.drawable.ic_food).withIdentifier(3),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_sport).withIcon(R.drawable.ic_tennis).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_attr).withIcon(R.drawable.ic_camera).withIdentifier(5),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_hotel).withIcon(R.drawable.ic_seat_individual_suite).withIdentifier(6),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_shop).withIcon(R.drawable.ic_shopping).withIdentifier(7),
                        new SecondaryDrawerItem().withName(R.string.drawer_places_night).withIcon(R.drawable.ic_weather_night).withIdentifier(8)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 1){
                            mToolbar.setTitle(R.string.drawer_places_all);
                        }else if(drawerItem.getIdentifier() == 2){
                            mToolbar.setTitle(R.string.drawer_places_fav);
                        }else if(drawerItem.getIdentifier() == 3){
                            mToolbar.setTitle(R.string.drawer_places_food);
                        }else if(drawerItem.getIdentifier() == 4){
                            mToolbar.setTitle(R.string.drawer_places_sport);
                        }else if(drawerItem.getIdentifier() == 5){
                            mToolbar.setTitle(R.string.drawer_places_attr);
                        }else if(drawerItem.getIdentifier() == 6){
                            mToolbar.setTitle(R.string.drawer_places_hotel);
                        }else if(drawerItem.getIdentifier() == 7){
                            mToolbar.setTitle(R.string.drawer_places_shop);
                        }else if(drawerItem.getIdentifier() == 8){
                            mToolbar.setTitle(R.string.drawer_places_night);
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed(){
        if(mDrawer.isDrawerOpen()){
            mDrawer.closeDrawer();
        }
        else{
            super.onBackPressed();
        }
    }
}
