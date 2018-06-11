package com.prmobiapp.ustamilfm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListView;

public class Help extends AppCompatActivity {
//    public static android.support.v7.widget.Toolbar mToolbar;

    ListView list;
    public static Toolbar mToolbar;

    Integer[] imgid={

            R.drawable.playicon,
            R.drawable.stop1,
            R.drawable.fav_white,
            R.drawable.fav_mejo,
            R.drawable.programicon1,
            R.drawable.cameraicon1,
            R.drawable.broadcasted1,
            R.drawable.programicon1,
            R.drawable.request_attach1,
            R.drawable.contactus1,
    };

    String[] itemname ={

            "Play the live streaming ",
            "Stop the live streaming",
            "Remove favourite (push notification)",
            "Add favourite (push notification)",
            "Program Schedule",
            "Rj Profiles",
            "Recorded Program",
            "special Event",
            "Request Box",
            "Contact Us"
    };



//    String[] newtext ={
//            "car",
//            "car",
//            "car",
//            "car",
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("   Help");
//        getSupportActionBar().setIcon(R.drawable.icon);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
