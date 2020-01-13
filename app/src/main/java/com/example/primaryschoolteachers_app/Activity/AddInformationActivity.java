package com.example.primaryschoolteachers_app.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.primaryschoolteachers_app.Model.Data;
import com.example.primaryschoolteachers_app.R;
import com.example.primaryschoolteachers_app.api.Common;
import com.example.primaryschoolteachers_app.api.RetrofitClient;
import com.example.primaryschoolteachers_app.api.Util;
import com.goodiebag.pinview.Pinview;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import es.dmoral.toasty.Toasty;

public class AddInformationActivity extends AppCompatActivity {
    Pinview pvBoys1, pvGirls1, pvBoys2, pvGirls2, pvBoys3, pvGirls3, pvBoys4, pvGirls4, pvBoys5, pvGirls5;
    Button btnDetail;
    String schoolid;
    JSONArray jsonArray;
    Gson gson;
    String newDataArray;
    String replacedOne;
    Common common;
    List<Data> dataArray;
    ArrayList<HashMap<String, String>> cartArray;
    Data dt;
    RetrofitClient baseurl = new RetrofitClient();

    String gb;
    SharedPreferences prefs;
    private RequestQueue mqueue, sendQueue;
    Util util = new Util();
    JSONArray cartItemsArray;


    int boys1, boys2, boys3, boys4, boys5, girls1, girls2, girls3, girls4, girls5;
    String responseServer;

    private static final String TAG = "App";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar mToolbar = findViewById(R.id.tbHead);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.back);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
// then you use

        schoolid = prefs.getString("school_id", "defaultValue");
        pvBoys1 = findViewById(R.id.pvBoys1);
        pvBoys2 = findViewById(R.id.pvBoys2);
        pvBoys3 = findViewById(R.id.pvBoys3);
        pvBoys4 = findViewById(R.id.pvBoys4);
        pvBoys5 = findViewById(R.id.pvBoys5);
        pvGirls1 = findViewById(R.id.pvGirls1);
        pvGirls2 = findViewById(R.id.pvGirls2);
        pvGirls3 = findViewById(R.id.pvGirls3);
        pvGirls4 = findViewById(R.id.pvGirls4);
        pvGirls5 = findViewById(R.id.pvGirls5);
        ///////////////
        dataArray = new ArrayList<Data>();
        cartArray = new ArrayList<HashMap<String, String>>();

        //Data dt=new Data("Jhon","1234567890","LA",200);

        // add into list array
        //dataArray.add(dt);
        gson = new Gson();


