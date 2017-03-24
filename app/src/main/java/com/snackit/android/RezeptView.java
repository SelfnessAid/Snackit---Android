package com.snackit.android;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

public class RezeptView extends AppCompatActivity {


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

    ImageView img_bild, img_zutat1, img_zutat2, img_zutat3,img_zutat4,img_zutat5,btn_like;
    TextView txt_name, txt_zutat1, txt_zutat2, txt_zutat3,txt_zutat4,txt_zutat5, txt_zutat1anzahl,txt_zutat2anzahl, txt_zutat3anzahl,txt_zutat4anzahl,txt_zutat5anzahl, txtzubereitung1,txtzubereitung2,txtzubereitung3,txtzubereitung4,txtzubereitung5;

    ArrayList<Integer> ID = new ArrayList<Integer>();
    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<String> Bild = new ArrayList<String>();
    ArrayList<String> GIF = new ArrayList<String>();
    ArrayList<String> Zutat1 = new ArrayList<String>();
    ArrayList<String> Zutat1Anzahl = new ArrayList<String>();
    ArrayList<String> Zutat1Bild = new ArrayList<String>();
    ArrayList<String> Zutat2 = new ArrayList<String>();
    ArrayList<String> Zutat2Anzahl = new ArrayList<String>();
    ArrayList<String> Zutat2Bild = new ArrayList<String>();
    ArrayList<String> Zutat3 = new ArrayList<String>();
    ArrayList<String> Zutat3Anzahl = new ArrayList<String>();
    ArrayList<String> Zutat3Bild = new ArrayList<String>();
    ArrayList<String> Zutat4 = new ArrayList<String>();
    ArrayList<String> Zutat4Anzahl = new ArrayList<String>();
    ArrayList<String> Zutat4Bild = new ArrayList<String>();
    ArrayList<String> Zutat5 = new ArrayList<String>();
    ArrayList<String> Zutat5Anzahl = new ArrayList<String>();
    ArrayList<String> Zutat5Bild = new ArrayList<String>();
    ArrayList<String> Zuberteitung = new ArrayList<String>();

    String category = "morgens_togo_suess";

    RelativeLayout rezept_content;

    LinearLayout l1,l2,l3,l4,l5;
    LinearLayout l_zubereitung, l_like, l_sogehts;
    ImageView zb1,zb2,zb3,zb4,zb5;
    LinearLayout l1top,l1bottom,l2top,l2bottom,l3top,l3bottom,l4top,l4bottom,l5top,l5bottom;
    TextView txt_zutat1anzahl2,txt_zutat2anzahl2,txt_zutat3anzahl2,txt_zutat4anzahl2,txt_zutat5anzahl2;

    private boolean cli = false;

    Typeface displayBold,displayMedium,displayThin,displayTitling;
    TextView textView5;
    ImageView btnBackTop, btnOder,btnEntweder,btnBackTop2;


    boolean vegetarisch,nuss,vegan,lactose;

