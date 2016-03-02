package io.github.romanyukeduard.tripguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.github.romanyukeduard.tripguider.app.AppController;

public class MainActivity extends AppCompatActivity {

    private String url = "http://goodbadcity.com/trip/cities";//міста
    private String places = "http://goodbadcity.com/trip/places/1";//місця

    private TextView txtResponse;
    private String jsonResponse;

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtResponse = (TextView) findViewById(R.id.teeest);

        initToolbar();
        initDrawer();
        initFAB();
        checkInternet();

        /*ImageView img = (ImageView) findViewById(R.id.image);
        Picasso.with(this).load(url).into(img);*/

    }

    private void checkInternet() {

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        Boolean isInternetPresent = cd.isConnectingToInternet();
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .main_cl);
        if(!isInternetPresent){
            Snackbar disconnect = Snackbar
                    .make(coordinatorLayout, R.string.internet_snack_txt, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.internet_snack_btn, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkInternet();
                        }
                    });

            disconnect.show();
        }
        else{
            cityRequest();
        }

    }

    private void cityRequest() {
        JsonArrayRequest req = new JsonArrayRequest(places,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject places = (JSONObject) response
                                        .get(i);

                                String id = places.getString("id");
                                String city_id = places.getString("city_id");
                                String name = places.getString("name");
                                String coord_lat = places.getString("coord_lat");
                                String coord_lng = places.getString("coord_lng");
                                String category_id = places.getString("category_id");
                                String photo_url = places.getString("photo_url");

                                jsonResponse += "Id: " + id + "\n\n";
                                jsonResponse += "City id: " + city_id + "\n\n";
                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Latitude: " + coord_lat + "\n\n";
                                jsonResponse += "Longetude: " + coord_lng + "\n\n";
                                jsonResponse += "Category id: " + category_id + "\n\n";
                                jsonResponse += "Url: " + "http://goodbadcity.com" + photo_url + "\n\n";

                            }

                            txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(req);
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
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
    }

    private void initDrawer() {
        AccountHeader mHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .withAccountHeader(mHeader)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_main_home).withIcon(R.drawable.ic_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_main_login).withIcon(R.drawable.ic_login).withIdentifier(2).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_main_info).withIcon(R.drawable.ic_information).withIdentifier(3).withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if (drawerItem.getIdentifier() == 2) {
                            Intent intent = new Intent(MainActivity.this, PlacesListActivity.class);
                            startActivity(intent);
                        } else if (drawerItem.getIdentifier() == 3) {
                            Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                            startActivity(intent);
                            /*Toast toast = Toast.makeText(MainActivity.this, "info", Toast.LENGTH_SHORT);
                            toast.show();*/
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) {
            mDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