///////////////////////
        btnDetail = findViewById(R.id.details);


        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                conFirmValue(v);

            }
        });

    }

    private void conFirmValue(View v) {
        //replacedOne = one.getText().replaceAll("0","০").replaceAll("1","১").replaceAll("2","২").replaceAll("3","৩").replaceAll("4","৪").replaceAll("5","৫").replaceAll("6","৬").replaceAll("7","৭").replaceAll("8","৮").replaceAll("9","৯");
        new AlertDialog.Builder(this).
                //v = getLayoutInflater().inflate(R.layout.confirm_alert_bg,null);

                        setTitle(R.string.confirm)
                .setMessage("প্রথম শ্রেণী:\t"
                        + "ছেলে:\t" + pvBoys1.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                        + "মেয়ে:\t" + pvGirls1.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                        "দ্বিতীয় শ্রেণী:\t"
                        + "ছেলে:\t" + pvBoys2.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                        + "মেয়ে:\t" + pvGirls2.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                        "তৃতীয় শ্রেণী:\t"
                        + "ছেলে:\t" + pvBoys3.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                        + "মেয়ে:\t" + pvGirls3.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                        "চতুর্থ শ্রেণী:\t"
                        + "ছেলে:\t" + pvBoys4.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                        + "মেয়ে:\t" + pvGirls4.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n" +
                        "পঞ্চম শ্রেণী:\t"
                        + "ছেলে:\t" + pvBoys5.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + ","
                        + "মেয়ে:\t" + pvGirls5.getValue().replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯") + "\n\n")


                .setPositiveButton("নিশ্চিত", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        getValue();

                        dialog.dismiss();
                    }
                }).
                setNegativeButton("না", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void getValue() {

        // Get Value And Convert to Integer




       /*dt = new Data(schoolid,"1",boysone,girlsone);
        dt = new Data(schoolid,"2",boystwo,girlstwo);
      /*boys1 = Integer.parseInt(pvBoys1.getValue());*/
        String boysone = pvBoys1.getValue();
        /* boys2 = Integer.parseInt(pvBoys2.getValue());*/
        String boystwo = pvBoys2.getValue();
        //boys3 = Integer.parseInt(pvBoys3.getValue());
        String boysthree = pvBoys3.getValue();
        //boys4 = Integer.parseInt(pvBoys4.getValue());
        String boysfour = pvBoys4.getValue();
        //boys5 = Integer.parseInt(pvBoys5.getValue());
        String boysfive = pvBoys5.getValue();
        //girls1 = Integer.parseInt(pvGirls1.getValue());
        String girlsone = pvGirls1.getValue();
        //girls2 = Integer.parseInt(pvGirls2.getValue());
        String girlstwo = pvGirls2.getValue();
        //girls3 = Integer.parseInt(pvGirls3.getValue());
        String girlsThree = pvGirls3.getValue();
        //girls4 = Integer.parseInt(pvGirls4.getValue());
        String girlsFour = pvGirls4.getValue();
        //girls5 = Integer.parseInt(pvGirls5.getValue());
        String girlsFive = pvGirls5.getValue();
        /*dt = new Data(schoolid,"3",boysthree,girlsThree);
        dt = new Data(schoolid,"4",boysfour,girlsFour);
        dt = new Data(schoolid,"5",boysfive,girlsFive);*/

        dataArray.add(new Data(schoolid, "1", boysone, girlsone));
        dataArray.add(new Data(schoolid, "2", boystwo, girlstwo));
        dataArray.add(new Data(schoolid, "3", boysthree, girlsThree));
        dataArray.add(new Data(schoolid, "4", boysfour, girlsFour));
        dataArray.add(new Data(schoolid, "5", boysfive, girlsFive));
        Log.d("dataarray:", String.valueOf(dataArray.size()));
        //new OrderTask().execute(true);
        //new AsyncT().execute();
        JSONObject dataObj = new JSONObject();
        try {


            JSONArray cartItemsArray = new JSONArray();
            JSONObject cartItemsObjedct;
            for (int i = 0; i < dataArray.size(); i++) {
                cartItemsObjedct = new JSONObject();
                cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                        .getSchool_id());
                cartItemsObjedct.putOpt("class_id", dataArray.get(i).getClass_id());
                cartItemsObjedct.putOpt("total_male", dataArray.get(i).getTotal_male());
                cartItemsObjedct.putOpt("total_female", dataArray.get(i).getTotal_female());
                cartItemsArray.put(cartItemsObjedct);
            }

            dataObj.put("schoolinfo", cartItemsArray);
        } catch (JSONException e) {
            e.printStackTrace();

        }

        String yourData = dataObj.toString();
        Log.d("data",yourData);
        StringEntity entity = null;
        try {
            entity = new StringEntity(yourData);
            entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        } catch(Exception e) {
//Exception
        }

        String url="http://demo.olivineltd.com/primary_attendance/api/school/total_student/store";

        new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                
                Boolean deger = new Boolean(responseBody.toString());
                Toast.makeText(AddInformationActivity.this, String.valueOf(deger), Toast.LENGTH_SHORT).show();
                if(deger)
                {
                    Intent mainpage = new Intent(AddInformationActivity.this,MainActivity.class);
                    startActivity(mainpage);
                    finish();
                }
                else
                {
                    Toasty.warning(AddInformationActivity.this,"যান্ত্রিক গোলযোগ",Toasty.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
                Log.d("tag", deger.toString());
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });






/*
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest jobReq = new JsonArrayRequest(Request.Method.POST, util.BASE_URL + "api/school/total_student/store", jsonArray,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        JSONArray array=new JSONArray();
                        JSONObject obj=new JSONObject();
                        JSONObject jsonParams = new JSONObject();
                        for(int i=0;i<dataArray.size();i++)
                        {


                            try {
                                obj.put("school_id",dataArray.get(i).getSchool_id());
                                obj.put("class_id",dataArray.get(i).getClass_id());
                                obj.put("total_male",dataArray.get(i).getTotal_male());
                                obj.put("total_female",dataArray.get(i).getTotal_female());

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            array.put(obj);

                        }
                        try {
                            jsonParams.put("schoolinfo",array);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                });

        queue.add(jobReq);*/
            //cartArray = (ArrayList<HashMap<String,String>>) ObjectSerializer.deserialize(settings.getString("cart_items", ObjectSerializer.serialize(new ArrayList<HashMap<String,String>>())));
        /*List<String> classTwo = Arrays.asList(schoolid,"2",boystwo,boysone);
        List<String> classThree = Arrays.asList(schoolid,"3",boysthree,boysone);
        List<String> classFour = Arrays.asList(schoolid,"4",boysfour,boysone);
        List<String> classFive = Arrays.asList(schoolid,"5",boysfive,boysone);*/


            //classone.addAll(classOne);
            //newDataArray=gson.toJson(dataArray);

        /*StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.43.232:1000/api/school/total_student/store",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        final String result=response;
                        Log.d("response", "result : "+result); //when response come i will log it
                        Intent goback = new Intent(AddInformationActivity.this, MainActivity.class);
                        startActivity(goback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                        error.getMessage(); // when error come i will log it
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String, String>();
                param.put("array",newDataArray);

                // array is key which we will use on server side

                return param;
            }
        };
        Vconnection.getnInstance(this).addRequestQue(stringRequest);*/


    }




    class OrderTask extends AsyncTask<Boolean, Void, String> {
        String radiobutton;

        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub

            JSONObject dataObj = new JSONObject();
            try {



                JSONArray cartItemsArray = new JSONArray();
                JSONObject cartItemsObjedct;
                for (int i = 0; i < dataArray.size(); i++) {
                    cartItemsObjedct = new JSONObject();
                    cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                            .getSchool_id());
                    cartItemsObjedct.putOpt("class_id", dataArray.get(i).getClass_id());
                    cartItemsObjedct.putOpt("total_male", dataArray.get(i).getTotal_male());
                    cartItemsObjedct.putOpt("total_female", dataArray.get(i).getTotal_female());
                    cartItemsArray.put(cartItemsObjedct);
                }

                dataObj.put("schoolinfo",cartItemsArray);


                /*URL url = null;
                try {
                    url = new URL(util.BASE_URL + "api/school/total_student/store" + dataObj.put("schoolinfo", cartItemsArray));
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    // is output buffer writter

                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Accept", "application/json");
//set headers and method



                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return dataObj.toString();
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        @Override
        protected void onCancelled(String result) {
            // TODO Auto-generated method stub
            super.onCancelled(result);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                addData(result);
                //login(result);
                //dataSend(result);
                //addTotalStudent(result);

                //String gb = http://192.168.43.232:1000/api/school/total_student/store
                /*URL url = null;
                try {
                    url = new URL(baseurl+"api/school/total_student/store"+result);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/


                //addTotalStudent(result);
                /*Gson gson = new Gson();
                gb = gson.toJson(result);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, util.BASE_URL + "api/school/total_student/store",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Log.d(TAG, "res: " + response);
                                //



                       *//*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);*//*

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String , String> params = new HashMap<>();
                        params.put("schoolinfo",gb);

                        return params;
                    }
                };

                Vconnection.getnInstance(AddInformationActivity.this).addRequestQue(stringRequest);*/

                //addData(gb);

                Log.d("response", "result : " + result);


            } else {


            }
            // TODO Auto-generated method stub

        }

        private ArrayList<HashMap<String, String>> get_items() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

    }


    private void addTotalStudent(String g) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.10.108:1000/api/school/total_student/store" + g,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("added", response);
                        //hiding the progressbar after completion
                        if (response.equals("true")) {
                            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("total_student", response).apply();
                            Intent mainpage = new Intent(AddInformationActivity.this, MainActivity.class);
                            startActivity(mainpage);

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void addRawData(String data) {

    }

    private void dataSend(String data) {

        final String savedata = data;
        String URL = "http://192.168.10.108:1000/api/school/total_student/" + data;

        sendQueue = Volley.newRequestQueue(AddInformationActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("added:", response);
                /*try {
                    JSONObject objres=new JSONObject(response);



                } catch (JSONException e) {
                    Toast.makeText(AddInformationActivity.this,"Server Error",Toast.LENGTH_LONG).show();

                }*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddInformationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }

        };
        sendQueue.add(stringRequest);

    }


   /* void addJsonData()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.10.108:1000/api/school/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //JSONObject jsonObject = new JSONObject(response);
                            cartItemsArray = new JSONArray();
                            JSONObject cartItemsObjedct;
                            for (int i = 0; i < dataArray.size(); i++) {
                                cartItemsObjedct = new JSONObject();
                                cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                                        .getSchool_id());
                                cartItemsObjedct.putOpt("class_id", dataArray.get(i).getClass_id());
                                cartItemsObjedct.putOpt("total_male", dataArray.get(i).getTotal_male());
                                cartItemsObjedct.putOpt("total_female", dataArray.get(i).getTotal_female());
                                cartItemsArray.put(cartItemsObjedct);
                            }

                            //prefs.edit().putString("total_student", "").apply();

                            //Toast.makeText(LoginActivity.this,school_id,Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "res: " + response);
                        //



                       *//*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);*//*

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, Object>  throws AuthFailureError {

                Map<String , Object> params = new HashMap<>();
                params.put("schoolinfo",cartItemsArray);

                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }*/

    void addData(String data)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.10.108:1000/api/store/total_student"+data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        /*prefs.edit().putString("total_student",response) .apply();
                        Intent intent = new Intent(AddInformationActivity.this,MainActivity.class);
                        *//* intent.putExtra("total","ten");*//*
                        startActivity(intent);*/





                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    class AsyncT extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://192.168.10.108:1000/api/school/total_student/store");
            JSONObject dataObj = new JSONObject();

            try {

                JSONArray cartItemsArray = new JSONArray();
                JSONObject cartItemsObjedct = new JSONObject();
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for (int i = 0; i < dataArray.size(); i++) {

                    cartItemsObjedct.put("school_id", dataArray.get(i)
                            .getSchool_id());
                    cartItemsObjedct.put("class_id", dataArray.get(i).getClass_id());
                    cartItemsObjedct.put("total_male", dataArray.get(i).getTotal_male());
                    cartItemsObjedct.put("total_female", dataArray.get(i).getTotal_female());
                    cartItemsArray.put(cartItemsObjedct);

                    /*nameValuePairs.add(new BasicNameValuePair("schoolinfo", cartItemsArray.toString()));
                    Log.e("mainToPost", "mainToPost" + nameValuePairs.toString());*/


                }



                // Use UrlEncodedFormEntity to send in proper format which we need
                //httppost.setEntity(new UrlEncodedFormEntity(cartArray));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                InputStream inputStream = response.getEntity().getContent();
                InputStreamToStringExample str = new InputStreamToStringExample();
                responseServer = str.getStringFromInputStream(inputStream);
                Log.e("response", "response -----" + responseServer);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Toast.makeText(AddInformationActivity.this,responseServer,Toast.LENGTH_LONG).show();
            //txt.setText(responseServer);
        }
    }

    public static class InputStreamToStringExample {

        public static void main(String[] args) throws IOException {

            // intilize an InputStream
            InputStream is =
                    new ByteArrayInputStream("file content..blah blah".getBytes());

            String result = getStringFromInputStream(is);

            System.out.println(result);
            System.out.println("Done");

        }

        // convert InputStream to String
        private static String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }

    }




    }















