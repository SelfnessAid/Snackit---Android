package com.snackit.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Leitfaden extends AppCompatActivity {

    ImageView backBtn, ueberunsBtn, mehrBtn, profilBtn;
    ScrollView l_ueberuns, l_mehr, l_profil;

    ImageView btn_lactose, btn_nuss, btn_vegetarisch, btn_vegan;

    private boolean lactose,nuss,vegetarisch,vegan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitfaden);

        backBtn = (ImageView) findViewById(R.id.btn_back);
        ueberunsBtn = (ImageView)findViewById(R.id.btn_uns);
        mehrBtn = (ImageView)findViewById(R.id.btn_mehr);
        profilBtn = (ImageView)findViewById(R.id.btn_profil);

        btn_lactose = (ImageView) findViewById(R.id.btn_lactose);
        btn_nuss = (ImageView) findViewById(R.id.btn_nuss);
        btn_vegetarisch = (ImageView) findViewById(R.id.btn_vegetarisch);
        btn_vegan = (ImageView) findViewById(R.id.btn_vegan);

        ueberunsBtn.setBackgroundResource(R.drawable.btn_ueber_notselecte);
        mehrBtn.setBackgroundResource(R.drawable.btn_agb_notselecte);
        profilBtn.setBackgroundResource(R.drawable.btn_profil);

        l_ueberuns = (ScrollView)findViewById(R.id.layout_ueberuns);
        l_mehr = (ScrollView)findViewById(R.id.layout_mehr);
        l_profil = (ScrollView)findViewById(R.id.layout_profil);

        l_mehr.setVisibility(View.GONE);
        l_profil.setVisibility(View.VISIBLE);
        l_ueberuns.setVisibility(View.GONE);

        ueberunsBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                l_ueberuns.setVisibility(View.VISIBLE);
                l_mehr.setVisibility(View.GONE);
                l_profil.setVisibility(View.GONE);

                ueberunsBtn.setBackgroundResource(R.drawable.btn_ueber);
                mehrBtn.setBackgroundResource(R.drawable.btn_agb_notselecte);
                profilBtn.setBackgroundResource(R.drawable.btn_profil_notselecte);
            }
        });


        mehrBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                l_mehr.setVisibility(View.VISIBLE);
                l_ueberuns.setVisibility(View.GONE);
                l_profil.setVisibility(View.GONE);

                ueberunsBtn.setBackgroundResource(R.drawable.btn_ueber_notselecte);
                mehrBtn.setBackgroundResource(R.drawable.btn_agb);
                profilBtn.setBackgroundResource(R.drawable.btn_profil_notselecte);
            }
        });

        profilBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                l_profil.setVisibility(View.VISIBLE);
                l_ueberuns.setVisibility(View.GONE);
                l_mehr.setVisibility(View.GONE);

                ueberunsBtn.setBackgroundResource(R.drawable.btn_ueber_notselecte);
                mehrBtn.setBackgroundResource(R.drawable.btn_agb_notselecte);
                profilBtn.setBackgroundResource(R.drawable.btn_profil);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(Leitfaden.this, FragmentPagerMain.class);
                startActivity(intent);
                finish();

            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(Leitfaden.this, FragmentPagerMain.class);
        startActivity(intent);
        finish();
    }

}
