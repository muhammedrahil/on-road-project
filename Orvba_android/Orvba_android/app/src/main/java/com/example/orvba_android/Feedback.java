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

public class
Feedback extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sp;
    String url="",ip="", feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        e1 = (EditText)findViewById(R.id.editTextTextPersonName9);
        b1 = (Button)findViewById(R.id.button5);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
        url = "http://" + ip + ":5000/fdbks";
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String feedback = e1.getText().toString();
//                Toast.makeText(Feedback.this, ""+feedback, Toast.LENGTH_SHORT).show();

                if (feedback.equalsIgnoreCase("")) {
                    e1.setError("Enter feedback");
                } else {

                    RequestQueue queue = Volley.newRequestQueue(Feedback.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String res = json.getString("result");
                                if (res.equalsIgnoreCase("success")) {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();

                                    startActivity(new Intent(getApplicationContext(), User_home.class));

                                } else {
                                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Error"+error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("feedback", feedback);
                            params.put("lid", sp.getString("lid", ""));
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