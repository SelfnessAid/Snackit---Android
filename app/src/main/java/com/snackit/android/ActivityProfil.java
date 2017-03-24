package com.snackit.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.TimerTask;

public class ActivityProfil extends AppCompatActivity {

    ImageView backBtn, ueberunsBtn, mehrBtn, profilBtn;
    private boolean lactose,nuss,vegetarisch,vegan = false;
    ImageView btn_lactose, btn_nuss, btn_vegetarisch, btn_vegan;
    LinearLayout l_lactose, l_nuss, l_vegetarisch, l_vegan;
    ApplicationSnackit snackit;
    final Handler handler = new Handler();
    public float timeCounter;
    TimerTask timerTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_profil);

//        snackit = (ApplicationSnackit) getApplication();

        backBtn = (ImageView) findViewById(R.id.btn_back);
        ueberunsBtn = (ImageView)findViewById(R.id.btn_uns);
        mehrBtn = (ImageView)findViewById(R.id.btn_mehr);
        profilBtn = (ImageView)findViewById(R.id.btn_profil);

        l_lactose = (LinearLayout)findViewById(R.id.l_lactose);
        l_nuss = (LinearLayout)findViewById(R.id.l_nuss);
        l_vegetarisch = (LinearLayout)findViewById(R.id.l_vegetarisch);
        l_vegan = (LinearLayout)findViewById(R.id.l_vegan);

        btn_lactose = (ImageView) findViewById(R.id.btn_lactose);
        btn_nuss = (ImageView) findViewById(R.id.btn_nuss);
        btn_vegetarisch = (ImageView) findViewById(R.id.btn_vegetarisch);
        btn_vegan = (ImageView) findViewById(R.id.btn_vegan);


        final SharedPreferences prefss = getSharedPreferences("snackit", Context.MODE_PRIVATE);


        if(prefss.getBoolean("lactose",false)){
            btn_lactose.setBackgroundResource(R.drawable.btn_selecte);
        }
        if(prefss.getBoolean("nuss",false)){
            btn_nuss.setBackgroundResource(R.drawable.btn_selecte);
        }
        if(prefss.getBoolean("vegetarisch",false)){
            btn_vegetarisch.setBackgroundResource(R.drawable.btn_selecte);
        }
        if(prefss.getBoolean("vegan",false)){
            btn_vegan.setBackgroundResource(R.drawable.btn_selecte);
        }

        l_lactose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if(lactose){
                    btn_lactose.setBackgroundResource(R.drawable.btn_empty);
                    prefss.edit().putBoolean("lactose",false).apply();
                    lactose = false;
                }else{
                    btn_lactose.setBackgroundResource(R.drawable.btn_selecte);
                    prefss.edit().putBoolean("lactose",true).apply();

                    lactose = true;
                }
            }
        });

        l_nuss.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(nuss){
                    btn_nuss.setBackgroundResource(R.drawable.btn_empty);
                    prefss.edit().putBoolean("nuss",false).apply();

                    nuss = false;
                }else{
                    btn_nuss.setBackgroundResource(R.drawable.btn_selecte);
                    prefss.edit().putBoolean("nuss",true).apply();

                    nuss = true;
                }
            }
        });

        l_vegetarisch.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(vegetarisch){
                    btn_vegetarisch.setBackgroundResource(R.drawable.btn_empty);
                    prefss.edit().putBoolean("vegetarisch",false).apply();

                    vegetarisch = false;
                }else{
                    btn_vegetarisch.setBackgroundResource(R.drawable.btn_selecte);
                    prefss.edit().putBoolean("vegetarisch",true).apply();

                    vegetarisch = true;
                }
            }
        });

        l_vegan.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(vegan){
                    btn_vegan.setBackgroundResource(R.drawable.btn_empty);
                    prefss.edit().putBoolean("vegan",false).apply();

                    vegan = false;
                }else{
                    btn_vegan.setBackgroundResource(R.drawable.btn_selecte);
                    prefss.edit().putBoolean("vegan",true).apply();

                    vegan = true;
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                snackit = (ApplicationSnackit) getApplication();
                Intent intent = new Intent(ActivityProfil.this, FragmentPagerMain.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed() {
        snackit = (ApplicationSnackit) getApplication();
        Intent intent = new Intent(ActivityProfil.this, FragmentPagerMain.class);
        startActivity(intent);
        finish();
    }
}
