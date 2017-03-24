package com.snackit.android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.FetchQuery;
import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
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
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MainActivity extends Fragment {

    private ImageView img_morgens,img_mittags,img_abends;
    private ImageView btn_suess,btn_herzhaft,btn_info,background_grey, btn_bewerten;
    //btn_suess_togo,btn_suess_home,btn_herzhaft_togo,btn_herzhaft_home,
    private TextView txt_week,txt_bewerten,txt_leit;
    private RelativeLayout relBackground;
    private ImageView btn_week;
    CustomPager vp;
    TextView textView2;
    ImageView translucent;

    public ArrayList<Boolean> s1 = new ArrayList<Boolean>();
    public ArrayList<Boolean> s2 = new ArrayList<Boolean>();
    public ArrayList<Boolean> h1 = new ArrayList<Boolean>();
    public ArrayList<Boolean> h2 = new ArrayList<Boolean>();

    boolean vegetarisch,nuss,vegan,lactose;

    Typeface displayBold,displayMedium,displayThin,displayTitling;

    ApplicationSnackit snackit;

    public String doneShit = "";
    public String doneid;


    // newInstance constructor for creating fragment with arguments
    public static MainActivity newInstance(int page, String title) {
        MainActivity fragmentFirst = new MainActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    private float x1,x2;
    static final int MIN_DISTANCE = 50;

    public EventTest eventbus;
    private CDAClient client;
    private ProgressDialog dialog;

    HTextView txt_begruesung;

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);

        client = CDAClient.builder()
                .setSpace(getString(R.string.CDA_space))
                .setToken(getString(R.string.CDA_delivery_token))
                .build();

        dialog = new ProgressDialog(this.getContext());
        dialog.setMessage("Loading Datas...");
        dialog.setIndeterminate(true);


        for(int i = 0; i <= 3; i++){
            s1.add(i,false);
            s2.add(i,false);
            h1.add(i,false);
            h2.add(i,false);
        }

        FabSpeedDial fabSpeedDial = (FabSpeedDial) view.findViewById(R.id.fab_action);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {

                translucent.setVisibility(View.VISIBLE);

                //TODO: Start some activity
                if(menuItem.getTitle().equals("Leitfaden")){
                    Intent intent = new Intent(getActivity(), Leitfaden.class);
                    startActivity(intent);
                    translucent.setVisibility(View.INVISIBLE);
                }
                if(menuItem.getTitle().equals("Profil")){
                    Intent intent = new Intent(getActivity(), ActivityProfil.class);
                    startActivity(intent);
                    translucent.setVisibility(View.INVISIBLE);
                }else{
                    translucent.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });

        translucent = (ImageView)view.findViewById(R.id.translucent);
        translucent.setVisibility(View.INVISIBLE);

        displayBold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Bold.ttf");
        displayMedium = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Medium.ttf");
        displayThin = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Neutra2Display-Light.ttf");

        relBackground = (RelativeLayout)view.findViewById(R.id.relBackground);
        txt_begruesung = (HTextView)view.findViewById(R.id.txt_begruesung);
        img_morgens = (ImageView)view.findViewById(R.id.img_morgens);
        img_abends = (ImageView)view.findViewById(R.id.img_abend);
        img_mittags = (ImageView)view.findViewById(R.id.img_mittag);
        textView2 = (TextView)view.findViewById(R.id.textView2);

        txt_begruesung.setTypeface(displayBold);
        textView2.setTypeface(displayMedium);

        eventbus = new EventTest();

        btn_suess = (ImageView)view.findViewById(R.id.btn_suess);
        btn_herzhaft = (ImageView)view.findViewById(R.id.btn_herzhaft);

        vp =(CustomPager) getActivity().findViewById(R.id.viewPagerMain);

        SharedPreferences prefs = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);


        if(prefs.getInt("firststart",0) == 1){

            FragmentManager fm = getFragmentManager();
            MyDialogFragment dialogFragment = new MyDialogFragment ();
            dialogFragment.show(fm, "Hi :)");
            prefs.edit().putInt("firststart",2).apply();

        }

        final SharedPreferences pref = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
        vegetarisch = pref.getBoolean("vegetarisch",false);
        nuss = pref.getBoolean("nuss",false);
        vegan = pref.getBoolean("vegan",false);
        lactose = pref.getBoolean("lactose",false);

        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);


        txt_begruesung.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch(event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (Math.abs(deltaX) > MIN_DISTANCE)
                        {

                            // Left to Right swipe action
                            if (x2 > x1)
                            {
                                if(txt_begruesung.getText().toString().equals("FRÜHSTÜCK")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("LUNCH");
                                    System.out.println("GEH RON 1");
                                    eventbus.daytime = 2;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",2).apply();


                                    break;
                                }
                                if(txt_begruesung.getText().toString().equals("LUNCH")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("DINNER");
                                    System.out.println("GEH RON 2");
                                    eventbus.daytime = 3;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",3).apply();
                                    break;

                                }
                                if(txt_begruesung.getText().toString().equals("DINNER")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("FRÜHSTÜCK");
                                    System.out.println("GEH RON 3");
                                    eventbus.daytime = 1;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",1).apply();
                                    break;

                                }
                            }

                            // Right to left swipe action
                            else
                            {
                                if(txt_begruesung.getText().toString().equals("FRÜHSTÜCK")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("DINNER");
                                    System.out.println("GEH RON 4");
                                    eventbus.daytime = 3;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",3).apply();
                                    break;

                                }
                                if(txt_begruesung.getText().toString().equals("LUNCH")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("FRÜHSTÜCK");
                                    System.out.println("GEH RON 5");
                                    eventbus.daytime =1;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",1).apply();
                                    break;

                                }
                                if(txt_begruesung.getText().toString().equals("DINNER")){
                                    txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                                    txt_begruesung.animateText("LUNCH");
                                    System.out.println("GEH RON 6");
                                    eventbus.daytime = 2;
                                    EventBus.getDefault().post(eventbus);

                                    SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                                    prefss.edit().putInt("zeit",2).apply();
                                    break;

                                }
                            }
                        }
                        break;
                }
                return true;
            }
        });

        if(hour < 11){
            eventbus.daytime = 1;
            EventBus.getDefault().post(eventbus);

            txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
            txt_begruesung.animateText("FRÜHSTÜCK");
            img_morgens.setBackgroundResource(R.drawable.morgens_selected);
            img_mittags.setBackgroundResource(R.drawable.mittags);
            img_abends.setBackgroundResource(R.drawable.abends);
            Toast.makeText(getActivity().getApplicationContext(),"morgens",Toast.LENGTH_LONG);

            SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
            prefss.edit().putInt("zeit",1).apply();
        }
        if(hour >= 11 && hour < 16){

            eventbus.daytime = 2;
            EventBus.getDefault().post(eventbus);

            txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
            txt_begruesung.animateText("LUNCH");
            img_mittags.setBackgroundResource(R.drawable.mittags_selected);
            img_morgens.setBackgroundResource(R.drawable.morgens);
            img_abends.setBackgroundResource(R.drawable.abends);
            Toast.makeText(getActivity().getApplicationContext(),"mittag",Toast.LENGTH_LONG);

            SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
            prefss.edit().putInt("zeit",2).apply();
        }
        if(hour >= 16){

            eventbus.daytime = 3;
            EventBus.getDefault().post(eventbus);

            txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
            txt_begruesung.animateText("DINNER");
            img_abends.setBackgroundResource(R.drawable.abends_selected);
            img_mittags.setBackgroundResource(R.drawable.mittags);
            img_morgens.setBackgroundResource(R.drawable.morgens);
            Toast.makeText(getActivity().getApplicationContext(),"abend",Toast.LENGTH_LONG);

            SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
            prefss.edit().putInt("zeit",3).apply();
        }


        img_morgens.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                eventbus.daytime = 1;
                EventBus.getDefault().post(eventbus);

                img_morgens.setBackgroundResource(R.drawable.morgens_selected);
                img_mittags.setBackgroundResource(R.drawable.mittags);
                img_abends.setBackgroundResource(R.drawable.abends);

                AlphaAnimation fadeIn = new AlphaAnimation(1.0f , 0.0f ) ;
                AlphaAnimation fadeOut = new AlphaAnimation( 0.0f , 1.0f ) ;

                txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                txt_begruesung.animateText("FRÜHSTÜCK");

                fadeIn.setDuration(200);
                fadeIn.setFillAfter(true);
                fadeOut.setDuration(200);
                fadeOut.setFillAfter(true);
                fadeOut.setStartOffset(300);

                SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                prefss.edit().putInt("zeit",1).apply();
            }
        });

        img_mittags.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                eventbus.daytime = 2;
                EventBus.getDefault().post(eventbus);

                img_morgens.setBackgroundResource(R.drawable.morgens);
                img_mittags.setBackgroundResource(R.drawable.mittags_selected);
                img_abends.setBackgroundResource(R.drawable.abends);

                AlphaAnimation fadeIn = new AlphaAnimation(1.0f , 0.0f ) ;
                AlphaAnimation fadeOut = new AlphaAnimation( 0.0f , 1.0f ) ;

                txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                txt_begruesung.animateText("LUNCH ");

                fadeIn.setDuration(200);
                fadeIn.setFillAfter(true);
                fadeOut.setDuration(200);
                fadeOut.setFillAfter(true);
                fadeOut.setStartOffset(300);

                SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                prefss.edit().putInt("zeit",2).apply();
            }
        });

        img_abends.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                eventbus.daytime = 3;
                EventBus.getDefault().post(eventbus);

                img_morgens.setBackgroundResource(R.drawable.morgens);
                img_mittags.setBackgroundResource(R.drawable.mittags);
                img_abends.setBackgroundResource(R.drawable.abends_selected);

                AlphaAnimation fadeIn = new AlphaAnimation(1.0f , 0.0f ) ;
                AlphaAnimation fadeOut = new AlphaAnimation( 0.0f , 1.0f ) ;

                txt_begruesung.setAnimateType(HTextViewType.EVAPORATE);
                txt_begruesung.animateText("DINNER");
                //txt_begruesung.setText("GUTEN ABEND");

                fadeIn.setDuration(200);
                fadeIn.setFillAfter(true);
                fadeOut.setDuration(200);
                fadeOut.setFillAfter(true);
                fadeOut.setStartOffset(300);

                SharedPreferences prefss = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
                prefss.edit().putInt("zeit",3).apply();
            }
        });


        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final float width = display.getWidth();

        btn_suess.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int iAction=event.getAction();

                if(iAction==0){
                    btn_suess.setBackground(getResources().getDrawable(R.drawable.btn_suess_clicked_zwei));
                }
                else{
                    btn_suess.setBackground(getResources().getDrawable(R.drawable.btn_suess_clicked));
                }
                return false;
            }
        });


        btn_herzhaft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int iAction=event.getAction();

                if(iAction==0){
                    btn_herzhaft.setBackground(getResources().getDrawable(R.drawable.btn_herzhaft_clicked_zwei));
                }
                else{
                    btn_herzhaft.setBackground(getResources().getDrawable(R.drawable.btn_herzhaft_clicked));
                }
                return false;
            }
        });


        btn_suess.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(checkIf()){
                    vp.setAllowedSwipeDirection(CustomPager.SwipeDirection.all);
                    vp.setCurrentItem(3);

                }else{
                    EventBus.getDefault().post(eventbus);
                    vp.setAllowedSwipeDirection(CustomPager.SwipeDirection.all);
                    vp.setCurrentItem(3);
                }
            }
        });

        btn_herzhaft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    if(checkIf()){
                        EventBus.getDefault().post(eventbus);

                        vp.setAllowedSwipeDirection(CustomPager.SwipeDirection.all);
                        vp.setCurrentItem(0);

                    }else {

                        vp.setAllowedSwipeDirection(CustomPager.SwipeDirection.all);
                        vp.setCurrentItem(0);
                    }

            }
        });

        doneid = "";
