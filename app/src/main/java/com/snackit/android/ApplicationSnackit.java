package com.snackit.android;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.contentful.java.cda.CDAArray;
import com.contentful.java.cda.CDAClient;
import com.contentful.java.cda.CDAEntry;
import com.contentful.java.cda.FetchQuery;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Schmänd on 20.11.2016.
 */
public class ApplicationSnackit extends Application {


    public ArrayList<Integer> suess_ID =  new ArrayList<Integer>();
    public ArrayList<String> suess_Name = new ArrayList<String>();
    public ArrayList<String> suess_Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zutat1 = new ArrayList<String>();
    public ArrayList<String> suess_Zutat1Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_Zutat1Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zutat2 = new ArrayList<String>();
    public ArrayList<String> suess_Zutat2Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_Zutat2Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zutat3 = new ArrayList<String>();
    public ArrayList<String> suess_Zutat3Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_Zutat3Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zutat4 = new ArrayList<String>();
    public ArrayList<String> suess_Zutat4Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_Zutat4Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zutat5 = new ArrayList<String>();
    public ArrayList<String> suess_Zutat5Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_Zutat5Bild = new ArrayList<String>();
    public ArrayList<String> suess_Zubereitung = new ArrayList<String>();



    ArrayList<Integer> herzhaft_ID = new ArrayList<Integer>();
    ArrayList<String> herzhaft_Name = new ArrayList<String>();
    ArrayList<String> herzhaft_Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat1 = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat1Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat1Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat2 = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat2Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat2Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat3 = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat3Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat3Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat4 = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat4Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat4Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat5 = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat5Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_Zutat5Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_Zubereitung = new ArrayList<String>();


    public ArrayList<Integer> suess_zwei_ID =  new ArrayList<Integer>();
    public ArrayList<String> suess_zwei_Name = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat1 = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat1Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat1Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat2 = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat2Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat2Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat3 = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat3Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat3Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat4 = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat4Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat4Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat5 = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat5Anzahl = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zutat5Bild = new ArrayList<String>();
    public ArrayList<String> suess_zwei_Zubereitung = new ArrayList<String>();



    ArrayList<Integer> herzhaft_zwei_ID = new ArrayList<Integer>();
    ArrayList<String> herzhaft_zwei_Name = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat1 = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat1Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat1Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat2 = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat2Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat2Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat3 = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat3Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat3Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat4 = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat4Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat4Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat5 = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat5Anzahl = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zutat5Bild = new ArrayList<String>();
    ArrayList<String> herzhaft_zwei_Zubereitung = new ArrayList<String>();

    public ArrayList<CDAEntry> suess = new ArrayList<CDAEntry>();
    public ArrayList<CDAEntry> herzhaft = new ArrayList<CDAEntry>();

    public MyAsyncTask task;
    boolean vegetarisch,nuss,vegan,lactose;

    private CDAClient client;
    private Gson resultGson;


    public boolean language;

    private Tracker mTracker;


    @Override
    public void onCreate() {
        super.onCreate();

        final SharedPreferences pref = getSharedPreferences("snackit", Context.MODE_PRIVATE);
        vegetarisch = pref.getBoolean("vegetarisch",false);
        nuss = pref.getBoolean("nuss",false);
        vegan = pref.getBoolean("vegan",false);
        lactose = pref.getBoolean("lactose",false);

        client = CDAClient.builder()
                .setSpace(getString(R.string.CDA_space))
                .setToken(getString(R.string.CDA_delivery_token))
                .build();

        resultGson = new GsonBuilder().setPrettyPrinting().create();

        // do stuff (prefs, etc)
        getDatasfromContentful();


        String Language = Locale.getDefault().getLanguage();
        if(Language.equals("de")){
            language = false;
        }else{
            language = true;
        }
    }

    public void getDatasfromContentful() {
        task = new MyAsyncTask();
        task.execute("");
    }

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }


    public class MyAsyncTask extends AsyncTask<String, String, String> {

        InputStream inputStream = null;
        String result = "";

        protected void onPreExecute() {

        }

        private void getRandomEntry(FetchQuery<CDAEntry> query, Boolean isSuess) {
            if(isSuess) {
                query = query.where("fields.tags[in]", "sweet");
            }else {
                query = query.where("fields.tags[in]", "hearty");
            }

            CDAArray entries = query.all();

            int entries_num = entries.total();
            for(int i=0; i<2; i++) {
                final int r = new Random().nextInt(entries_num);
                CDAEntry random_entry = (CDAEntry) entries.items().get(r);
                if(isSuess) {
                    suess.add(random_entry);
                }else {
                    herzhaft.add(random_entry);
                }
            }
        }

        @Override
        protected String doInBackground(String... params) {

            String url_select = "http://www.marketingsfinest.com/snackit/getRezepte2.php";

            //ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            SharedPreferences pref = getSharedPreferences("snackit", Context.MODE_PRIVATE);

            List<NameValuePair> param = new ArrayList<NameValuePair>();

            Calendar c = Calendar.getInstance();
            int day = c.get(Calendar.DAY_OF_WEEK);

//            CDAArray entries = client.fetch(CDAEntry.class).where("content_type", "recipe").where("fields.tags[in]", "breakfast").all();
            FetchQuery<CDAEntry> query = client.fetch(CDAEntry.class).where("content_type", "recipe");
            int hours = c.get(Calendar.HOUR_OF_DAY);

            if(hours > 3 && hours < 10) {
                query = query.where("fields.tags[in]", "breakfast");
            }else if(hours > 9 && hours < 15) {
                query = query.where("fields.tags[in]", "lunch");
            }else {
                query = query.where("fields.tags[in]", "dinner");
            }

            if(vegetarisch) {
                query = query.where("fields.tags[in]", "vegetarian");
            }else {
                query = query.where("fields.tags[nin]", "vegetarian");
            }

            if(vegan) {
                query = query.where("fields.tags[in]", "vegan");
            }else {
                query = query.where("fields.tags[nin]", "vegan");
            }

            if(nuss) {
                query = query.where("fields.tags[in]", "nuts");
            }else {
                query = query.where("fields.tags[nin]", "nuts");
            }

            if(lactose) {
                query = query.where("fields.tags[in]", "Lactose free");
            } else {
                query = query.where("fields.tags[nin]", "Lactose free");
            }

            getRandomEntry(query, true);
            getRandomEntry(query, false);


            if(day == pref.getInt("currentDay",0)){

                param.add(new BasicNameValuePair("gezeigt", "12;15;33;44;"));

            }else{

                pref.edit().putInt("currentDay",day);
                param.add(new BasicNameValuePair("ID", pref.getString("gezeigt","")));

            }

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
//            result = resultGson.toJson(random_entry).toString();

            return result;
        }

        protected void onPostExecute(String result) {
        }


    }


}