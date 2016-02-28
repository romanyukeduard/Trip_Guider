package io.github.romanyukeduard.tripguider;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.places_layout);

        initToolbar();
        initDrawer();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("All places");
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
                        new PrimaryDrawerItem().withName("All Places").withIcon(R.drawable.ic_map_marker).withIdentifier(1),
                        new PrimaryDrawerItem().withName("Favorites").withIcon(R.drawable.ic_fav).withIdentifier(2),
                        new SectionDrawerItem().withName("Categories:"),
                        new SecondaryDrawerItem().withName("Food").withIcon(R.drawable.ic_food).withIdentifier(3),
                        new SecondaryDrawerItem().withName("Sport").withIcon(R.drawable.ic_tennis).withIdentifier(4),
                        new SecondaryDrawerItem().withName("Attractions").withIcon(R.drawable.ic_camera).withIdentifier(5),
                        new SecondaryDrawerItem().withName("Hotels").withIcon(R.drawable.ic_seat_individual_suite).withIdentifier(6),
                        new SecondaryDrawerItem().withName("Shopping").withIcon(R.drawable.ic_shopping).withIdentifier(7),
                        new SecondaryDrawerItem().withName("Hightlife").withIcon(R.drawable.ic_weather_night).withIdentifier(8)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        if(drawerItem.getIdentifier() == 1){
                            mToolbar.setTitle("All places");
                        }else if(drawerItem.getIdentifier() == 2){
                            mToolbar.setTitle("Favorites");
                        }else if(drawerItem.getIdentifier() == 3){
                            mToolbar.setTitle("Food");
                        }else if(drawerItem.getIdentifier() == 4){
                            mToolbar.setTitle("Sport");
                        }else if(drawerItem.getIdentifier() == 5){
                            mToolbar.setTitle("Attractions");
                        }else if(drawerItem.getIdentifier() == 6){
                            mToolbar.setTitle("Hotels");
                        }else if(drawerItem.getIdentifier() == 7){
                            mToolbar.setTitle("Shopping");
                        }else if(drawerItem.getIdentifier() == 8){
                            mToolbar.setTitle("Hightlife");
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
