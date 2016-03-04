package io.github.romanyukeduard.tripguider.cities_recycler_view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.romanyukeduard.tripguider.R;

public class ListRowViewHolder extends RecyclerView.ViewHolder {

    protected ImageView image;
    protected TextView title;
    protected TextView id;
    protected CardView cities_item;

    public ListRowViewHolder(View view){
        super(view);
        this.image = (ImageView) view.findViewById(R.id.image);
        this.title = (TextView) view.findViewById(R.id.title);
        this.id = (TextView) view.findViewById(R.id.city_id);
        this.cities_item = (CardView) view.findViewById(R.id.cities_item);
        view.setClickable(true);
    }

}
