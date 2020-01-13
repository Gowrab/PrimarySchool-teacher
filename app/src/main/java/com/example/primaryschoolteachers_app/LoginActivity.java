package com.example.primaryschoolteachers_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.primaryschoolteachers_app.Activity.AddInformationActivity;
import com.example.primaryschoolteachers_app.Activity.MainActivity;
import com.example.primaryschoolteachers_app.Model.LoginResponse;
import com.example.primaryschoolteachers_app.api.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {

    private Button button;
    String school_id,school_name_bn,school_eiin_no,school_teacher_name,school_teacher_mobile,school_upazila_id;
    private static final String TAG = "App";
    EditText etLoginUser , etLoginPassword ;
    String mobile_no,password;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        school_id = prefs.getString("school_id","");
        if(!school_id.isEmpty())
        {
           Intent mainpage = new Intent(this, MainActivity.class);
           startActivity(mainpage);

        }
        setContentView(R.layout.activity_login);


        button = findViewById(R.id.button_login);
        etLoginUser = findViewById(R.id.etLoginUser);
        etLoginPassword = findViewById(R.id.etLoginPass);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);*/
            //    login();
                mobile_no = etLoginUser.getText().toString();
                password = etLoginPassword.getText().toString();
              login();
                //onLoginSuccess();
            }
        });
    }

    private void login() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://demo.olivineltd.com/primary_attendance/api/school/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Invalid Credentials"))
                        {
                            Toast.makeText(LoginActivity.this,response,Toast.LENGTH_LONG).show();
                        }

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            school_id = jsonObject.getString("school_id");
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

                            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                            checkTotalStudent();
                            //prefs.edit().putString("total_student", "").apply();

                            //Toast.makeText(LoginActivity.this,school_id,Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "res: " + response);
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
                params.put("mobile_no",mobile_no);
                params.put("password",password);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void checkTotalStudent() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://192.168.10.108:1000/api/school/total_student/"+school_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion

                            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                            prefs.edit().putString("total_student",response) .apply();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        /* intent.putExtra("total","ten");*/
                        startActivity(intent);





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


    public void onLoginSuccess() {
       final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        //Toast.makeText(LoginActivity.this,mobile_no+password,Toast.LENGTH_LONG).show();

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(mobile_no, password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: Code: " + response.code());
                    return;
                }
                LoginResponse loginResponse = response.body();

                if (loginResponse != null) {
                   //Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    //Log.d("login hit",loginResponse.getMessage());

                 String school_id = loginResponse.getUserData().getSchool_id();
                    Toast.makeText(LoginActivity.this,String.valueOf(school_id),Toast.LENGTH_LONG).show();
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    prefs.edit().putString("u_id", String.valueOf(school_id)).apply();

//                    Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
                    //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }  else {
                    Log.d(TAG, "onResponse: Login Response is null");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this,"Invalid Login",Toast.LENGTH_LONG).show();
            }
        });
    }
}
