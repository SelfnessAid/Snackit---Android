package com.snackit.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MotionEventCompat;
import android.transition.Fade;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;


public class ToGoEins extends Fragment {

    public String title;
    private int page;

    ImageView selecte, selecte2, selecte3,selecte4,selecte5;
    boolean clicket = false;
    boolean clicket2 = false;
    boolean clicket3 = false;
    boolean clicket4 = false;
    boolean clicket5 = false;

    private String likeName;
    private int inex;

    ImageView img_bild, img_zutat1, img_zutat2, img_zutat3,img_zutat4,img_zutat5,btn_like,btn_like2;
    public TextView txt_name, txt_zutat1, txt_zutat2, txt_zutat3,txt_zutat4,txt_zutat5, txt_zutat1anzahl,txt_zutat2anzahl, txt_zutat3anzahl,txt_zutat4anzahl,txt_zutat5anzahl, txtzubereitung1,txtzubereitung2,txtzubereitung3,txtzubereitung4,txtzubereitung5;
    TextView txt_zutat1anzahl2,txt_zutat2anzahl2,txt_zutat3anzahl2,txt_zutat4anzahl2,txt_zutat5anzahl2;
    ImageView btn_brauchst;

    ImageView zutat1beschreibung,zutat2beschreibung,zutat3beschreibung,zutat4beschreibung,zutat5beschreibung;

    String category = "morgens_togo_suess";
    boolean vegetarisch,nuss,vegan,lactose;

    RelativeLayout rezept_content;
    ImageView check, check2, check3,check4,check5;

    private boolean cli = false;

    LinearLayout l1,l2,l3,l4,l5;
    LinearLayout l_zubereitung, l_like, l_sogehts;
    ImageView zb1,zb2,zb3,zb4,zb5;

    Typeface displayBold,displayMedium,displayThin,displayTitling;
    TextView textView5,textView6,textlike;

    ImageView btnBackTop, btnOder,btnEntweder,btnBackTop2;
    CustomPager vp;

    //ScrollView scrollView;
    StickyScrollView scrollView;

    LinearLayout untere_leiste;

    ImageView m360DegreeImageView;

    ArrayList<String> gif = new ArrayList<String>();
    ArrayList<Bitmap> gof = new ArrayList<Bitmap>();
    ArrayList<Drawable> dr = new ArrayList<Drawable>();
    ArrayList<File> fli = new ArrayList<File>();

    int array = 0;


    ApplicationSnackit snackit;

    // newInstance constructor for creating fragment with arguments
    public static ToGoEins newInstance(int page, String title) {
        ToGoEins fragmentFirst = new ToGoEins();
        Bundle args = new Bundle();
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    LevelListDrawable l;
    LayerDrawable ld;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(ToGoEins.this).attach(ToGoEins.this).commit();
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_go_eins_test_two, container, false);

        //getFragmentManager().beginTransaction().detach(this).attach(this).commit();

        vp =(CustomPager) getActivity().findViewById(R.id.viewPagerMain);

        snackit = (ApplicationSnackit) getActivity().getApplication();
        displayBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Bold.ttf");
        displayMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Medium.ttf");
        displayThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Light.ttf");

        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView5.setTypeface(displayMedium);
        textView6 = (TextView)view.findViewById(R.id.textView6);
        textView6.setTypeface(displayMedium);
        textlike = (TextView) view.findViewById(R.id.textlike);
        textlike.setTypeface(displayMedium);

        btnBackTop = (ImageView) view.findViewById(R.id.btnBackTop);
        btnOder = (ImageView) view.findViewById(R.id.btnOder);
        btnEntweder = (ImageView) view.findViewById(R.id.btnEntweder);
        btnBackTop2 = (ImageView) view.findViewById(R.id.btnBackBottom);

