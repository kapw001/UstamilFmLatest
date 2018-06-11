package com.prmobiapp.ustamilfm;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.prmobiapp.ustamilfm.activity.Player;

public class Setting extends AppCompatActivity {
    public static android.support.v7.widget.Toolbar mToolbar;
    SharedPreferences sharedpreferences;
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("   Settings");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       sharedpreferences = getSharedPreferences("MyP", Context.MODE_PRIVATE);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        SharedPreferences shared = getSharedPreferences("MyP", MODE_PRIVATE);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();

                check=check+1;
                if(check>1)
                {
                int posi=position;
                String sectposi=String.valueOf(posi);
////                if (sectposi.equals("0")){
////
////                }else {
//                    SongService.mp.stop();
////                    PlayerConstants.SONG_PAUSED = true;
////                    Player.playpause.setImageResource(R.drawable.play);
////                }
//                    Controls.fragplayControl(getApplicationContext());
//                    Player.playpause.setVisibility(View.INVISIBLE);

//                Toast.makeText(parent.getContext(), "Selected: " + position, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("text", item);
                editor.putInt("position",position);
                editor.commit();


                // Showing selected spinner item
//                Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("HIGH");
//        categories.add("96 bits");
        categories.add("LOW");
//        categories.add("32 bits");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        int postion=shared.getInt("position",0);
        spinner.setSelection(postion);

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
