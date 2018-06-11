package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;

/**
 * Created by Karthik on 11/13/2015.
 */
public class EventFeedListRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected ImageView movieteaserimage;
    protected TextView moviename,moviesubname;
    private List<MovieTraillerItem> feedItemList;
    private Context mContext;
    private  String uporcom;
    public EventFeedListRowHolder(View view, List<MovieTraillerItem> feedItemList, Context context,String uporcom) {
        super(view);
        view.setOnClickListener(this);
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.uporcom=uporcom;
        this.movieteaserimage = (ImageView) view.findViewById(R.id.movieteaserimage);
        this.moviename = (TextView) view.findViewById(R.id.moviename);
        this.moviesubname=(TextView) view.findViewById(R.id.moviesubname);
    }

    @Override
    public void onClick(View v) {
        MovieTraillerItem feedItem = feedItemList.get(getPosition());
        if (feedItem.getDes()!=null &&feedItem.getDes().length()!=0) {
            Intent i = new Intent(mContext, EventFullPageView.class);
            i.putExtra("image", feedItem.getThumbimage2());
            i.putExtra("des", feedItem.getDes());
            i.putExtra("moreinfo", feedItem.getMoreinfo());
            i.putExtra("uporcom", uporcom);
            mContext.startActivity(i);
        }
//        Toast.makeText(v.getContext(), "position = " + feedItem.getMoreinfo(), Toast.LENGTH_SHORT).show();
    }
}