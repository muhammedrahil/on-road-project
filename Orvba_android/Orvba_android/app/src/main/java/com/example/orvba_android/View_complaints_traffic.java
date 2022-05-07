package com.example.orvba_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class View_complaints_traffic extends AppCompatActivity {
    ListView lv;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> Name,Date,Complaints,Latitude,Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaints_traffic);
        lv=(ListView)findViewById(R.id.lv9);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");

        url ="http://"+ip+":5000/view_complaints";
//        Toast.makeText(this, "hy", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(View_complaints_traffic.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
//                Toast.makeText(View_petrol_pump.this, "j", Toast.LENGTH_SHORT).show();
                Log.d("+++++++++++++++++", response);
//                Toast.makeText(View_complaints_traffic.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ar = new JSONArray(response);
                    Name = new ArrayList<>();
                    Date = new ArrayList<>();
                    Complaints = new ArrayList<>();
                    Latitude = new ArrayList<>();
                    Longitude = new ArrayList<>();
                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        Name.add(jo.getString("Fname") + jo.getString("Lname"));
                        Date.add(jo.getString("Date"));
                        Complaints.add(jo.getString("Complaints"));
                        Latitude.add(jo.getString("latitude"));
                        Longitude.add(jo.getString("longitude"));
//                        Toast.makeText(View_complaints_traffic.this, "hy", Toast.LENGTH_SHORT).show();
                    }
                    lv.setAdapter(new Custom_view_complaints_traffic(View_complaints_traffic.this,Name,Date,Complaints,Latitude,Longitude));
                } catch (Exception e) {
//                    Toast.makeText(View_complaints_traffic.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_complaints_traffic.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        });


        queue.add(stringRequest);


    }
}