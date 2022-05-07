package com.example.orvba_android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class pView_service_center extends AppCompatActivity{
    ListView lv;
    SharedPreferences sp;
    String url = "", ip = "";
    ArrayList<String> Name, Phone, Email,sid,Latitude,Longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_center);
        lv = (ListView) findViewById(R.id.lv21);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sp.getString("ip", "");

        url = "http://" + ip + ":5000/nearestservice";

        RequestQueue queue = Volley.newRequestQueue(pView_service_center.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);

                try {
                    JSONArray ar = new JSONArray(response);
                    Name = new ArrayList<>();
                    Phone = new ArrayList<>();
                    Email = new ArrayList<>();
                    sid = new ArrayList<>();

                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        Name.add(jo.getString("name") );
                        Phone.add(jo.getString("phone"));
                        Email.add(jo.getString("email"));
                        sid.add(jo.getString("service_id"));

                    }

//                    Toast.makeText(View_service_center.this, ""+LocactionService.lati+","+LocactionService.logi, Toast.LENGTH_SHORT).show();
                    lv.setAdapter(new pCustom_view_service_center(pView_service_center.this, Name,Phone,Email,sid));
                } catch (Exception e) {
                    Toast.makeText(pView_service_center.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(pView_service_center.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lati", LocactionService.lati+"");
                params.put("longi", LocactionService.logi+"");

                return params;
            }
        };

        queue.add(stringRequest);
    };




}