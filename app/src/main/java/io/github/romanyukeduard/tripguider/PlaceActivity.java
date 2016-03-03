package io.github.romanyukeduard.tripguider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class PlaceActivity extends AppCompatActivity{

    String url = "http://images4.fanpop.com/image/photos/21200000/Lutsk-castle-ukraine-21240842-620-465.jpg";
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_layout);

        initFabMenu();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Place name");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView col = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this).load(url).into(col);

        testCard("Pizza", "Street", "10 km", "099 1488 228");

    }

    private void initFabMenu() {

        final FloatingActionButton fav = (FloatingActionButton) findViewById(R.id.fab_fav);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(PlaceActivity.this, "fav", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        final FloatingActionButton plan = (FloatingActionButton) findViewById(R.id.fab_plan);
        plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast1 = Toast.makeText(PlaceActivity.this, "plan", Toast.LENGTH_SHORT);
                toast1.show();
            }
        });

    }


    public void testCard(final String intro, final String address, final String distance, final String number){
        LinearLayout cIntro = (LinearLayout) findViewById(R.id.intro);
        final TextView txtIntro = (TextView) findViewById(R.id.txt_intro);
        cIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtIntro.setText(intro);
            }
        });

        LinearLayout cAddress = (LinearLayout) findViewById(R.id.address);
        final TextView txtAddress = (TextView) findViewById(R.id.txt_address);
        cAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAddress.setText(address);
            }
        });

        LinearLayout cDistance = (LinearLayout) findViewById(R.id.distance);
        final TextView txtDistance = (TextView) findViewById(R.id.txt_distance);
        cDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDistance.setText(distance);
            }
        });

        LinearLayout cNumber = (LinearLayout) findViewById(R.id.number);
        final TextView txtNumber = (TextView) findViewById(R.id.txt_number);
        cNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtNumber.setText(number);
            }
        });

    }
}
