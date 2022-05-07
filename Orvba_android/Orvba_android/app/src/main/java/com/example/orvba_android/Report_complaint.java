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

public class Report_complaint extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sp;
    String url="",ip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_complaint);
        e1=(EditText)findViewById(R.id.editTextTextPersonName11);
        b1 = (Button) findViewById(R.id.button6);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip = sp.getString("ip", "");
        url = "http://" + ip + ":5000/complaint";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String complaint = e1.getText().toString();
                if (complaint.equalsIgnoreCase("")) {
                    e1.setError("Enter complaint");
                } else {
                    RequestQueue queue = Volley.newRequestQueue(Report_complaint.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("result");
                                if (res.equalsIgnoreCase("error")) {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                                } else {
                                    startActivity(new Intent(getApplicationContext(), User_home.class));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("complaint", complaint);
                            params.put("lid", sp.getString("lid", ""));
                            params.put("lati", LocactionService.lati);
                            params.put("longi", LocactionService.logi);
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