        btnEntweder.setVisibility(View.GONE);
        btnOder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                vp.setCurrentItem(4);
            }
        });

        btnBackTop2.setVisibility(View.GONE);


        l_like = (LinearLayout)view.findViewById(R.id.l_like);
        l_zubereitung = (LinearLayout)view.findViewById(R.id.l_zubereitung);


        btnBackTop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                vp.setCurrentItem(2);
            }
        });

        scrollView = (StickyScrollView)view.findViewById(R.id.scrollView);
        l_sogehts = (LinearLayout)view.findViewById(R.id.l_sogehts);
        untere_leiste = (LinearLayout)view.findViewById(R.id.btnLeiste);

        untere_leiste.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, 1600);
            }
        });

        l_sogehts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                scrollView.smoothScrollTo(0, scrollView.getBottom());
            }
        });

        l1 = (LinearLayout) view.findViewById(R.id.layout1);
        l2 = (LinearLayout) view.findViewById(R.id.layout2);
        l3 = (LinearLayout) view.findViewById(R.id.layout3);
        l4 = (LinearLayout) view.findViewById(R.id.layout4);
        l5 = (LinearLayout) view.findViewById(R.id.layout5);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        l4.setVisibility(View.GONE);
        l5.setVisibility(View.GONE);

        zb1 = (ImageView) view.findViewById(R.id.zubereitung1beschreibung);
        zb2 = (ImageView) view.findViewById(R.id.zubereitung2beschreibung);
        zb3 = (ImageView) view.findViewById(R.id.zubereitung3beschreibung);
        zb4 = (ImageView) view.findViewById(R.id.zubereitung4beschreibung);
        zb5 = (ImageView) view.findViewById(R.id.zubereitung5beschreibung);

        zb1.setVisibility(View.GONE);
        zb2.setVisibility(View.GONE);
        zb3.setVisibility(View.GONE);
        zb4.setVisibility(View.GONE);
        zb5.setVisibility(View.GONE);


        img_bild = (ImageView) view.findViewById(R.id.img_bild);
        img_zutat1 = (ImageView) view.findViewById(R.id.img_ZutatBild1);
        img_zutat2 = (ImageView) view.findViewById(R.id.img_ZutatBild2);
        img_zutat3 = (ImageView)view.findViewById(R.id.img_ZutatBild3);
        img_zutat4 = (ImageView)view.findViewById(R.id.img_ZutatBild4);
        img_zutat5 = (ImageView)view.findViewById(R.id.img_ZutatBild5);
        btn_like = (ImageView)view.findViewById(R.id.btn_like);
        btn_like.setVisibility(View.GONE);
        btn_like2 = (ImageView)view.findViewById(R.id.btn_like2);
        btn_like2.setVisibility(View.GONE);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                final int id = snackit.suess_ID.get(num);
//                idss = id;
//                StringTokenizer nam = new StringTokenizer(snackit.suess_Name.get(num), "::");
//                final String name = nam.nextToken();
//                System.out.println("COUNTERS " + snackit.suess_Name.get(num));

