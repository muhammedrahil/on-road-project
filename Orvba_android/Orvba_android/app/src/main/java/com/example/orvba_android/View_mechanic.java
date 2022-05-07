package com.example.orvba_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class View_mechanic extends AppCompatActivity{
    ListView lv;
    SharedPreferences sp;
    String url = "", ip = "";
   public static  ArrayList<String> Name, Phone,  mid;
    Intent ik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mechanic);
        lv = (ListView) findViewById(R.id.lv4);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sp.getString("ip", "");

        url = "http://" + ip + ":5000/view_mechanic";
//        Toast.makeText(this, "hy", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(View_mechanic.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
//                Toast.makeText(View_mechanic.this, "j", Toast.LENGTH_SHORT).show();
                Log.d("+++++++++++++++++", response);
//                Toast.makeText(View_mechanic.this, "hy", Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ar = new JSONArray(response);
                    Name = new ArrayList<>();
                    Phone = new ArrayList<>();
                    mid = new ArrayList<>();
                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        Name.add(jo.getString("Fname") + jo.getString("Lname"));
                        Phone.add(jo.getString("Phone"));

                        mid.add(jo.getString("Login_id"));
//                        Toast.makeText(View_mechanic.this, ""+mid, Toast.LENGTH_SHORT).show();
                    }
                    lv.setAdapter(new Custom_view_mechanic(View_mechanic.this, Name, Phone,mid));
                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_mechanic.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    };




}