//        if (checkDay() == false || eventbus.suess.size() == 0) {
//            new MyAsyncTask().execute("");
//        }
        return view;
    }


    public void getRezeptePicked(int pick1,int pick2, int tageszeit){

    }

    private void getRandomEntry(String tags, Boolean isSuess, int day_time) {
        if(isSuess) {
            tags += ",sweet";
//            query = query.where("fields.tags[in]", "sweet");
        }else {
            tags += ",hearty";
//            query = query.where("fields.tags[in]", "hearty");
        }

        if(day_time == 0) {
            tags += ",breakfast";
//            query = query.where("fields.tags[in]", "breakfast");
        }else if(day_time == 1) {
            tags += ",lunch";
//            query = query.where("fields.tags[in]", "lunch");
        }else {
            tags += ",dinner";
//            query = query.where("fields.tags[in]", "dinner");
        }
        FetchQuery<CDAEntry> query = client.fetch(CDAEntry.class).where("content_type", "recipe");
        query = query.where("fields.tags[all]", tags);
        CDAArray entries = query.all();

        int entries_num = entries.total();
        for(int i=0; i<2; i++) {
            final int r = new Random().nextInt(entries_num);
            CDAEntry random_entry = (CDAEntry) entries.items().get(r);
            if(isSuess) {
                eventbus.suess.add(random_entry);
            }else {
                eventbus.herzhaft.add(random_entry);
            }
        }
    }

    public static class MyDialogFragment extends DialogFragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.popup, container, false);
            getDialog().setTitle("Hi :)");


            Button dismiss = (Button) rootView.findViewById(R.id.dismiss);
            dismiss.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            return rootView;
        }
    }


    public Boolean checkDay(){
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_WEEK);

        SharedPreferences prefs = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);

        if(prefs.getInt("currentDay",0)!=day){
            prefs.edit().putInt("currentDay",day).apply();
            return false;
        }
        return true;
    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this.getContext());
            dialog.setMessage("Loading Datas...");
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            final SharedPreferences pref = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);
            vegetarisch = pref.getBoolean("vegetarisch",false);
            nuss = pref.getBoolean("nuss",false);
            vegan = pref.getBoolean("vegan",false);
            lactose = pref.getBoolean("lactose",false);
            FetchQuery<CDAEntry> query = client.fetch(CDAEntry.class).where("content_type", "recipe");
            String queryParams = "";

            if(vegetarisch) {
                if (queryParams == ""){
                    queryParams = "vegetarian";
                } else {
                    queryParams += ",vegetarian";
                }
//                query = query.where("fields.tags[in]", "vegetarian");
//            }else {
//                query = query.where("fields.tags[nin]", "vegetarian");
            }

            if(vegan) {
                if (queryParams == ""){
                    queryParams = "vegan";
                } else {
                    queryParams += ",vegan";
                }
//                queryParams += ", vegan";
//                query = query.where("fields.tags[in]", "vegan");
//            }else {
//                query = query.where("fields.tags[nin]", "vegan");
            }

            if(nuss) {
                if (queryParams == ""){
                    queryParams = "nuts";
                } else {
                    queryParams += ",nuts";
                }
//                queryParams += ", nuts";
//                query = query.where("fields.tags[in]", "nuts");
//            }else {
//                query = query.where("fields.tags[nin]", "nuts");
            }

            if(lactose) {
                if (queryParams == ""){
                    queryParams = "Lactose free";
                } else {
                    queryParams += ",Lactose free";
                }
//                queryParams += ", Lactose free";
//                query = query.where("fields.tags[in]", "Lactose free");
//            } else {
//                query = query.where("fields.tags[nin]", "Lactose free");
            }
