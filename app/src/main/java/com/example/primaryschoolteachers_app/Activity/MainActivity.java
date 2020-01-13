package com.example.primaryschoolteachers_app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
import com.example.primaryschoolteachers_app.Fragment.HomeFragment;
import com.example.primaryschoolteachers_app.Fragment.LogFragment;
import com.example.primaryschoolteachers_app.Fragment.ProfileFragment;
import com.example.primaryschoolteachers_app.R;
import com.goodiebag.pinview.Pinview;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    String total;
    Fragment selectFragment = null ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        /*Intent intent = getIntent();
        total = intent.getStringExtra("total");*/


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        total = prefs.getString("total_student","defaultValue");
        if(total.equals("false"))
        {

            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    new ProfileFragment()).commit();
            //Toast.makeText(this,total,Toast.LENGTH_LONG).show();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    new HomeFragment()).commit();

        }


        BottomNavigationView bottomNv = findViewById(R.id.bottomNav);
        bottomNv.setOnNavigationItemSelectedListener(navListener);




        /*getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                new HomeFragment()).commit();*/



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


            switch (menuItem.getItemId())
            {
                case R.id.nav_home:
                    selectFragment = new HomeFragment();
                    break;
                case R.id.nav_log:
                    selectFragment = new LogFragment();
                    break;
                case  R.id.nav_profile:
                    selectFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,
                    selectFragment).commit();

            return true ;
        }
    };

}
