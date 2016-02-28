package io.github.romanyukeduard.tripguider;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class PlacesActivity extends AppCompatActivity{

    Drawer mDrawer;
    Toolbar mToolbar;
    FloatingActionButton mFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.places_layout);

        initToolbar();
        initDrawer();
        initFAB();
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
        mToolbar.setTitle(R.string.drawer_places_all);
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
