package com.example.primaryschoolteachers_app.Fragment;

import android.content.DialogInterface;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.primaryschoolteachers_app.Activity.MainActivity;
import com.example.primaryschoolteachers_app.LoginActivity;
import com.example.primaryschoolteachers_app.Model.Data;
import com.example.primaryschoolteachers_app.R;
import com.example.primaryschoolteachers_app.api.Util;
import com.goodiebag.pinview.Pinview;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;
import es.dmoral.toasty.Toasty;


public class HomeFragment extends Fragment {

    String url = "";
    Util util = new Util();
    String headMasterName , Boys , Girls , schoolName, schooleiin;
    TextView total_male_one, total_male_two,total_male_three, total_male_four, total_male_five, total_female_one,
            total_female_two, total_female_three, total_female_four, total_female_five;

    Pinview pvBoys1,pvGirls1 , pvBoys2 , pvGirls2 , pvBoys3 , pvGirls3 , pvBoys4 , pvGirls4 , pvBoys5 , pvGirls5 ;

    int totalBoys , totalGirls ;

    int boys1 , boys2 , boys3 , boys4 , boys5 , girls1 , girls2, girls3 , girls4 , girls5 ;
    List<Data> dataArray;

    int absentBoys, absentGirls;
    String presentboys, presentgirls;

    Button btnDetail;
    SharedPreferences prefs;
    String schoolid;
    String boysone,boystwo,boysthree,boysfour,boysfive,girlsone,girlstwo,girlsThree,girlsFour,girlsFive;

    private TextView tvTodayDate , tvSchoolName, tvEiinNo;
    private RequestQueue mqueue , sendQueue;
    String  attendance_date,today_date;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

      prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
// then you use
        headMasterName = prefs.getString("school_teacher_name","defaultValue");
        schoolName = prefs.getString("school_name_bn","defaultValue");
        schoolid = prefs.getString("school_id","defaultValue");
        schooleiin = prefs.getString("school_eiin_no","defaultValue");
        //tvHeadMasterName.setText(headMasterName);
        //Toast.makeText(getContext(),headMasterName,Toast.LENGTH_LONG).show();
                //jsonParse();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        today_date = simpleDateFormat.format(new Date()).replaceAll("0", "০").replaceAll("1", "১").replaceAll("2", "২").replaceAll("3", "৩").replaceAll("4", "৪").replaceAll("5", "৫").replaceAll("6", "৬").replaceAll("7", "৭").replaceAll("8", "৮").replaceAll("9", "৯");

        tvTodayDate = rootView.findViewById(R.id.tvtoday_date);
        tvTodayDate.setText(today_date);

        //tvHeadMasterName.setText(headMasterName);
        tvSchoolName = rootView.findViewById(R.id.tvSchoolName);
        tvSchoolName.setText(schoolName);
        tvEiinNo = rootView.findViewById(R.id.eiinno);
        tvEiinNo.setText(schooleiin);
        getTotalStudent();

        pvBoys1 = rootView.findViewById(R.id.pvBoys1);
        pvBoys2 = rootView.findViewById(R.id.pvBoys2);
        pvBoys3 = rootView.findViewById(R.id.pvBoys3);
        pvBoys4 = rootView.findViewById(R.id.pvBoys4);
        pvBoys5 = rootView.findViewById(R.id.pvBoys5);
        pvGirls1 = rootView.findViewById(R.id.pvGirls1);
        pvGirls2 = rootView.findViewById(R.id.pvGirls2);
        pvGirls3 = rootView.findViewById(R.id.pvGirls3);
        pvGirls4 = rootView.findViewById(R.id.pvGirls4);
        pvGirls5 = rootView.findViewById(R.id.pvGirls5);
        btnDetail = rootView.findViewById(R.id.details);
        total_male_one = rootView.findViewById(R.id.textView6_Id);
        total_female_one = rootView.findViewById(R.id.textView7Id);

        total_male_two = rootView.findViewById(R.id.textView11Id);
        total_female_two = rootView.findViewById(R.id.textView11Id);

