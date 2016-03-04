package io.github.romanyukeduard.tripguider.cities_recycler_view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import io.github.romanyukeduard.tripguider.PlacesListActivity;
import io.github.romanyukeduard.tripguider.R;

public class MyRecyclerAdapter extends RecyclerView.Adapter<ListRowViewHolder> {

    private List<ListItems> mListItemses;
    private Context mContext;
    private int focusedItem = 0;

    public MyRecyclerAdapter(Context context, List<ListItems> list){
        this.mListItemses = list;
        this.mContext = context;
    }

    @Override
    public ListRowViewHolder onCreateViewHolder(final ViewGroup viewGroup, int position) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cities_rw_item, null);
        ListRowViewHolder holder = new ListRowViewHolder(v);

        holder.cities_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                TextView city_id_tv = (TextView) v.findViewById(R.id.city_id);
                String city_id = city_id_tv.getText().toString();

                TextView title_tv = (TextView) v.findViewById(R.id.title);
                String title = title_tv.getText().toString();

                Intent intent = new Intent(mContext, PlacesListActivity.class);
                intent.putExtra("city_id", city_id);
                intent.putExtra("title", title);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListRowViewHolder mListRowViewHolder, int position) {
        ListItems listItems = mListItemses.get(position);
        mListRowViewHolder.itemView.setSelected(focusedItem == position);
        mListRowViewHolder.getLayoutPosition();

        Picasso.with(mContext).load(listItems.getImage()).placeholder(R.drawable.placeholder).into(mListRowViewHolder.image);
        mListRowViewHolder.title.setText(Html.fromHtml(listItems.getTitle()));
        mListRowViewHolder.id.setText(Html.fromHtml(listItems.getCityId()));
    }

    public void clearAdapter (){
        mListItemses.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (null != mListItemses ? mListItemses.size() : 0);
    }


}