//            query = query.where("fields.tags[in]", queryParams);
            getRandomEntry(queryParams, true, 0);
            getRandomEntry(queryParams, true, 1);
            getRandomEntry(queryParams, true, 2);
            getRandomEntry(queryParams, false, 0);
            getRandomEntry(queryParams, false, 1);
            getRandomEntry(queryParams, false, 2);

            return result;
        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
        }
    }


    public String heut;

public void safeClicked(int id){

    //clicked Süß etc die werden gespeichert in yesterday...
    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_WEEK);

    SharedPreferences prefs = getActivity().getSharedPreferences("snackit", Context.MODE_PRIVATE);

    if(prefs.getInt("currentDay",0)==day){
        doneShit += id + ";";
        prefs.edit().putString("yesterday",doneShit).apply();
        doneShit = prefs.getString("yesterday",doneShit);
        System.out.println("GEHE HIER REIN DAYOTHERDAY " + doneShit) ;

    }else{

    }




    System.out.println("doneShit :" + doneShit);
}



    public void shownMeals(int id){
        doneShit += id + ";";
    }



    @Subscribe
    public void onEvent(EventTest event){
        System.out.println("EVENTBUSS MAIN " + event.suess_Name.get(0));
    }

    @Subscribe
    public void onMessageEvent(EventTest event) {
        System.out.println("EVENTBUSS MAIN " + event.suess_Name.get(0));

    }


    public boolean checkIf(){

        for(int i = 0; i <= 12;i++){
            if(s1.get(i)==false){
                return false;

            }
            if(s2.get(i)==false){
                return false;

            }
            if(h1.get(i)==false){
                return false;

            }
            if(h2.get(i)==false){
                return false;
            }
        }

        return true;

    }



    public void onStart() {
        super.onStart();
        if (checkDay() == false || eventbus.suess.size() == 0) {
            new MyAsyncTask().execute("");
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void onDestroy(){
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void clearArrays(){

        eventbus.suess_ID.clear();
        eventbus.suess_Name.clear();
        eventbus.suess_Bild.clear();
        eventbus.suess_Zutat1.clear();
        eventbus.suess_Zutat1Anzahl.clear();
        eventbus.suess_Zutat1Bild.clear();
        eventbus.suess_Zutat2.clear();
        eventbus.suess_Zutat2Anzahl.clear();
        eventbus.suess_Zutat2Bild.clear();
        eventbus.suess_Zutat3.clear();
        eventbus.suess_Zutat3Anzahl.clear();
        eventbus.suess_Zutat3Bild.clear();
        eventbus.suess_Zutat4.clear();
        eventbus.suess_Zutat4Anzahl.clear();
        eventbus.suess_Zutat4Bild.clear();
        eventbus.suess_Zutat5.clear();
        eventbus.suess_Zutat5Anzahl.clear();
        eventbus.suess_Zutat5Bild.clear();
        eventbus.suess_Zubereitung.clear();


        eventbus.suess_zwei_ID.clear();
        eventbus.suess_zwei_Name.clear();
        eventbus.suess_zwei_Bild.clear();
        eventbus.suess_zwei_Zutat1.clear();
        eventbus.suess_zwei_Zutat1Anzahl.clear();
        eventbus.suess_zwei_Zutat1Bild.clear();
        eventbus.suess_zwei_Zutat2.clear();
        eventbus.suess_zwei_Zutat2Anzahl.clear();
        eventbus.suess_zwei_Zutat2Bild.clear();
        eventbus.suess_zwei_Zutat3.clear();
        eventbus.suess_zwei_Zutat3Anzahl.clear();
        eventbus.suess_zwei_Zutat3Bild.clear();
        eventbus.suess_zwei_Zutat4.clear();
        eventbus.suess_zwei_Zutat4Anzahl.clear();
        eventbus.suess_zwei_Zutat4Bild.clear();
        eventbus.suess_zwei_Zutat5.clear();
        eventbus.suess_zwei_Zutat5Anzahl.clear();
        eventbus.suess_zwei_Zutat5Bild.clear();
        eventbus.suess_zwei_Zubereitung.clear();


        eventbus.herzhaft_ID.clear();
        eventbus.herzhaft_Name.clear();
        eventbus.herzhaft_Bild.clear();
        eventbus.herzhaft_Zutat1.clear();
        eventbus.herzhaft_Zutat1Anzahl.clear();
        eventbus.herzhaft_Zutat1Bild.clear();
        eventbus.herzhaft_Zutat2.clear();
        eventbus.herzhaft_Zutat2Anzahl.clear();
        eventbus.herzhaft_Zutat2Bild.clear();
        eventbus.herzhaft_Zutat3.clear();
        eventbus.herzhaft_Zutat3Anzahl.clear();
        eventbus.herzhaft_Zutat3Bild.clear();
        eventbus.herzhaft_Zutat4.clear();
        eventbus.herzhaft_Zutat4Anzahl.clear();
        eventbus.herzhaft_Zutat4Bild.clear();
        eventbus.herzhaft_Zutat5.clear();
        eventbus.herzhaft_Zutat5Anzahl.clear();
        eventbus.herzhaft_Zutat5Bild.clear();
        eventbus.herzhaft_Zubereitung.clear();

        eventbus.herzhaft_zwei_ID.clear();
        eventbus.herzhaft_zwei_Name.clear();
        eventbus.herzhaft_zwei_Bild.clear();
        eventbus.herzhaft_zwei_Zutat1.clear();
        eventbus.herzhaft_zwei_Zutat1Anzahl.clear();
        eventbus.herzhaft_zwei_Zutat1Bild.clear();
        eventbus.herzhaft_zwei_Zutat2.clear();
        eventbus.herzhaft_zwei_Zutat2Anzahl.clear();
        eventbus.herzhaft_zwei_Zutat2Bild.clear();
        eventbus.herzhaft_zwei_Zutat3.clear();
        eventbus.herzhaft_zwei_Zutat3Anzahl.clear();
        eventbus.herzhaft_zwei_Zutat3Bild.clear();
        eventbus.herzhaft_zwei_Zutat4.clear();
        eventbus.herzhaft_zwei_Zutat4Anzahl.clear();
        eventbus.herzhaft_zwei_Zutat4Bild.clear();
        eventbus.herzhaft_zwei_Zutat5.clear();
        eventbus.herzhaft_zwei_Zutat5Anzahl.clear();
        eventbus.herzhaft_zwei_Zutat5Bild.clear();
        eventbus.herzhaft_zwei_Zubereitung.clear();
    }

}




