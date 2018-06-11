package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

import com.prmobiapp.ustamilfm.MovieTraillerItem;
import com.prmobiapp.ustamilfm.PagerAdapter;
import com.prmobiapp.ustamilfm.R;
import com.prmobiapp.ustamilfm.UtilFunctions;

/**
 * Created by Ravi on 29/07/15.
 */
public class MovieTeaser extends Fragment {
    private static final String TAG = "RecyclerViewExample";

    private List<MovieTraillerItem> feedItemList = new ArrayList<MovieTraillerItem>();
private String url;
    private RecyclerView mRecyclerView;

    private MyRecyclerMovieAdapter adapter;
    public MovieTeaser() {
        // Required empty public constructor
    }
//    public MovieTeaser(String url) {
//        // Required empty public constructor
//        this.url=url;
//    }
public void setUrl(String url){
    this.url=url;
}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_teaser, container, false);
//        String url = getArguments().getString("url");
        setUrl(PagerAdapter.getUrl);
 /* Initialize recyclerview */
        new Asytask().execute();
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                feedItemList = UtilFunctions.listOfItems(getActivity());
//                adapter = new MyRecyclerMovieAdapter(getActivity(), feedItemList);
//                mRecyclerView.setAdapter(adapter);
////                adapter.notifyDataSetChanged();
////                mRecyclerView.notifyAll();
//            }
//        }, 300);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewmoviewteaser);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean hideToolBar = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hideToolBar) {
                    MainActivity.mToolbar.animate().translationY(-MainActivity.mToolbar.getBottom()).setInterpolator(new AccelerateInterpolator()).start();
                    ((MainActivity)getActivity()).getSupportActionBar().hide();
                } else {
                    MainActivity.mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator()).start();
                    ((MainActivity)getActivity()).getSupportActionBar().show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 120) {
                    hideToolBar = true;

                } else if (dy < -2) {
                    hideToolBar = false;
                }

            }
        });

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

    private class Asytask extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] params) {
            feedItemList = UtilFunctions.listOfItems(getActivity(),url);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            adapter = new MyRecyclerMovieAdapter(getActivity(), feedItemList);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
