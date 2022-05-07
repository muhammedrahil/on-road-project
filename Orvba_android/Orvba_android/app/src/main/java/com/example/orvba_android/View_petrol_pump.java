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

public class View_petrol_pump extends AppCompatActivity {
    ListView lv;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> PetrolPump, Phone,Latitude,Longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_petrol_pump);

        lv=(ListView)findViewById(R.id.lv6);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");

        url ="http://"+ip+":5000/nearestpetrol";
//        Toast.makeText(this, "hy", Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(View_petrol_pump.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
//                Toast.makeText(View_petrol_pump.this, "j", Toast.LENGTH_SHORT).show();
                Log.d("+++++++++++++++++", response);
                Toast.makeText(View_petrol_pump.this, ""+LocactionService.lati+LocactionService.logi, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ar = new JSONArray(response);
                    PetrolPump = new ArrayList<>();
                    Phone = new ArrayList<>();
                    Latitude = new ArrayList<>();
                    Longitude = new ArrayList<>();
                    for (int i = 0; i < ar.length(); i++) {
                        JSONObject jo = ar.getJSONObject(i);
                        PetrolPump.add(jo.getString("name"));
                        Phone.add(jo.getString("phone_no"));
                        Latitude.add(jo.getString("latitude"));
                        Longitude.add(jo.getString("longitude"));
//                        Toast.makeText(View_petrol_pump.this, "hy", Toast.LENGTH_SHORT).show();
                    }
                    lv.setAdapter(new Custom_view_petrolpump(View_petrol_pump.this, PetrolPump, Phone,Latitude,Longitude));
                } catch (Exception e) {
//                    Toast.makeText(View_petrol_pump.this, ""+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(View_petrol_pump.this, "err"+error, Toast.LENGTH_SHORT).show();
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


    }
}