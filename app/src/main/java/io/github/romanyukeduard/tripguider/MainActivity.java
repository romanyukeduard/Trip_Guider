package io.github.romanyukeduard.tripguider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.github.romanyukeduard.tripguider.cities_recycler_view.ListItems;
import io.github.romanyukeduard.tripguider.cities_recycler_view.MyRecyclerAdapter;

public class MainActivity extends AppCompatActivity {

    //"http://goodbadcity.com/trip/cities";//міста
    //"http://goodbadcity.com/trip/places/1";//місця

    private static final String MY_SETTINGS = "my_settings";

    private Drawer mDrawer;
    private Toolbar mToolbar;
    private FloatingActionButton mFAB;

    private String url = "http://romanyukeduard.github.io/cities.json";
    private List<ListItems> mListItemsList = new ArrayList<ListItems>();
    private MyRecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private int counter = 0;
    private String count;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {

            new MaterialDialog.Builder(this)
                    .title(R.string.location_msg_title)
                    .content(R.string.location_msg_content)
                    .positiveText(R.string.location_msg_positive)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(MaterialDialog dialog, DialogAction which) {
                            startActivity(new Intent(
                                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .negativeText(R.string.location_msg_negative)
                    .show();

            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply();

        }

        initToolbar();
        initDrawer();
        initFAB();
        checkInternet();

    }

    private void ifInternetEnable() {

        mRecyclerView = (RecyclerView) findViewById(R.id.cities_rw);
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration
                        .Builder(this)
                        .color(Color.WHITE)
                        .size(8)
                        .build()
        );

        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        cityRequest(url);

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
        else {
            ifInternetEnable();
        }

    }

    private void cityRequest(String url) {

        counter = 0;

        mAdapter = new MyRecyclerAdapter(MainActivity.this, mListItemsList);
        mRecyclerView.setAdapter(mAdapter);

        RequestQueue queue = Volley.newRequestQueue(this);

        mAdapter.clearAdapter();

        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject cities = (JSONObject) response
                                        .get(i);

                                ListItems item = new ListItems();

                                item.setTitle(cities.getString("name"));
                                item.setImage(cities.getString("photo_url"));
                                item.setCityId(cities.getString("id"));

                                mListItemsList.add(item);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        mAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(req);
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
