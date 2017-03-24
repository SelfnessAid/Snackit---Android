package com.snackit.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.contentful.java.cda.CDAClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
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
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class StartActivity extends AppCompatActivity {


    Timer timer;
    TimerTask timerTask;
    final Handler handler = new Handler();
    public float timeCounter;
    private String UI;
//    ApplicationSnackit snackit;

    boolean vegetarisch,nuss,vegan,lactose;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
//        snackit = (ApplicationSnackit) getApplication();
        startTimer();

        SharedPreferences prefss = getSharedPreferences("snackit", MODE_PRIVATE);
        UI = prefss.getString("uid","");


        EventBus.getDefault().register(this);


        if(!UI.equals("")){

            prefss.edit().putInt("firststart",1).apply();

        }else{


        }


        final SharedPreferences pref = getSharedPreferences("snackit", Context.MODE_PRIVATE);
        vegetarisch = pref.getBoolean("vegetarisch",false);
        nuss = pref.getBoolean("nuss",false);
        vegan = pref.getBoolean("vegan",false);
        lactose = pref.getBoolean("lactose",false);

        //Hier noch ein paar kommentare etc..
        //new MyAsyncTask().execute("");

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventTest event) {
    }


    /*LOOOOP */
    protected void onResume() {
        super.onResume();

        //onResume we start our timer so it can start when the app comes from the background
        startTimer();
    }

    public void startTimer() {

        timer = new Timer();
        initializeTimerTask();


        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 500, 100); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {

                        timeCounter++;
                        if (timeCounter == 22) {
                            Intent intent = new Intent(StartActivity.this, FragmentPagerMain.class);
                            startActivity(intent);
                            finish();
                        }


                    }
                });
            }
        };
    }


    class MyAsyncTask extends AsyncTask<String, String, String> {

        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {

            String url_select = "http://www.marketingsfinest.com/snackit/getRezepte2.php";

            //ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            SharedPreferences pref = getSharedPreferences("snackit", Context.MODE_PRIVATE);

            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_WEEK);

            List<NameValuePair> param = new ArrayList<NameValuePair>();

            if(vegetarisch){
                param.add(new BasicNameValuePair("vegetarisch", "1"));
            }else{
                param.add(new BasicNameValuePair("vegetarisch", "0"));
            }
            if(vegan){
                param.add(new BasicNameValuePair("vegetarisch", "1"));
            }else{
                param.add(new BasicNameValuePair("vegetarisch", "0"));
            }
            if(nuss){
                param.add(new BasicNameValuePair("nuss", "1"));
            }else{
                param.add(new BasicNameValuePair("nuss", "0"));
            }
            if(lactose){
                param.add(new BasicNameValuePair("lactose", "1"));
            }else{
                param.add(new BasicNameValuePair("lactose", "0"));
            }

            try {
                // Set up HTTP post

                // HttpClient is more then less deprecated. Need to change to URLConnection
                HttpClient httpClient = new DefaultHttpClient();

                HttpPost httpPost = new HttpPost(url_select);
                httpPost.setEntity(new UrlEncodedFormEntity(param));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();

                // Read content & Log
                inputStream = httpEntity.getContent();
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            } catch (ClientProtocolException e2) {
                Log.e("ClientProtocolException", e2.toString());
                e2.printStackTrace();
            } catch (IllegalStateException e3) {
                Log.e("IllegalStateException", e3.toString());
                e3.printStackTrace();
            } catch (IOException e4) {
                Log.e("IOException", e4.toString());
                e4.printStackTrace();
            }
            // Convert response to string using String Builder
            try {
                BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
                StringBuilder sBuilder = new StringBuilder();

                String line = null;
                while ((line = bReader.readLine()) != null) {
                    sBuilder.append(line + "\n");
                }

                inputStream.close();
                result = sBuilder.toString();

            } catch (Exception e) {
            }
            return result;
        }

        protected void onPostExecute(String result) {
            //parse JSON data

            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray;

                EventTest eventbus = new EventTest();


                jsonArray = jsonObject.getJSONArray("mittags_togo_suess");
                ArrayList<String> tageszeiten = new ArrayList<String>();
                tageszeiten.add("morgens_suess");
                tageszeiten.add("mittags_suess");
                tageszeiten.add("abends_suess");

                JSONObject jObject;

                for(int i = 0; i < tageszeiten.size(); i++) {


                    jsonArray = jsonObject.getJSONArray(tageszeiten.get(i));
                    jObject = jsonArray.getJSONObject(0);

                    System.out.println("RESUUULLTSIZE222 " + result.length() + "   " + i);
                    System.out.println("RJOBN " + jObject.toString());


                    eventbus.suess_ID.add(i, jObject.getInt("ID"));
                    eventbus.suess_Name.add(i, jObject.getString("Name"));
                    eventbus.suess_Bild.add(i, jObject.getString("Bild"));
                    eventbus.suess_Zutat1.add(i, jObject.getString("Zutat1"));
                    eventbus.suess_Zutat1Anzahl.add(i, jObject.getString("Zutat1Anzahl"));
                    eventbus.suess_Zutat1Bild.add(i, jObject.getString("Zutat1Bild"));
                    eventbus.suess_Zutat2.add(i, jObject.getString("Zutat2"));
                    eventbus.suess_Zutat2Anzahl.add(i, jObject.getString("Zutat2Anzahl"));
                    eventbus.suess_Zutat2Bild.add(i, jObject.getString("Zutat2Bild"));
                    eventbus.suess_Zutat3.add(i, jObject.getString("Zutat3"));
                    eventbus.suess_Zutat3Anzahl.add(i, jObject.getString("Zutat3Anzahl"));
                    eventbus.suess_Zutat3Bild.add(i, jObject.getString("Zutat3Bild"));
                    eventbus.suess_Zutat4.add(i, jObject.getString("Zutat4"));
                    eventbus.suess_Zutat4Anzahl.add(i, jObject.getString("Zutat4Anzahl"));
                    eventbus.suess_Zutat4Bild.add(i, jObject.getString("Zutat4Bild"));
                    eventbus.suess_Zutat5.add(i, jObject.getString("Zutat5"));
                    eventbus.suess_Zutat5Anzahl.add(i, jObject.getString("Zutat5Anzahl"));
                    eventbus.suess_Zutat5Bild.add(i, jObject.getString("Zutat5Bild"));
                    eventbus.suess_Zubereitung.add(i, jObject.getString("Zubereitung"));

                }


                for(int i = 0; i < tageszeiten.size(); i++){


                    jsonArray = jsonObject.getJSONArray(tageszeiten.get(i));

                    jObject = jsonArray.getJSONObject(1);

                    System.out.println("GEHE REEIEENE 2 " + tageszeiten.size());
                    System.out.println("RJOBN " + jObject.toString());

                    eventbus.suess_zwei_ID.add(i, jObject.getInt("ID"));
                    eventbus.suess_zwei_Name.add(i, jObject.getString("Name"));
                    eventbus.suess_zwei_Bild.add(i, jObject.getString("Bild"));
                    eventbus.suess_zwei_Zutat1.add(i, jObject.getString("Zutat1"));
                    eventbus.suess_zwei_Zutat1Anzahl.add(i, jObject.getString("Zutat1Anzahl"));
                    eventbus.suess_zwei_Zutat1Bild.add(i, jObject.getString("Zutat1Bild"));
                    eventbus.suess_zwei_Zutat2.add(i, jObject.getString("Zutat2"));
                    eventbus.suess_zwei_Zutat2Anzahl.add(i, jObject.getString("Zutat2Anzahl"));
                    eventbus.suess_zwei_Zutat2Bild.add(i, jObject.getString("Zutat2Bild"));
                    eventbus.suess_zwei_Zutat3.add(i, jObject.getString("Zutat3"));
                    eventbus.suess_zwei_Zutat3Anzahl.add(i, jObject.getString("Zutat3Anzahl"));
                    eventbus.suess_zwei_Zutat3Bild.add(i, jObject.getString("Zutat3Bild"));
                    eventbus.suess_zwei_Zutat4.add(i, jObject.getString("Zutat4"));
                    eventbus.suess_zwei_Zutat4Anzahl.add(i, jObject.getString("Zutat4Anzahl"));
                    eventbus.suess_zwei_Zutat4Bild.add(i, jObject.getString("Zutat4Bild"));
                    eventbus.suess_zwei_Zutat5.add(i, jObject.getString("Zutat5"));
                    eventbus.suess_zwei_Zutat5Anzahl.add(i, jObject.getString("Zutat5Anzahl"));
                    eventbus.suess_zwei_Zutat5Bild.add(i, jObject.getString("Zutat5Bild"));
                    eventbus.suess_zwei_Zubereitung.add(i, jObject.getString("Zubereitung"));
                }

                ArrayList<String> tageszeitenzwei = new ArrayList<String>();
                tageszeitenzwei.add("morgens_herzhaft");
                tageszeitenzwei.add("mittags_herzhaft");
                tageszeitenzwei.add("abends_herzhaft");

                for(int i = 0; i < tageszeitenzwei.size(); i++){


                    jsonArray = jsonObject.getJSONArray(tageszeitenzwei.get(i));
                    jObject = jsonArray.getJSONObject(0);

                    System.out.println("GEHE REEIEENE 3 " + tageszeitenzwei.size());
                    System.out.println("RJOBN " + jObject.toString());


                    eventbus.herzhaft_ID.add(i, jObject.getInt("ID"));
                    eventbus.herzhaft_Name.add(i, jObject.getString("Name"));
                    eventbus.herzhaft_Bild.add(i, jObject.getString("Bild"));
                    eventbus.herzhaft_Zutat1.add(i, jObject.getString("Zutat1"));
                    eventbus.herzhaft_Zutat1Anzahl.add(i, jObject.getString("Zutat1Anzahl"));
                    eventbus.herzhaft_Zutat1Bild.add(i, jObject.getString("Zutat1Bild"));
                    eventbus.herzhaft_Zutat2.add(i, jObject.getString("Zutat2"));
                    eventbus.herzhaft_Zutat2Anzahl.add(i, jObject.getString("Zutat2Anzahl"));
                    eventbus.herzhaft_Zutat2Bild.add(i, jObject.getString("Zutat2Bild"));
                    eventbus.herzhaft_Zutat3.add(i, jObject.getString("Zutat3"));
                    eventbus.herzhaft_Zutat3Anzahl.add(i, jObject.getString("Zutat3Anzahl"));
                    eventbus.herzhaft_Zutat3Bild.add(i, jObject.getString("Zutat3Bild"));
                    eventbus.herzhaft_Zutat4.add(i, jObject.getString("Zutat4"));
                    eventbus.herzhaft_Zutat4Anzahl.add(i, jObject.getString("Zutat4Anzahl"));
                    eventbus.herzhaft_Zutat4Bild.add(i, jObject.getString("Zutat4Bild"));
                    eventbus.herzhaft_Zutat5.add(i, jObject.getString("Zutat5"));
                    eventbus.herzhaft_Zutat5Anzahl.add(i, jObject.getString("Zutat5Anzahl"));
                    eventbus.herzhaft_Zutat5Bild.add(i, jObject.getString("Zutat5Bild"));
                    eventbus.herzhaft_Zubereitung.add(i, jObject.getString("Zubereitung"));
                }

                for(int i = 0; i < tageszeitenzwei.size(); i++){


                    jsonArray = jsonObject.getJSONArray(tageszeitenzwei.get(i));
                    jObject = jsonArray.getJSONObject(1);

                    System.out.println("GEHE REEIEENE 4 " + tageszeitenzwei.size());

                    eventbus.herzhaft_zwei_ID.add(i, jObject.getInt("ID"));
                    eventbus.herzhaft_zwei_Name.add(i, jObject.getString("Name"));
                    eventbus.herzhaft_zwei_Bild.add(i, jObject.getString("Bild"));
                    eventbus.herzhaft_zwei_Zutat1.add(i, jObject.getString("Zutat1"));
                    eventbus.herzhaft_zwei_Zutat1Anzahl.add(i, jObject.getString("Zutat1Anzahl"));
                    eventbus.herzhaft_zwei_Zutat1Bild.add(i, jObject.getString("Zutat1Bild"));
                    eventbus.herzhaft_zwei_Zutat2.add(i, jObject.getString("Zutat2"));
                    eventbus.herzhaft_zwei_Zutat2Anzahl.add(i, jObject.getString("Zutat2Anzahl"));
                    eventbus.herzhaft_zwei_Zutat2Bild.add(i, jObject.getString("Zutat2Bild"));
                    eventbus.herzhaft_zwei_Zutat3.add(i, jObject.getString("Zutat3"));
                    eventbus.herzhaft_zwei_Zutat3Anzahl.add(i, jObject.getString("Zutat3Anzahl"));
                    eventbus.herzhaft_zwei_Zutat3Bild.add(i, jObject.getString("Zutat3Bild"));
                    eventbus.herzhaft_zwei_Zutat4.add(i, jObject.getString("Zutat4"));
                    eventbus.herzhaft_zwei_Zutat4Anzahl.add(i, jObject.getString("Zutat4Anzahl"));
                    eventbus.herzhaft_zwei_Zutat4Bild.add(i, jObject.getString("Zutat4Bild"));
                    eventbus.herzhaft_zwei_Zutat5.add(i, jObject.getString("Zutat5"));
                    eventbus.herzhaft_zwei_Zutat5Anzahl.add(i, jObject.getString("Zutat5Anzahl"));
                    eventbus.herzhaft_zwei_Zutat5Bild.add(i, jObject.getString("Zutat5Bild"));
                    eventbus.herzhaft_zwei_Zubereitung.add(i, jObject.getString("Zubereitung"));

                }

                EventBus.getDefault().post(eventbus);


            } catch (JSONException e) {
            } // catch (JSONException e)



            Intent intent = new Intent(StartActivity.this, FragmentPagerMain.class);
            startActivity(intent);
            finish();
            EventBus.getDefault().unregister(this);
        }

    }


}