    String IDsend = ""+2;

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_to_go_eins_test_two);

        final Animation animationFadeIn = AnimationUtils.loadAnimation(this, R.anim.faein);
        final Animation animationFadeOut = AnimationUtils.loadAnimation(this, R.anim.faeout);

        l1 = (LinearLayout) findViewById(R.id.layout1);
        l2 = (LinearLayout) findViewById(R.id.layout2);
        l3 = (LinearLayout) findViewById(R.id.layout3);
        l4 = (LinearLayout) findViewById(R.id.layout4);
        l5 = (LinearLayout) findViewById(R.id.layout5);
        l1.setVisibility(View.GONE);
        l2.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        l4.setVisibility(View.GONE);
        l5.setVisibility(View.GONE);

        displayBold = Typeface.createFromAsset(getAssets(), "fonts/Neutra2Display-Bold.ttf");
        displayMedium = Typeface.createFromAsset(getAssets(), "fonts/Neutra2Display-Medium.ttf");
        displayThin = Typeface.createFromAsset(getAssets(), "fonts/Neutra2Display-Light.ttf");

        textView5 = (TextView) findViewById(R.id.textView5);
        textView5.setTypeface(displayThin);

        btnBackTop = (ImageView) findViewById(R.id.btnBackTop);
        btnOder = (ImageView) findViewById(R.id.btnOder);
        btnEntweder = (ImageView) findViewById(R.id.btnEntweder);
        btnBackTop2 = (ImageView) findViewById(R.id.btnBackBottom);

        btnBackTop.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(RezeptView.this, FragmentPagerMain.class);
                startActivity(intent);
                finish();
            }
        });


        btnOder.setVisibility(View.GONE);
        btnEntweder.setVisibility(View.GONE);
        btnBackTop2.setVisibility(View.GONE);

        zb1 = (ImageView) findViewById(R.id.zubereitung1beschreibung);
        zb2 = (ImageView) findViewById(R.id.zubereitung2beschreibung);
        zb3 = (ImageView) findViewById(R.id.zubereitung3beschreibung);
        zb4 = (ImageView) findViewById(R.id.zubereitung4beschreibung);
        zb5 = (ImageView) findViewById(R.id.zubereitung5beschreibung);

        zb1.setVisibility(View.GONE);
        zb2.setVisibility(View.GONE);
        zb3.setVisibility(View.GONE);
        zb4.setVisibility(View.GONE);
        zb5.setVisibility(View.GONE);

        img_bild = (ImageView) findViewById(R.id.img_bild);
        img_zutat2 = (ImageView) findViewById(R.id.img_ZutatBild2);
        img_zutat3 = (ImageView)findViewById(R.id.img_ZutatBild3);
        img_zutat1 = (ImageView) findViewById(R.id.img_ZutatBild1);
        txt_zutat1 = (TextView) findViewById(R.id.txt_Zutat1);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_zutat2 = (TextView) findViewById(R.id.txt_Zutat2);
        txt_zutat3 = (TextView) findViewById(R.id.txt_Zutat3);
        img_zutat4 = (ImageView)findViewById(R.id.img_ZutatBild4);
        img_zutat5 = (ImageView)findViewById(R.id.img_ZutatBild5);

        txtzubereitung1 = (TextView)findViewById(R.id.txt_zubereitung1);
        txtzubereitung2 = (TextView)findViewById(R.id.txt_zubereitung2);
        txtzubereitung3 = (TextView)findViewById(R.id.txt_zubereitung3);

        txt_zutat1anzahl2 = (TextView) findViewById(R.id.txt_Zutat1Anzahl2);
        txt_zutat2anzahl2 = (TextView) findViewById(R.id.txt_Zutat2Anzahl2);
        txt_zutat3anzahl2 = (TextView) findViewById(R.id.txt_Zutat3Anzahl2);
        txt_zutat4anzahl2 = (TextView) findViewById(R.id.txt_Zutat4Anzahl2);
        txt_zutat5anzahl2 = (TextView) findViewById(R.id.txt_Zutat5Anzahl2);

        txt_zutat2anzahl = (TextView) findViewById(R.id.txt_Zutat2Anzahl);
        txt_zutat3anzahl = (TextView) findViewById(R.id.txt_Zutat3Anzahl);
        txt_zutat1anzahl = (TextView) findViewById(R.id.txt_Zutat1Anzahl);

        txt_zutat4 = (TextView) findViewById(R.id.txt_Zutat4);
        txt_zutat5 = (TextView) findViewById(R.id.txt_Zutat5);
        txt_zutat4anzahl = (TextView) findViewById(R.id.txt_Zutat4Anzahl);
        txt_zutat5anzahl = (TextView) findViewById(R.id.txt_Zutat5Anzahl);
        txtzubereitung4 = (TextView)findViewById(R.id.txt_zubereitung4);
        txtzubereitung5 = (TextView)findViewById(R.id.txt_zubereitung5);

        txtzubereitung4.setVisibility(View.GONE);
        txtzubereitung5.setVisibility(View.GONE);

        l_like = (LinearLayout)findViewById(R.id.l_like);
        l_zubereitung = (LinearLayout)findViewById(R.id.l_zubereitung);
        l_sogehts = (LinearLayout)findViewById(R.id.l_sogehts);

        Intent myIntent = getIntent(); // gets the previously created intent
        IDsend = myIntent.getStringExtra("ID");

        //rezept_content = (RelativeLayout)findViewById(R.id.rez_cont); ///////

        btn_like = (ImageView)findViewById(R.id.btn_like);
        btn_like.setVisibility(View.GONE);

        ImageView btn_like2 = (ImageView)findViewById(R.id.btn_like2);
        btn_like2.setVisibility(View.GONE);

        /*
        ProductShowCaseWebView wv = (ProductShowCaseWebView) findViewById(R.id.web_view);

        String imagesTag360="";

        int imageCount=36;

        for(int i=1;i<imageCount;i++)
        {
            imagesTag360=imagesTag360+"<img src=\"http://www.spire.de/snackit/360/"+i+".jpg\"/>" ;
        }

        wv.loadDataWithBaseURL("",
                imagesTag360, "text/html", "UTF-8", null);*/

        new MyAsyncTask().execute("");

    }

    public int getInfo(){

        SharedPreferences prefss = getSharedPreferences("snackit", Context.MODE_PRIVATE);
        return prefss.getInt("chosencategory",0);

    }

    class MyAsyncTask extends AsyncTask<String, String, String> {

        InputStream inputStream = null;
        String result = "";


        protected void onPreExecute() {

        }


        @Override
        protected String doInBackground(String... params) {

            String url_select = "http://www.appljuice.com/snackit/getSingleRezept.php";

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            //IDsend
            param.add(new BasicNameValuePair("id",IDsend));


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
            System.out.println("hier " + result);


            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("rezept");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jObject = jsonArray.getJSONObject(i);

                    Name.add(jObject.getString("Name"));
                    Bild.add(jObject.getString("Bild"));
                    GIF.add(jObject.getString("GIF"));


                    Zutat1.add(jObject.getString("Zutat1"));
                    Zutat1Anzahl.add(jObject.getString("Zutat1Anzahl"));
                    Zutat1Bild.add(jObject.getString("Zutat1Bild"));

                    Zutat2.add(jObject.getString("Zutat2"));
                    Zutat2Anzahl.add(jObject.getString("Zutat2Anzahl"));
                    Zutat2Bild.add(jObject.getString("Zutat2Bild"));

                    Zutat3.add(jObject.getString("Zutat3"));
                    Zutat3Anzahl.add(jObject.getString("Zutat3Anzahl"));
                    Zutat3Bild.add(jObject.getString("Zutat3Bild"));

                    Zutat4.add(jObject.getString("Zutat4"));
                    Zutat4Anzahl.add(jObject.getString("Zutat4Anzahl"));
                    Zutat4Bild.add(jObject.getString("Zutat4Bild"));

                    Zutat5.add(jObject.getString("Zutat5"));
                    Zutat5Anzahl.add(jObject.getString("Zutat5Anzahl"));
                    Zutat5Bild.add(jObject.getString("Zutat5Bild"));

                    Zuberteitung.add(jObject.getString("Zubereitung"));

                } // End Loop



            } catch (JSONException e) {
            }

            int num = 0;


            String[] re = Zuberteitung.get(num).split("::");


            System.out.println("tokens1 " + Zuberteitung.get(num));
            StringTokenizer tokens = new StringTokenizer(re[0], ";");


            if(!Zutat1.get(num).equals("")){

                StringTokenizer zut = new StringTokenizer(Zutat1.get(num), "::");

                show(
                        zut.nextToken(),
                        Zutat1Bild.get(num),
                        img_zutat1,
                        txt_zutat1anzahl,
                        txt_zutat1anzahl2,
                        txt_zutat1,
                        txtzubereitung1,
                        l1, zb1, tokens);

                StringTokenizer nam = new StringTokenizer(Name.get(num), "::");

                txt_name.setText(nam.nextToken());
                txt_name.setTypeface(displayBold);


                StringTokenizer anzahl = new StringTokenizer(Zutat1.get(num), ";");

                Glide.with(RezeptView.this).load(Bild.get(num)).override(500,500).into(img_bild);


                //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(img_bild);
                //Glide.with(RezeptView.this).load(GIF.get(num)).into(imageViewTarget);
            }
            if(!Zutat2.get(num).equals("")){

                StringTokenizer zut = new StringTokenizer(Zutat2.get(num), "::");

                show(
                        zut.nextToken(),
                        Zutat2Bild.get(num),
                        img_zutat2,
                        txt_zutat2anzahl,
                        txt_zutat2anzahl2,
                        txt_zutat2,
                        txtzubereitung2,
                        l2, zb2, tokens);

            }
            if(!Zutat3.get(num).equals("")){

                StringTokenizer zut = new StringTokenizer(Zutat3.get(num), "::");

                show(
                        zut.nextToken(),
                        Zutat3Bild.get(num),
                        img_zutat3,
                        txt_zutat3anzahl,
                        txt_zutat3anzahl2,
                        txt_zutat3,
                        txtzubereitung3,
                        l3, zb3, tokens);

            }

            if(!Zutat4.get(num).equals("")){

                StringTokenizer zut = new StringTokenizer(Zutat4.get(num), "::");

                show(
                        zut.nextToken(),
                        Zutat4Bild.get(num),
                        img_zutat4,
                        txt_zutat4anzahl,
                        txt_zutat4anzahl2,
                        txt_zutat4,
                        txtzubereitung4,
                        l4, zb4, tokens);

            }

            if(!Zutat5.get(num).equals("")){

                StringTokenizer zut = new StringTokenizer(Zutat5.get(num), "::");

                show(
                        zut.nextToken(),
                        Zutat5Bild.get(num),
                        img_zutat5,
                        txt_zutat5anzahl,
                        txt_zutat5anzahl2,
                        txt_zutat5,
                        txtzubereitung5,
                        l5, zb5, tokens);

            }


            String[] res = Zuberteitung.get(num).split("::");

            StringTokenizer tokenss = new StringTokenizer(res[0], ";");
            System.out.println("tokens groeße " + tokenss.countTokens());

            StringTokenizer stk = new StringTokenizer(res[0],";");
            ArrayList<String> zubereitungs = new ArrayList<String>();

            while ( stk.hasMoreTokens() ) {
                zubereitungs.add(stk.nextToken());
            }

            for(int i = 0; i < zubereitungs.size(); i++){

                System.out.println("gehe rein total");


                if(i == 0){
                    String zub22 = tokenss.nextToken();
                    zub22 = zub22.substring(3);
                    if (Character.isDigit(zubereitungs.get(0).charAt(0))){
                        zub22 = zubereitungs.get(0).substring(3);
                    }
                    else{
                        zub22 = zubereitungs.get(0);
                    }
                    txtzubereitung1.setText(zub22);
                    txtzubereitung1.setTypeface(displayMedium);
                    l1.setVisibility(View.VISIBLE);
                    zb1.setVisibility(View.VISIBLE);
                    txtzubereitung1.setVisibility(View.VISIBLE);
                    System.out.println("gehe rein 1");
                }
                if(i == 1){
                    String zub22 = tokenss.nextToken();
                    zub22 = zub22.substring(3);
                    if (Character.isDigit(zubereitungs.get(1).charAt(0))){
                        zub22 = zubereitungs.get(1).substring(3);
                    }
                    else{
                        zub22 = zubereitungs.get(1);
                    }
                    txtzubereitung2.setText(zub22);
                    txtzubereitung2.setTypeface(displayMedium);
                    l2.setVisibility(View.VISIBLE);
                    zb2.setVisibility(View.VISIBLE);
                    txtzubereitung2.setVisibility(View.VISIBLE);
                    System.out.println("gehe rein 2");

                }
                if(i == 2){
                    String zub22 = tokenss.nextToken();
                    zub22 = zub22.substring(3);
                    if (Character.isDigit(zubereitungs.get(2).charAt(0))){
                        zub22 = zubereitungs.get(2).substring(3);
                    }
                    else{
                        zub22 = zubereitungs.get(2);
                    }
                    txtzubereitung3.setText(zub22);
                    txtzubereitung3.setTypeface(displayMedium);
                    l3.setVisibility(View.VISIBLE);
                    zb3.setVisibility(View.VISIBLE);
                    txtzubereitung3.setVisibility(View.VISIBLE);
                    System.out.println("gehe rein 3");

                }
                if(i == 3){
                    String zub22 = tokenss.nextToken();
                    zub22 = zub22.substring(3);
                    if (Character.isDigit(zubereitungs.get(3).charAt(0))){
                        zub22 = zubereitungs.get(3).substring(3);
                    }
                    else{
                        zub22 = zubereitungs.get(3);
                    }
                    txtzubereitung4.setText(zub22);
                    txtzubereitung4.setTypeface(displayMedium);
                    l4.setVisibility(View.VISIBLE);
                    zb4.setVisibility(View.VISIBLE);
                    txtzubereitung4.setVisibility(View.VISIBLE);
                    System.out.println("gehe rein 4");

                }
                if(i == 4){
                    String zub22 = tokenss.nextToken();
                    zub22 = zub22.substring(3);
                    if (Character.isDigit(zubereitungs.get(4).charAt(0))){
                        zub22 = zubereitungs.get(4).substring(3);
                    }
                    else{
                        zub22 = zubereitungs.get(4);
                    }
                    txtzubereitung5.setText(zub22);
                    txtzubereitung5.setTypeface(displayMedium);
                    l5.setVisibility(View.VISIBLE);
                    zb5.setVisibility(View.VISIBLE);
                    txtzubereitung5.setVisibility(View.VISIBLE);
                    System.out.println("gehe rein 5");

                }



            }



        }
    }


    public void show(
                     String anzahl,
                     String bildString,
                     ImageView bild,
                     TextView txtAnzahl,
                     TextView txtZutat,
                     TextView txtZutat22,
                     TextView txtZubereitung,
                     LinearLayout l2,
                     ImageView zb,
                     StringTokenizer token){


        StringTokenizer a = new StringTokenizer(anzahl, ";");
        bild.setVisibility(View.VISIBLE);

        Glide.with(this).load(bildString).override(150,150).into(bild);
        txtAnzahl.setText(a.nextToken() );
        txtAnzahl.setVisibility(View.VISIBLE);
        txtZutat.setText(a.nextToken());
        txtZutat.setVisibility(View.VISIBLE);
        txtZutat22.setText(a.nextToken());
        txtZutat22.setVisibility(View.VISIBLE);

        //String zub22 = token.nextToken();
        //zub22 = zub22.substring(3);
        //txtZubereitung.setText(zub22);
        bild.invalidate();
        bild.postInvalidate();


        txtAnzahl.setTypeface(displayThin);
        txtZutat.setTypeface(displayBold);
        txtZubereitung.setTypeface(displayMedium);

        l2.setVisibility(View.VISIBLE);
        //zb.setVisibility(View.VISIBLE);
        //txtZubereitung.setVisibility(View.VISIBLE);

    }



    public void onBackPressed() {
        Intent intent = new Intent(RezeptView.this, FragmentPagerMain.class);
        startActivity(intent);
        finish();
    }
}
