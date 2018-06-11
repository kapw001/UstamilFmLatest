package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.UtilFunctions;

/**
 * Created by Ravi on 29/07/15.
 */
public class RjProfiles extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "RecyclerViewExample";

    private List<MovieTraillerItem> feedItemList = new ArrayList<MovieTraillerItem>();

    private RecyclerView mRecyclerView;

    private MyRecyclerRjprofilesAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    public RjProfiles() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rjprofiles, container, false);




 /* Initialize recyclerview */

//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                feedItemList = UtilFunctions.listOfRjprofiles(getActivity());
//                adapter = new MyRecyclerRjprofilesAdapter(getActivity(), feedItemList);
//                mRecyclerView.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
////                mRecyclerView.notifyAll();
//            }
//        }, 300);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewmoviewteaser);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
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
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Object doInBackground(Object[] params) {
            feedItemList = UtilFunctions.listOfRjprofiles(getActivity());

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            swipeRefreshLayout.setRefreshing(false);
            adapter = new MyRecyclerRjprofilesAdapter(getActivity(), feedItemList);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