//                if(!cli){
//                    final SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
//
//                    System.out.println("COUNTER " + prefss.getInt("counter",0));
//
//
//                    int counter = prefss.getInt("counter",0);
//
//
//                    DateFormat df = new SimpleDateFormat("EEEE, d MMM yyyy, HH:mm");
//                    String day = df.format(Calendar.getInstance().getTime());
//
//                    //prefss.edit().putString("ID",prefss.getString("ID","") + ";" + id ).apply();
////                    prefss.edit().putString("snackweeklike" + prefss.getInt("counter",0),"" + day + ";" + idss  + ";" + name + ";" + d + ";").apply();
//                    btn_like.setBackgroundResource(R.drawable.btn_selecte);
//
//                    counter = counter +1;
//                    prefss.edit().putInt("counter",counter).apply();
//                    cli = true;
//                }else{
//                    btn_like.setBackgroundResource(R.drawable.btn_check);
//                    cli= false;
//                }

            }

        });


        txt_name = (TextView) view.findViewById(R.id.txt_name);
        txt_zutat1 = (TextView) view.findViewById(R.id.txt_Zutat1);
        txt_zutat2 = (TextView) view.findViewById(R.id.txt_Zutat2);
        txt_zutat3 = (TextView) view.findViewById(R.id.txt_Zutat3);
        txt_zutat4 = (TextView) view.findViewById(R.id.txt_Zutat4);
        txt_zutat5 = (TextView) view.findViewById(R.id.txt_Zutat5);

        txt_zutat1anzahl = (TextView) view.findViewById(R.id.txt_Zutat1Anzahl);
        txt_zutat2anzahl = (TextView) view.findViewById(R.id.txt_Zutat2Anzahl);
        txt_zutat3anzahl = (TextView) view.findViewById(R.id.txt_Zutat3Anzahl);
        txt_zutat4anzahl = (TextView) view.findViewById(R.id.txt_Zutat4Anzahl);
        txt_zutat5anzahl = (TextView) view.findViewById(R.id.txt_Zutat5Anzahl);

        txt_zutat1anzahl2 = (TextView) view.findViewById(R.id.txt_Zutat1Anzahl2);
        txt_zutat2anzahl2 = (TextView) view.findViewById(R.id.txt_Zutat2Anzahl2);
        txt_zutat3anzahl2 = (TextView) view.findViewById(R.id.txt_Zutat3Anzahl2);
        txt_zutat4anzahl2 = (TextView) view.findViewById(R.id.txt_Zutat4Anzahl2);
        txt_zutat5anzahl2 = (TextView) view.findViewById(R.id.txt_Zutat5Anzahl2);

        txtzubereitung1 = (TextView)view.findViewById(R.id.txt_zubereitung1);
        txtzubereitung2 = (TextView)view.findViewById(R.id.txt_zubereitung2);
        txtzubereitung3 = (TextView)view.findViewById(R.id.txt_zubereitung3);
        txtzubereitung4 = (TextView)view.findViewById(R.id.txt_zubereitung4);
        txtzubereitung5 = (TextView)view.findViewById(R.id.txt_zubereitung5);
        txtzubereitung1.setVisibility(View.GONE);
        txtzubereitung2.setVisibility(View.GONE);
        txtzubereitung3.setVisibility(View.GONE);
        txtzubereitung4.setVisibility(View.GONE);
        txtzubereitung5.setVisibility(View.GONE);


        final SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
        vegetarisch = prefss.getBoolean("vegetarisch",false);
        nuss = prefss.getBoolean("nuss",false);
        vegan = prefss.getBoolean("vegan",false);
        lactose = prefss.getBoolean("lactose",false);