        dataArray=new ArrayList<Data>();
         boysone = pvBoys1.getValue();
        //boys1 = Integer.parseInt(boysone);
        /* */
     boystwo = pvBoys2.getValue();
        //boys2 = Integer.parseInt(boystwo);
        //
         boysthree = pvBoys3.getValue();
        //boys3 = Integer.parseInt(boysthree);
        //
       boysfour = pvBoys4.getValue();
        //boys4 = Integer.parseInt(boysfour);
        //
         boysfive = pvBoys5.getValue();
        //boys5 = Integer.parseInt(boysfive);
        //
        girlsone = pvGirls1.getValue();
        //girls1 = Integer.parseInt(girlsone);
        //
        girlstwo = pvGirls2.getValue();
        //girls2 = Integer.parseInt(girlstwo);
        //
        girlsThree = pvGirls3.getValue();
        //girls3 = Integer.parseInt(girlsThree);
        //
      girlsFour = pvGirls4.getValue();
        //girls4 = Integer.parseInt(girlsFour);
        //
         girlsFive = pvGirls5.getValue();




        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate(boysone,boystwo,boysthree,boysfour,boysfive,girlsone,girlstwo,girlsThree,girlsFour,girlsFive);

               validate();

                //getValue();

            }
        });
        return rootView ;
    }

    private void getTotalStudent() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url+schoolid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject jsonObject = response.getJSONObject(i);


                        String class_id =  jsonObject.getString("ts_class_id");
                        if(class_id.equals("1"))
                        {


                        }




                    } catch (JSONException e) {
                        e.printStackTrace();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  Log.e("Volley", error.toString());

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);

    }

    //private void validate(String boysone, String boystwo, String boysthree, String boysfour, String boysfive, String girlsone, String girlstwo, String girlsThree, String girlsFour, String girlsFive) {

