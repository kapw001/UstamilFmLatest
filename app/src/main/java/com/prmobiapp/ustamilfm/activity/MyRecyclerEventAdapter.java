package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;

/**
 * Created by Karthik on 11/16/2015.
 */
public class MyRecyclerEventAdapter extends RecyclerView.Adapter<EventFeedListRowHolder> {


    private List<MovieTraillerItem> feedItemList;
    private  String uporcom;
    private Context mContext;

    public MyRecyclerEventAdapter(Context context, List<MovieTraillerItem> feedItemList,String uporcom) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uporcom=uporcom;
//        MovieTraillerItem feedItem = feedItemList.get(1);
//        Log.e("RE",feedItem.getThumimage());
    }

    @Override
    public EventFeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teaser_row, null);
        EventFeedListRowHolder mh = new EventFeedListRowHolder(v,feedItemList,mContext,uporcom);

        return mh;
    }


    @Override
    public void onBindViewHolder(EventFeedListRowHolder feedListRowHolder, int i) {
        MovieTraillerItem feedItem = feedItemList.get(i);
        Log.e("RE",feedItem.getThumimage());
//        byte[] data = Base64.decode(feedItem.getThumimage(), Base64.DEFAULT);
//        String text = null;
//        try {
//            text = new String(data, "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        Picasso.with(mContext).load(feedItem.getThumimage())
//                .error(R.drawable.placeholder)
//                .placeholder(R.drawable.placeholder)
                .into(feedListRowHolder.movieteaserimage);

        feedListRowHolder.moviename.setText(Html.fromHtml(feedItem.getName()));
        feedListRowHolder.moviesubname.setText(Html.fromHtml(feedItem.getSubname()));

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
