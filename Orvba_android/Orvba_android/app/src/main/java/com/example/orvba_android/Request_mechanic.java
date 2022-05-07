package com.example.orvba_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Request_mechanic extends AppCompatActivity {
    EditText e1,e2,e3;
    Button b1;
    SharedPreferences sp;
    String url="", ip="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_mechanic);
        e1=(EditText)findViewById(R.id.editTextTextPersonName12);
        e2=(EditText)findViewById(R.id.editTextTextPersonName20);
        e3=(EditText)findViewById(R.id.editTextTextPersonName21);
        b1=(Button)findViewById(R.id.button8);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
//        String mid=getIntent().getStringExtra("mid");
        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String mid=sh.getString("mid","");
        Toast.makeText(this, ""+mid, Toast.LENGTH_SHORT).show();

        e2.setText(LocactionService.lati);
        e3.setText(LocactionService.logi);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String request = e1.getText().toString();
                final String latitude = e2.getText().toString();
                final String longitude = e3.getText().toString();
                if (request.equalsIgnoreCase("")) {
                    e1.setError("Enter Request");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Request_mechanic.this);
                    String url = "http://" + ip + ":5000/req1";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                                String res = json.getString("result");
                                if (res == "error") {
                                    Toast.makeText(getApplicationContext(), "request failed", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), User_home.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "exp" + e, Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("request", request);
                            params.put("lat", latitude);
                            params.put("lon", longitude);
                            params.put("lid", sp.getString("lid", ""));
                            params.put("mid", mid);
                            return params;
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });

    }
}