private void validate() {
        boolean error = false;


   /* if (boysone.isEmpty() || girlsone.isEmpty() ) {

        error = true;

        Toasty.info(getContext(), "প্রথম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();

    }
   if(boystwo.isEmpty()|| girlstwo.isEmpty())
    {
        error = true;


        Toasty.info(getContext(), "দ্বিতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
    }

    if(boysthree.isEmpty() || girlsThree.isEmpty())
    {

        error = true;
        Toasty.info(getContext(), "তৃতীয় শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
    }
     if(boysfour.isEmpty() || girlsFour.isEmpty())
    {
        error = true;
        Toasty.info(getContext(), "চতুর্থ শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
    }
     if(boysfive.matches("") || girlsFive.matches(""))
    {

        Toasty.info(getContext(), "পঞ্চম শ্রেণীর ছাত্র-ছাত্রী সংখ্যা পূরণ করুন", Toast.LENGTH_LONG).show();
    }

    else{*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        attendance_date = simpleDateFormat.format(new Date());

        new AlertDialog.Builder(getContext()).
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
                        //Toast.makeText(getContext(),attendance_date,Toast.LENGTH_LONG).show();
                        submitvalidation();

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

    //}

    }


    private void submitvalidation()

    {



            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.10.108:1000/api/school/attendance_student/"+schoolid+"/"+attendance_date,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //hiding the progressbar after completion

                            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            if(response.equals("true"))
                            {
                                Toasty.warning(getContext(),"উপস্থিতি পূর্বেই দেয়া হয়েছে",Toast.LENGTH_LONG).show();
                            }
                            else{
                                todayattendance();

                            }






                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //displaying the error in toast if occurrs
                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

            //creating a request queue
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);


    }





    private void getValue() {


        // Get Value And Convert to Integer

        //girls5 = Integer.parseInt(girlsFive);

        /*boys1 = Integer.parseInt(pvBoys1.getValue());
        boys2 = Integer.parseInt(pvBoys2.getValue());
        boys3 = Integer.parseInt(pvBoys3.getValue());
        boys4 = Integer.parseInt(pvBoys4.getValue());
        boys5 = Integer.parseInt(pvBoys5.getValue());
        girls1 = Integer.parseInt(pvGirls1.getValue());
        girls2 = Integer.parseInt(pvGirls2.getValue());
        girls3 = Integer.parseInt(pvGirls3.getValue());
        girls4 = Integer.parseInt(pvGirls4.getValue());
        girls5 = Integer.parseInt(pvGirls5.getValue());*/
        /*boys1 = Integer.parseInt(pvBoys1.getValue());*/






        /*totalBoys = (boys1 + boys2 +boys3 + boys4 + boys5);
        presentboys = String.valueOf(totalBoys);
        totalGirls = (girls1 + girls2 + girls3 + girls4 + girls5);
        presentgirls = String.valueOf(totalGirls);*/


        //todayattendance();

        //new OrderTask().execute(true);

        /*Boys = Integer.toString(totalBoys);
        Girls = Integer.toString(totalGirls);

        Toast.makeText(getActivity(),String.valueOf(totalBoys), Toast.LENGTH_SHORT).show();

        String data ="{"+ "\"boys\"" + "\"" + Boys + "\","+ "\"girls\"" + "\"" + Girls + "\""+ "}";

        dataSend(data);*/
        //sendTotal(presentboys,presentgirls);


    }

    private void todayattendance() {
        dataArray.add(new Data(schoolid,"1",boysone,girlsone));
        dataArray.add(new Data(schoolid,"2",boystwo,girlstwo));
        dataArray.add(new Data(schoolid,"3",boysthree,girlsThree));
        dataArray.add(new Data(schoolid,"4",boysfour,girlsFour));
        dataArray.add(new Data(schoolid,"5",boysfive,girlsFive));

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

            dataObj.put("attendanceinfo", cartItemsArray);
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

        }

        String url="http://192.168.10.108:1000/api/school/attend_student/store";

        new AsyncHttpClient().post(null,url,entity,"application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {

                //String object= new String(responseBody);
                //Toast.makeText(AddInformationActivity.this,object,Toast.LENGTH_LONG).show();
                try {
                    String object= new String(responseBody);
                    JSONObject jsonObject = new JSONObject(object);
                    /*String result = jsonObject.getString("success");
                    //String message= jsonObject.getString("message");*/
                    Log.d("attendacne", jsonObject.toString());
                } catch (JSONException e) {
                    //Toast.makeText(AddInformationActivity.this, String.valueOf(e), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    class OrderTask extends AsyncTask<Boolean, Void, String> {
        String radiobutton;
        @Override
        protected String doInBackground(Boolean... params) {
            // TODO Auto-generated method stub

            JSONObject dataObj = new JSONObject();
            try
            {

                JSONArray cartItemsArray = new JSONArray();
                JSONObject cartItemsObjedct;
                for (int i = 0; i < dataArray.size(); i++)
                {
                    cartItemsObjedct = new JSONObject();
                    cartItemsObjedct.putOpt("school_id", dataArray.get(i)
                            .getSchool_id());
                    cartItemsObjedct.putOpt("class_id", dataArray.get(i)
                            .getClass_id());
                    cartItemsObjedct.putOpt("present_male", dataArray.get(i)
                            .getTotal_male());
                    cartItemsObjedct.putOpt("present_female",dataArray.get(i).getTotal_female());

                    cartItemsArray.put(cartItemsObjedct);
                }

                dataObj.put("attendanceinfo", cartItemsArray);

            } catch (JSONException e)
            {
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
            if(result != null){
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






                Log.d("response", "result : "+result);


            }else{



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

    private void sendTotal(final String presentboys, final String presentgirls) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, util.BASE_URL+"api/school/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Invalid Credentials"))
                        {
                            Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                           /* school_id = jsonObject.getString("school_id");
                            school_name_bn = jsonObject.getString("school_name_bn");
                            school_eiin_no = jsonObject.getString("school_eiin_no");
                            school_teacher_name = jsonObject.getString("school_teacher_name");
                            school_teacher_mobile = jsonObject.getString("school_teacher_mobile");

                            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("school_id", String.valueOf(school_id)).apply();
                            prefs.edit().putString("school_name_bn", String.valueOf(school_name_bn)).apply();
                            prefs.edit().putString("school_eiin_no", String.valueOf(school_eiin_no)).apply();
                            prefs.edit().putString("school_teacher_name", String.valueOf(school_teacher_name)).apply();
                            prefs.edit().putString("school_teacher_mobile", String.valueOf(school_teacher_mobile)).apply();

                            checkTotalStudent();*/
                            //prefs.edit().putString("total_student", "").apply();

                            //Toast.makeText(LoginActivity.this,school_id,Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("res: ",  response);
                        //



                       /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("present_male",presentboys);
                params.put("present_female",presentgirls);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    // Method for sending TotalBoys and Girls Quantity

    private void dataSend(String data) {

        final String savedata= data;
        String URL="";

        sendQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    Toast.makeText(getActivity(),objres.toString(),Toast.LENGTH_LONG).show();


                } catch (JSONException e) {
                    Toast.makeText(getActivity(),"Server Error",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();


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



}
