package com.example.primaryschoolteachers_app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.primaryschoolteachers_app.Activity.AddInformation;
import com.example.primaryschoolteachers_app.Activity.AddInformationActivity;
import com.example.primaryschoolteachers_app.R;


public class ProfileFragment extends Fragment {

    String headMasterName,schoolname,eiinno, phoneno, emailid, totalstudent;
    Button addinfo;
    TextView tvprofileName,tvdesignation,tvschoolname,tveiin,tvphone,tvemail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_profile_fargment, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
// then you use
        headMasterName = prefs.getString("school_teacher_name","defaultValue");
        schoolname = prefs.getString("school_name_bn","defaultValue");
        eiinno = prefs.getString("school_eiin_no","defaultValue");
        phoneno = prefs.getString("school_teacher_mobile","defaultValue");
        totalstudent = prefs.getString("total_student","defaultvalue");
        if(totalstudent.equals("true"))
        {
            addinfo.setVisibility(View.GONE);
        }
        tvprofileName = rootView.findViewById(R.id.profileNameId);
        tvprofileName.setText(headMasterName);
        tvschoolname = rootView.findViewById(R.id.tvSchoolName);
        tvschoolname.setText(schoolname);
        tveiin = rootView.findViewById(R.id.eiinno);
        tveiin.setText(eiinno);
        tvphone = rootView.findViewById(R.id.phnNoId);
        tvphone.setText(phoneno);
        tvemail = rootView.findViewById(R.id.emailId);


        /*SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        schoolid = prefs.getString("u_id", "Empty");*/
        addinfo = rootView.findViewById(R.id.addinfo);
        addinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(getContext(), AddInformationActivity.class);
                startActivity(info);
            }
        });
        return rootView ;

    }

}
