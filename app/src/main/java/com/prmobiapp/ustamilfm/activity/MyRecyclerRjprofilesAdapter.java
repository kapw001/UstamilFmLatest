package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;

/**
 * Created by Karthik on 11/16/2015.
 */
public class MyRecyclerRjprofilesAdapter extends RecyclerView.Adapter<RjProfileFeedListRowHolder> {


    private List<MovieTraillerItem> feedItemList;

    private Context mContext;

    public MyRecyclerRjprofilesAdapter(Context context, List<MovieTraillerItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public RjProfileFeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rjprofiles, null);
        RjProfileFeedListRowHolder mh = new RjProfileFeedListRowHolder(v,feedItemList,mContext);

        return mh;
    }
    @Override
    public void onBindViewHolder(RjProfileFeedListRowHolder feedListRowHolder, int i) {
        MovieTraillerItem feedItem = feedItemList.get(i);

//        Picasso.with(mContext).load(feedItem.getThumimage())
//                .into(feedListRowHolder.movieteaserimage);

        Glide.with(mContext).load(feedItem.getThumimage()).thumbnail(.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(feedListRowHolder.movieteaserimage);

        if (i==feedItemList.size()-1){
            feedListRowHolder.moviename.setText(Html.fromHtml(feedItem.getName()));
            feedListRowHolder.moviesubname.setText(Html.fromHtml(feedItem.getSubname()));
            feedListRowHolder.rjclick.setText("RJ's Wanted");
        }else{
            feedListRowHolder.moviename.setText(Html.fromHtml(feedItem.getName()));
            feedListRowHolder.moviesubname.setText(Html.fromHtml(feedItem.getSubname()));
        }

    }
    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
