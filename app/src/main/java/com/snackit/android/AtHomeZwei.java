package com.snackit.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.contentful.java.cda.CDAAsset;
import com.contentful.java.cda.CDAEntry;
import com.emilsjolander.components.StickyScrollViewItems.StickyScrollView;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;


public class AtHomeZwei extends Fragment {

    private String title;
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
    TextView txt_name, txt_zutat1, txt_zutat2, txt_zutat3,txt_zutat4,txt_zutat5, txt_zutat1anzahl,txt_zutat2anzahl, txt_zutat3anzahl,txt_zutat4anzahl,txt_zutat5anzahl, txtzubereitung1,txtzubereitung2,txtzubereitung3,txtzubereitung4,txtzubereitung5;

    ArrayList<Integer> ID = new ArrayList<Integer>();
    ArrayList<String> Name = new ArrayList<String>();

    String category = "morgens_togo_suess";

    TextView txt_zutat1anzahl2,txt_zutat2anzahl2,txt_zutat3anzahl2,txt_zutat4anzahl2,txt_zutat5anzahl2;


    RelativeLayout rezept_content;


    ImageView check, check2, check3,check4,check5;

    boolean vegetarisch,nuss,vegan,lactose;

    LinearLayout l1,l2,l3,l4,l5;
    LinearLayout l_zubereitung, l_like, l_sogehts;
    ImageView zb1,zb2,zb3,zb4,zb5;

    Typeface displayBold,displayMedium,displayThin,displayTitling;
    TextView textView5,textView6,textlike;

    private boolean cli = false;

    ImageView btnBackTop, btnOder,btnEntweder,btnBackTop2;
    CustomPager vp;


    //ScrollView scrollView;
    StickyScrollView scrollView;
    LinearLayout untere_leiste;

    ApplicationSnackit snackit;


    // newInstance constructor for creating fragment with arguments
    public static AtHomeZwei newInstance(int page, String title) {
        AtHomeZwei fragmentFirst = new AtHomeZwei();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(AtHomeZwei.this).attach(AtHomeZwei.this).commit();
    }
    String[] re;
    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_go_eins_test_two, container, false);

        snackit = (ApplicationSnackit) getActivity().getApplication();

        re = new String[2];

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

        btnBackTop = (ImageView) view.findViewById(R.id.btnBackTop);
        btnBackTop2 = (ImageView) view.findViewById(R.id.btnBackBottom);
        btnOder = (ImageView) view.findViewById(R.id.btnOder);
        btnEntweder = (ImageView) view.findViewById(R.id.btnEntweder);
        btnBackTop2.setVisibility(View.GONE);
        btnEntweder.setVisibility(View.GONE);

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

        vp =(CustomPager) getActivity().findViewById(R.id.viewPagerMain);
        btnOder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                vp.setCurrentItem(1);
            }
        });

        displayBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Bold.ttf");
        displayMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Medium.ttf");
        displayThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Light.ttf");

        textView5 = (TextView) view.findViewById(R.id.textView5);
        textView5.setTypeface(displayMedium);
        textView6 = (TextView)view.findViewById(R.id.textView6);
        textView6.setTypeface(displayMedium);
        textlike = (TextView) view.findViewById(R.id.textlike);
        textlike.setTypeface(displayMedium);

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

        l_like = (LinearLayout)view.findViewById(R.id.l_like);
        l_zubereitung = (LinearLayout)view.findViewById(R.id.l_zubereitung);
        l_sogehts = (LinearLayout)view.findViewById(R.id.l_sogehts);

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

//        btn_like.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
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
////                    prefss.edit().putString("snackweeklike" + prefss.getInt("counter",0),"" + day + ";" + id  + ";" + name + ";" + d + ";").apply();
//                    btn_like.setBackgroundResource(R.drawable.btn_selecte);
//
//                    counter = counter +1;
//                    prefss.edit().putInt("counter",counter).apply();
//                    cli = true;
//                }else{
//                    btn_like.setBackgroundResource(R.drawable.btn_check);
//                    cli= false;
//                }
//            }
//        });

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
//        if (snackit.herzhaft.size() == 0)
//            return;
//        CDAEntry entry = snackit.herzhaft.get(0);

        // Set Title of Hearty
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
        }

        if (tutorialList.length > 2) {
            zb3.setVisibility(View.VISIBLE);
            txtzubereitung3.setVisibility(View.VISIBLE);
            txtzubereitung3.setText(tutorialList[2].substring(3));
            txtzubereitung3.setTypeface(displayMedium);
        }

        if (tutorialList.length > 3) {
            zb4.setVisibility(View.VISIBLE);
            txtzubereitung4.setVisibility(View.VISIBLE);
            txtzubereitung4.setText(tutorialList[3].substring(3));
            txtzubereitung4.setTypeface(displayMedium);
        }

        if (tutorialList.length > 4) {
            zb5.setVisibility(View.VISIBLE);
            txtzubereitung5.setVisibility(View.VISIBLE);
            txtzubereitung5.setText(tutorialList[4].substring(3));
            txtzubereitung5.setTypeface(displayMedium);
        }
    }

    public int num;

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

        //category ="mittags_togo_suess";
        System.out.println("ergebnis  " + category);

    }

    public void showHide(LinearLayout l1, LinearLayout l2, String a){
        l1.setBackgroundColor(Color.parseColor(a));
        l2.setBackgroundColor(Color.parseColor(a));
        l1.invalidate();
        l2.invalidate();
    }

    @Subscribe
    public void onEvent(EventTest event) {

    }

    public int d = 0;
    @Subscribe
    public void onMessageEvent(EventTest event){
        initialize(event.herzhaft.get((event.daytime-1)*2));
    }

    public void onStart() {
        super.onStart();
//        initialize();
        EventBus.getDefault().register(this);
    }


    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