//        initialize();
        getInfo();

       return view;
    }

    private String getLastString(String str) {
        StringTokenizer str_tokenizer = new StringTokenizer(str, "::");
        return str_tokenizer.nextToken();
    }

    private void initialize(CDAEntry entry) {
//        if (snackit.suess.size() == 0)
//            return;
//        CDAEntry entry = snackit.suess.get(0);

        String title = entry.getField("title");
        txt_name.setText(getLastString(title));

        //Set Main Image of Hearty
        CDAAsset image = entry.getField("image");
        String image_url = "http:" + image.url();
        Glide.with(getActivity()).load(image_url).into(img_bild);

        //Set Ingredients
        ArrayList<CDAEntry> ingredients = entry.getField("ingredients");

        l1.setVisibility(View.VISIBLE);
        txt_zutat1anzahl.setTypeface(displayThin);
        txt_zutat1.setTypeface(displayBold);
        CDAEntry ingredient_first = ingredients.get(0);
        String ingredient_first_amount = ingredient_first.getField("amount");
        txt_zutat1anzahl.setText(getLastString(ingredient_first_amount));
        String ingredient_first_unit = ingredient_first.getField("unit");
        txt_zutat1anzahl2.setText(getLastString(ingredient_first_unit));
        String ingredient_first_title = ingredient_first.getField("title");
        txt_zutat1.setText(getLastString(ingredient_first_title));
        CDAAsset ingredient_first_image = ingredient_first.getField("image");
        String ingredient_first_image_url = "http:" + ingredient_first_image.url();
        Glide.with(getActivity())
                .load(ingredient_first_image_url)
                .override(250,250)
                .into(img_zutat1);

        if(ingredients.size() > 1) {
            l2.setVisibility(View.VISIBLE);
            txt_zutat2anzahl.setTypeface(displayThin);
            txt_zutat2.setTypeface(displayBold);
            CDAEntry ingredient_second = ingredients.get(1);
            String ingredient_second_amount = ingredient_second.getField("amount");
            txt_zutat2anzahl.setText(getLastString(ingredient_second_amount));
            String ingredient_second_unit = ingredient_second.getField("unit");
            txt_zutat2anzahl2.setText(getLastString(ingredient_second_unit));
            String ingredient_second_title = ingredient_second.getField("title");
            txt_zutat2.setText(getLastString(ingredient_second_title));
            CDAAsset ingredient_second_image = ingredient_second.getField("image");
            String ingredient_second_image_url = "http:" + ingredient_second_image.url();
            Glide.with(getActivity())
                    .load(ingredient_second_image_url)
                    .override(250,250)
                    .into(img_zutat2);
        } else {
            l2.setVisibility(View.GONE);
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
        }

        if(ingredients.size() >2) {
            l3.setVisibility(View.VISIBLE);
            txt_zutat3anzahl.setTypeface(displayThin);
            txt_zutat3.setTypeface(displayBold);
            CDAEntry ingredient_third = ingredients.get(2);
            String ingredient_third_amount = ingredient_third.getField("amount");
            txt_zutat3anzahl.setText(getLastString(ingredient_third_amount));
            String ingredient_third_unit = ingredient_third.getField("unit");
            txt_zutat3anzahl2.setText(getLastString(ingredient_third_unit));
            String ingredient_third_title = ingredient_third.getField("title");
            txt_zutat3.setText(getLastString(ingredient_third_title));
            CDAAsset ingredient_third_image = ingredient_third.getField("image");
            String ingredient_third_image_url = "http:" + ingredient_third_image.url();
            Glide.with(getActivity())
                    .load(ingredient_third_image_url)
                    .override(250,250)
                    .into(img_zutat3);
        } else {
            l3.setVisibility(View.GONE);
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
        }

        if (ingredients.size() >3) {
            l4.setVisibility(View.VISIBLE);
            txt_zutat4anzahl.setTypeface(displayThin);
            txt_zutat4.setTypeface(displayBold);
            CDAEntry ingredient_forth = ingredients.get(3);
            String ingredient_forth_amount = ingredient_forth.getField("amount");
            txt_zutat4anzahl.setText(getLastString(ingredient_forth_amount));
            String ingredient_forth_unit = ingredient_forth.getField("unit");
            txt_zutat4anzahl2.setText(getLastString(ingredient_forth_unit));
            String ingredient_forth_title = ingredient_forth.getField("title");
            txt_zutat4.setText(getLastString(ingredient_forth_title));
            CDAAsset ingredient_forth_image = ingredient_forth.getField("image");
            String ingredient_forth_image_url = "http:" + ingredient_forth_image.url();
            Glide.with(getActivity())
                    .load(ingredient_forth_image_url)
                    .override(250,250)
                    .into(img_zutat4);
        } else {
            l4.setVisibility(View.GONE);
            l5.setVisibility(View.GONE);
        }

        if (ingredients.size() >4) {
            l5.setVisibility(View.VISIBLE);
            txt_zutat5anzahl.setTypeface(displayThin);
            txt_zutat5.setTypeface(displayBold);
            CDAEntry ingredient_fifth = ingredients.get(4);
            String ingredient_fifth_amount = ingredient_fifth.getField("amount");
            txt_zutat5anzahl.setText(getLastString(ingredient_fifth_amount));
            String ingredient_fifth_unit = ingredient_fifth.getField("unit");
            txt_zutat5anzahl2.setText(getLastString(ingredient_fifth_unit));
            String ingredient_fifth_title = ingredient_fifth.getField("title");
            txt_zutat5.setText(getLastString(ingredient_fifth_title));
            CDAAsset ingredient_fifth_image = ingredient_fifth.getField("image");
            String ingredient_fifth_image_url = "http:" + ingredient_fifth_image.url();
            Glide.with(getActivity())
                    .load(ingredient_fifth_image_url)
                    .override(250,250)
                    .into(img_zutat5);
        } else {
            l5.setVisibility(View.GONE);
        }

//        //Set Tutorial
        String tutorial = entry.getField("preparation");
        tutorial = getLastString(tutorial);
        String[] tutorialList = tutorial.split("\n");

        zb1.setVisibility(View.VISIBLE);
        txtzubereitung1.setVisibility(View.VISIBLE);
        txtzubereitung1.setText(tutorialList[0].substring(3));
        txtzubereitung1.setTypeface(displayMedium);

        if (tutorialList.length > 1) {
            zb2.setVisibility(View.VISIBLE);
            txtzubereitung2.setVisibility(View.VISIBLE);
            txtzubereitung2.setText(tutorialList[1].substring(3));
            txtzubereitung2.setTypeface(displayMedium);
        } else {
            zb2.setVisibility(View.GONE);
            zb3.setVisibility(View.GONE);
            zb4.setVisibility(View.GONE);
            zb5.setVisibility(View.GONE);
        }

        if (tutorialList.length > 2) {
            zb3.setVisibility(View.VISIBLE);
            txtzubereitung3.setVisibility(View.VISIBLE);
            txtzubereitung3.setText(tutorialList[2].substring(3));
            txtzubereitung3.setTypeface(displayMedium);
        } else {
            zb3.setVisibility(View.GONE);
            zb4.setVisibility(View.GONE);
            zb5.setVisibility(View.GONE);
        }

        if (tutorialList.length > 3) {
            zb4.setVisibility(View.VISIBLE);
            txtzubereitung4.setVisibility(View.VISIBLE);
            txtzubereitung4.setText(tutorialList[3].substring(3));
            txtzubereitung4.setTypeface(displayMedium);
        } else {
            zb4.setVisibility(View.GONE);
            zb5.setVisibility(View.GONE);
        }

        if (tutorialList.length > 4) {
            zb5.setVisibility(View.VISIBLE);
            txtzubereitung5.setVisibility(View.VISIBLE);
            txtzubereitung5.setText(tutorialList[4].substring(3));
            txtzubereitung5.setTypeface(displayMedium);
        }else {
            zb5.setVisibility(View.GONE);
        }
    }

    public int num;

    public int d = 0;

    @Subscribe
    public void onEvent(EventTest event) {
    }

    @Subscribe
    public void onMessageEvent(EventTest event) {
        initialize(event.suess.get((event.daytime-1)*2));
    }

    public String nams;
    public int idss;
    ArrayList<String> zubereitungs;

    public void getInfo(){

        SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
        String zeit = "";
        if(prefss.getInt("zeit",0)== 1){
            zeit = "morgens";
        }
        if(prefss.getInt("zeit",0)== 2){
            zeit = "mittags";
        }
        if(prefss.getInt("zeit",0)== 3){
            zeit = "mittags";
        }

        if(prefss.getInt("chosencategory",0) == 1){
            category = zeit + "_togo_" + "suess";
        }else{
            category = zeit + "_togo_" + "herzhaft";

        }

        //category = "mittags_togo_suess";
        System.out.println("ergebnis  " + category);

    }


    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    int mStartX,mStartY,mEndX,mEndY,mImageIndex;

    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        System.out.println("geht hier rein");

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :

                mStartX = (int)event.getX();
                mStartY = (int)event.getY();
                return true;

            case (MotionEvent.ACTION_MOVE) :

                mEndX = (int)event.getX();
                mEndY = (int)event.getY();

                if((mEndX - mStartX) > 9) {
                    mImageIndex++;
                    if(mImageIndex > 56 )
                        mImageIndex = 0;

                    m360DegreeImageView.setImageLevel(mImageIndex);

                }
                if((mEndX - mStartX) < -9) {
                    mImageIndex--;
                    if(mImageIndex <0)
                        mImageIndex = 56;

                    m360DegreeImageView.setImageLevel(mImageIndex);

                }
                mStartX = (int)event.getX();
                mStartY = (int)event.getY();
                return true;

            case (MotionEvent.ACTION_UP) :
                mEndX = (int)event.getX();
                mEndY = (int)event.getY();

                return true;

            case (MotionEvent.ACTION_CANCEL) :
                return true;

            case (MotionEvent.ACTION_OUTSIDE) :
                return true;

            default :
                return onTouchEvent(event);
        }

    }

    @Override
    public void onResume() {

        super.onResume();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                    // handle back button
                    vp.setCurrentItem(2);
                    return true;
                }
                return false;
            }
        });
    }
}