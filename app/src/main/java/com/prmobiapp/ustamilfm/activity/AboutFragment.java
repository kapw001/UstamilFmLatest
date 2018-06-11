package com.prmobiapp.ustamilfm.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prmobiapp.ustamilfm.R;

/**
 * Created by Ravi on 29/07/15.
 */
public class AboutFragment extends Fragment {
    private static final String TAG = "RecyclerViewExample";



    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_about1, container, false);
//        TextView t3=(TextView)rootView.findViewById(R.id.textView3);
//        TextView t4=(TextView)rootView.findViewById(R.id.textView4);
//        TextView t5=(TextView)rootView.findViewById(R.id.textView5);
//        t3.setText(Html.fromHtml("Location:<b> P.O Box 92722,\n" +"Austin, TX - 78709.</b>").toString());
//        t4.setText(Html.fromHtml("Phone: <b>+1 512-777-1271</b>").toString());
//        t5.setText(Html.fromHtml("Email:<b> com@ustamilfm.com</b>").toString());
        ImageView t3=(ImageView)rootView.findViewById(R.id.youtube);
        ImageView t4=(ImageView)rootView.findViewById(R.id.facebook);
        ImageView t5=(ImageView)rootView.findViewById(R.id.googleplusimg);
        ImageView t6=(ImageView)rootView.findViewById(R.id.twitter);

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UCuEEdIm0BPSEf3C_gXpF4eQ"));
                startActivity(intent);
            }
        });

        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/124210434588680"));
                    startActivity(intent);
                } catch (Exception e) {
                    intent =  new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/ustamilfm"));
                    startActivity(intent);
                }
            }
        });

        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://plus.google.com/107433292874511312104/about" ) );
                intent.setPackage( "com.google.android.apps.plus" );
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity( intent );
                }else{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://plus.google.com/107433292874511312104/about")));
                }
            }
        });

        t6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                try {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=3311151757"));
                    startActivity(intent);
                } catch (Exception e) {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/ustamilfm"));
                    startActivity(intent);
                }
            }
        });


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
