package io.github.romanyukeduard.tripguider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

public class MainActivity extends AppCompatActivity {

    Drawer mDrawer;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        initToolbar();
        initDrawer();
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
                        new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home),
                        new PrimaryDrawerItem().withName("Login").withIcon(R.drawable.ic_login).withSelectable(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName("Information").withIcon(R.drawable.ic_information).withSelectable(false)
                )
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
