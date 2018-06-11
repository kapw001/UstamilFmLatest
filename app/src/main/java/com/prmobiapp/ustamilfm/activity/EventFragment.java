package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.prmobiapp.ustamilfm.R;

/**
 * Created by Ravi on 29/07/15.
 */
public class EventFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";


    private FragmentTabHost mTabHost;

    public EventFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.eventfragment_broad, container, false);

        mTabHost = (FragmentTabHost)rootView.findViewById(android.R.id.tabhost);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
//        mTabHost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.roundedcorners);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentb").setIndicator("Upcoming"),
                EventUpcoming.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Completed"),
                EventCompleted.class, null);
        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
                {
                    TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
                    tv.setTextColor(Color.parseColor("#000000"));
                    tv.setAllCaps(false);
                }
//                mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#ffffff"));
                TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(android.R.id.title);
                tv.setTextColor(Color.parseColor("#ffffff"));
                tv.setAllCaps(false);
            }
        });
        for(int i=0;i<mTabHost.getTabWidget().getChildCount();i++)
        {
            TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(14.0f);
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setAllCaps(false);
        }
        TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(mTabHost.getCurrentTab()).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setAllCaps(false);
//        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Movie Teaser"),
//                MovieTeaser.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Movie Teaser"),
//                MovieTeaser.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("fragmentc").setIndicator("Movie Teaser"),
//                MovieTeaser.class, null);

//         /* Initialize recyclerview */
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                feedItemList = UtilFunctions.listOfSongs(getActivity());
//                adapter = new MyRecyclerAdapter(getActivity(), feedItemList);
//                mRecyclerView.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
////                mRecyclerView.notifyAll();
//            }
//        }, 300);
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
