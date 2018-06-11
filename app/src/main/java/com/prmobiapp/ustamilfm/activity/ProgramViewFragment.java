package com.prmobiapp.ustamilfm.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;

import com.prmobiapp.ustamilfm.R;

/**
 * Created by Ravi on 29/07/15.
 */
public class ProgramViewFragment extends DialogFragment {
    private static final String TAG = "RecyclerViewExample";
    ImageView bigimage;
    TextView tamildestxt;

    public ProgramViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_program_view, container, false);
        bigimage=(ImageView)rootView.findViewById(R.id.programbigimage);
        tamildestxt=(TextView)rootView.findViewById(R.id.tamildes);
        Intent pi=getActivity().getIntent();
        String urlimage=pi.getStringExtra("imgae");
        String tamildes=pi.getStringExtra("tamildes");
        byte[] data = Base64.decode(tamildes, Base64.DEFAULT);
        try {
            String text = new String(data, "UTF-8");
            tamildestxt.setText(text);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Picasso.with(getActivity()).load(urlimage)
                .into(bigimage);
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
