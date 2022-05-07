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

public class Request_status extends AppCompatActivity {
    ListView lv;
    SharedPreferences sp;
    String url="",ip="";
    ArrayList<String> Mechanic,Phone,Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_status);
        lv=(ListView)findViewById(R.id.lv5);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
//        Toast.makeText(Request_status.this,"Hi",Toast.LENGTH_SHORT).show();

        url ="http://"+ip+":5000/view_status";
//        Toast.makeText(Request_status.this,"url"+url,Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(Request_status.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                Toast.makeText(Request_status.this,""+response,Toast.LENGTH_SHORT).show();
                try {

                    JSONArray ar=new JSONArray(response);
//                    Toast.makeText(Request_status.this, ""+ar.toString(), Toast.LENGTH_SHORT).show();
                    Mechanic= new ArrayList<>();
                    Phone= new ArrayList<>();
                    Status= new ArrayList<>();

                    for(int i=0;i<ar.length();i++)
                    {
//                        Toast.makeText(Request_status.this, ""+i, Toast.LENGTH_SHORT).show();
                        JSONObject jo=ar.getJSONObject(i);
                        Mechanic.add(jo.getString("Fname")  + jo.getString("Lname"));
                        Phone.add(jo.getString("Phone"));
                        Status.add(jo.getString("status"));
//                        Toast.makeText(Request_status.this,"g",Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(Request_status.this,"h",Toast.LENGTH_SHORT).show();
                    lv.setAdapter(new Custom_request_status(Request_status.this,Mechanic,Phone,Status));
                } catch (Exception e) {
                    Toast.makeText(Request_status.this, "exp"+e, Toast.LENGTH_SHORT).show();
                    Log.d("=========", e.toString());
                }

//                Toast.makeText(Request_status.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Request_status.this, "err"+error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid", sp.getString("lid", ""));
                return params;
            }
        };
        queue.add(stringRequest);

    }
}