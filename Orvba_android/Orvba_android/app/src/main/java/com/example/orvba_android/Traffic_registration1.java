package com.example.orvba_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class Traffic_registration1 extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    SharedPreferences sp;
    String url="",ip="",gender;
    RadioButton r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_registration1);
        e1=(EditText)findViewById(R.id.editTextTextPersonName14);
        e2=(EditText)findViewById(R.id.editTextTextPersonName15);
        e3=(EditText)findViewById(R.id.editTextTextPersonName16);
        e4=(EditText)findViewById(R.id.editTextTextPersonName19);
        e5=(EditText)findViewById(R.id.editTextTextPersonName24);
        e6=(EditText)findViewById(R.id.editTextTextPersonName25);
        e7=(EditText)findViewById(R.id.editTextTextPassword4);
        r1=(RadioButton)findViewById(R.id.radioButton);
        r2=(RadioButton)findViewById(R.id.radioButton2);
        b1=(Button)findViewById(R.id.button7);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = e1.getText().toString();
                final String lname = e2.getText().toString();
                final String phone = e3.getText().toString();
                final String control_room = e4.getText().toString();
                final String police_station = e5.getText().toString();
                final String username = e6.getText().toString();
                final String password = e7.getText().toString();
                gender="";
                if (r1.isChecked()) {
                    gender = r1.getText().toString();
                } else {
                    gender = r2.getText().toString();
                }
                if (fname.equalsIgnoreCase("")) {
                    e1.setError("Enter First Name");
                }
                else if(!fname.matches("^[a-zA-Z]*$"))
                {
                    e1.setError("characters allowed");
                }else if (lname.equalsIgnoreCase("")) {
                    e2.setError("Enter Last Name");
                }
                else if(!lname.matches("^[a-zA-Z]*$"))
                {
                    e2.setError("characters allowed");
                }else if (phone.length() != 10) {
                    e3.setError("Enter Valid Phone Number");
                } else if (control_room.equalsIgnoreCase("")) {
                    e4.setError("Enter Control Room");
                } else if (police_station.equalsIgnoreCase("")) {
                    e5.setError("Enter Police Station");
                } else if (username.equalsIgnoreCase("")) {
                    e6.setError("Enter username");
                } else if (password.equalsIgnoreCase("")) {
                    e7.setError("Enter password");
                } else {
//                    Toast.makeText(Traffic_registration1.this, "" + username + password, Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(Traffic_registration1.this);
                    String url = "http://" + ip + ":5000/traffic";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
//                                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                                String res = json.getString("result");
                                if (res.equals("error")) {
                                    Toast.makeText(getApplicationContext(), "already exist", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
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
                            params.put("fname", fname);
                            params.put("lname", lname);
                            params.put("gender", gender);
                            params.put("phone", phone);
                            params.put("control_room", control_room);
                            params.put("police_station", police_station);
                            params.put("username", username);
                            params.put("password", password);

                            return params;

                        }
                    };
                    queue.add(stringRequest);
                }
            }

        });
    }
}