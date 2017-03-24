package com.snackit.android;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.StringTokenizer;

public class SnackWeek extends AppCompatActivity {


    ImageView backBtn;

    static final String KEY_ID = "";
    static final String KEY_TAG = "tag";
    static final String KEY_ESSEN = "essen";

    ArrayList<String> weekdays = new ArrayList<String>();

    String ID1,Name,Tag,ID2,Name2,Tag2,ID3,Name3,Tag3,ID4,Name4,Tag4;

    ArrayList<SnackDay> arrayOfUsers = new ArrayList<SnackDay>();

    ArrayList<String> snackweek = new ArrayList<String>();
    ArrayList<SnackDay> snackdays = new ArrayList<SnackDay>();

    ListView mainListView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_week);

        weekdays.add(0,"Montag");
        weekdays.add(1,"Sonntag");
        weekdays.add(2,"Montag");
        weekdays.add(3,"Dienstag");
        weekdays.add(4,"Mittwoch");
        weekdays.add(5,"Donnerstag");
        weekdays.add(6,"Freitag");
        weekdays.add(7,"Samstag");

        backBtn = (ImageView) findViewById(R.id.btn_back);

        final UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        mainListView = (ListView) findViewById(R.id.mainListView);
        mainListView.setAdapter(adapter);

        Calendar c = Calendar.getInstance();

        SharedPreferences prefss = getSharedPreferences("snackit", MODE_PRIVATE);
        for(int i = 0; i <  prefss.getInt("counter",0); i++){
            System.out.println("gehe " + i);
            snackweek.add(i,prefss.getString("snackweeklike"+i,""));
            System.out.println(snackweek.get(i));

            StringTokenizer tokens = new StringTokenizer(snackweek.get(i), ";");
            String day = tokens.nextToken();
            String id = tokens.nextToken();
            String name = tokens.nextToken();
            String tageszeit = tokens.nextToken();


            StringTokenizer daytoken = new StringTokenizer(day, ",");
            String weekday = daytoken.nextToken();
            String dateofyear = daytoken.nextToken();
            String daytime = daytoken.nextToken();


            int cr = c.get(Calendar.DAY_OF_WEEK);
            System.out.println("HEUTE " + cr);
            if(weekdays.get(cr).equals(weekday)){
                weekday = "Heute";
            }

            int eq = 0;
            if(weekday.equals("Sonntag")){eq = 1;}
            if(weekday.equals("Montag")){eq = 2;}
            if(weekday.equals("Dienstag")){eq = 3;}
            if(weekday.equals("Mittwoch")){eq = 4;}
            if(weekday.equals("Donnerstag")){eq = 5;}
            if(weekday.equals("Freitag")){eq = 6;}
            if(weekday.equals("Samstag")){eq = 7;}

            if(tageszeit.equals("1")){tageszeit="Morgens";}
            if(tageszeit.equals("2")){tageszeit="Mittags";}
            if(tageszeit.equals("3")){tageszeit="Abends";}

            cr = cr -1;
            if(cr == eq){weekday = "Gestern";}


            snackdays.add(i,new SnackDay(tageszeit,weekday,id,name));

            adapter.add(snackdays.get(i));

        }

        Collections.reverse(snackdays);
        adapter.clear();
        for(int i = 0; i <  prefss.getInt("counter",0); i++){
            adapter.add(snackdays.get(i));
        }



        backBtn.setFocusable(false);
        backBtn.setFocusableInTouchMode(false);


        // Click event for single list row
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view,
                                    int position, long id) {

                    Intent intent = new Intent(SnackWeek.this, RezeptView.class);
                    intent.putExtra("ID","" + adapter.getItem(position).id);
                    startActivity(intent);
                    finish();

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(SnackWeek.this, FragmentPagerMain.class);
                startActivity(intent);
                finish();

            }
        });


    }


    public void onBackPressed() {
        Intent intent = new Intent(SnackWeek.this, FragmentPagerMain.class);
        startActivity(intent);
        finish();
    }
}
