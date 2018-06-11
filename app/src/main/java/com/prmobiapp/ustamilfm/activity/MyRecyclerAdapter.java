package com.prmobiapp.ustamilfm.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.prmobiapp.ustamilfm.MediaItem;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.sharedpreferences;

/**
 * Created by Karthik on 11/13/2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<FeedListRowHolder> {


    private List<MediaItem> feedItemList;

    private Context mContext;
    static int lastPosition;

    private int positionSelected = -1;

    public MyRecyclerAdapter(Context context, List<MediaItem> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    public void setPositionSelected(int pos) {
        positionSelected = pos;
        notifyDataSetChanged();
    }

    @Override
    public FeedListRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.programs_row_list, null);
        FeedListRowHolder mh = new FeedListRowHolder(v, feedItemList, mContext);

        return mh;
    }

    @Override
    public void onBindViewHolder(final FeedListRowHolder feedListRowHolder, int i) {
        final MediaItem feedItem = feedItemList.get(i);
//
//        Picasso.with(mContext).load(feedItem.getUrlImage())
////                .error(R.drawable.placeholder)
////                .placeholder(R.drawable.placeholder)
//                .into(feedListRowHolder.thumbnail);

        Glide.with(mContext).load(feedItem.getUrlImage()).thumbnail(.5f).diskCacheStrategy(DiskCacheStrategy.ALL).into(feedListRowHolder.thumbnail);

        feedListRowHolder.programshowname.setText(Html.fromHtml(feedItem.getArtist()));
        feedListRowHolder.programrjname.setText(Html.fromHtml(feedItem.getTitle()));
//        feedListRowHolder.programshowtime.setText("CST: "+Html.fromHtml(feedItem.getsTime()+" TO "+feedItem.geteTime()));
        feedListRowHolder.programshowtime.setText("CST: " + Html.fromHtml(feedItem.getsTime()));
        if (feedItem.getfavorite().equalsIgnoreCase("false")) {
            feedListRowHolder.faviorprogram.setImageResource(R.drawable.favorites);
        } else if (feedItem.getfavorite().equalsIgnoreCase("true")) {
            feedListRowHolder.faviorprogram.setImageResource(R.drawable.fav_red);
        }
        feedListRowHolder.faviorprogram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feedItem.getfavorite().equalsIgnoreCase("false")) {
                    feedListRowHolder.faviorprogram.setImageResource(R.drawable.fav_red);
                    sharedpreferences.savepre(feedItem.getTitle(), "true");
                    feedItem.setfavorite("true");
//                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                } else if (feedItem.getfavorite().equalsIgnoreCase("true")) {
                    feedListRowHolder.faviorprogram.setImageResource(R.drawable.favorites);
                    feedItem.setfavorite("false");
                    sharedpreferences.savepre(feedItem.getTitle(), "false");
//                    PlayerConstants.SONG_CHANGE_HANDLER.sendMessage(PlayerConstants.SONG_CHANGE_HANDLER.obtainMessage());
                }
            }
        });

        if (positionSelected == i) {
            feedListRowHolder.colorchange.setBackgroundColor(Color.parseColor("#c1243f"));
            feedListRowHolder.programshowname.setTextColor(Color.WHITE);
            feedListRowHolder.programrjname.setTextColor(Color.WHITE);
            feedListRowHolder.programshowtime.setTextColor(Color.WHITE);
            notifyItemChanged(i);
        } else {
            feedListRowHolder.colorchange.setBackgroundColor(Color.WHITE);
            feedListRowHolder.programshowname.setTextColor(Color.BLACK);
            feedListRowHolder.programrjname.setTextColor(Color.BLACK);
            feedListRowHolder.programshowtime.setTextColor(Color.BLACK);
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext,
//                (i > lastPosition) ? R.anim.up_from_bottom
//                        : R.anim.down_from_top);
//        feedListRowHolder.itemview.startAnimation(animation);
//        lastPosition = i;

    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }
}
