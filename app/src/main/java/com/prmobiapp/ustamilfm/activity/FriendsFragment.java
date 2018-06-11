package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.prmobiapp.ustamilfm.MediaItem;
import com.prmobiapp.ustamilfm.MyService;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.UtilFunctions;
import com.prmobiapp.ustamilfm.Utils;

import info.hoang8f.android.segmented.SegmentedGroup;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by Ravi on 29/07/15.
 */
public class FriendsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "RecyclerViewExample";

    private ArrayList<MediaItem> feedItemList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyRecyclerAdapter adapter;
    private SegmentedGroup segmented2;

    public FriendsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        segmented2 = (SegmentedGroup) rootView.findViewById(R.id.segmented2);

        segmented2.setVisibility(View.GONE);
        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new Asytask().execute();
                                    }
                                }
        );

        swipeRefreshLayout.setEnabled(false);
         /* Initialize recyclerview */
//        new Asytask().execute();
//        Thread t=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                feedItemList = UtilFunctions.listOfSongs(getActivity());
//                adapter = new MyRecyclerAdapter(getActivity(), feedItemList);
//                mRecyclerView.setAdapter(adapter);
//            }
//        });
//        t.start();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                feedItemList = UtilFunctions.listOfSongs(getActivity());
//                adapter = new MyRecyclerAdapter(getActivity(), feedItemList);
//                mRecyclerView.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
////                mRecyclerView.notifyAll();
//            }
//        }, 300);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.setItemAnimator(new SlideInLeftAnimator());


//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            boolean hideToolBar = false;
//
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (hideToolBar) {
//                    MainActivity.mToolbar.animate().translationY(-MainActivity.mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
//                    ((MainActivity) getActivity()).getSupportActionBar().hide();
//                } else {
//                    MainActivity.mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
//                    ((MainActivity) getActivity()).getSupportActionBar().show();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 120) {
//                    hideToolBar = true;
//
//                } else if (dy < -2) {
//                    hideToolBar = false;
//                }
//
//            }
//        });


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

    @Override
    public void onRefresh() {
        new Asytask().execute();
    }


    private class Asytask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {

            feedItemList = UtilFunctions.listOfSongs(getActivity());

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            swipeRefreshLayout.setRefreshing(true);
//            if (feedItemList!=null && feedItemList.size()>0) {
//                feedItemList.remove(feedItemList.size() - 1);
//            }
            adapter = new MyRecyclerAdapter(getActivity(), feedItemList);
//            mRecyclerView.setAdapter(adapter);
            AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
            ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
//            scaleAdapter.setDuration(1000);
            //        scaleAdapter.setFirstOnly(false);
//                    scaleAdapter.setInterpolator(new OvershootInterpolator());
            mRecyclerView.setAdapter(scaleAdapter);
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
                mMessageReceiver, Utils.registerIntentFilterIMAGECHANGE());
//        Intent in = new Intent(getActivity(), MyService.class);
//        in.setAction(Utils.CHANGEPROGRAMIMAGE);
//        getActivity().startService(in);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
                mMessageReceiver);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            // Get extra data included in the Intent
//            int position = intent.getIntExtra("message",0);
            if (intent.getAction().equals(Utils.CHANGEPROGRAMIMAGE)) {
                updateList(intent.getIntExtra("pos", 0));
            }
        }
    };

    public void updateList(final int pos) {


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//                adapter.setPositionSelected(pos);
//                adapter.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "" + pos, Toast.LENGTH_SHORT).show();
//            }
//        }, 3000);


    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
//                mMessageReceiver, Utils.registerIntentFilter());
//
//    }
//
//
//    @Override
//    public void onPause() {
//        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(
//                mMessageReceiver);
//        super.onPause();
//
//    }
//
//    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // TODO Auto-generated method stub
//            // Get extra data included in the Intent
//            String message = intent.getStringExtra("message");
//
//            Log.e("receiver", "Got message: " + message);
//        }
//    };

}
