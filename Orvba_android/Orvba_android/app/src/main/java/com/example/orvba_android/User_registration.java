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

public class User_registration extends AppCompatActivity {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;
    SharedPreferences sp;
    String url="",ip="",gender,fname,lname,place,post,pin,email,phone,username,password;
    RadioButton r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        e1=(EditText)findViewById(R.id.editTextTextPersonName3);
        e2=(EditText)findViewById(R.id.editTextTextPersonName4);
        e3=(EditText)findViewById(R.id.editTextTextPersonName2);
        e4=(EditText)findViewById(R.id.editTextTextPersonName5);
        e5=(EditText)findViewById(R.id.editTextTextPersonName6);
        e6=(EditText)findViewById(R.id.editTextTextPersonName7);
        e7=(EditText)findViewById(R.id.editTextTextPersonName8);
        e8=(EditText)findViewById(R.id.editTextTextPassword2);
        e9=(EditText)findViewById(R.id.editTextTextPersonName10);
        r1=(RadioButton)findViewById(R.id.radioButton3);
        r2=(RadioButton)findViewById(R.id.radioButton4);
        b1=(Button)findViewById(R.id.button4);
        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        ip=sp.getString("ip","");
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String fname = e1.getText().toString();
                final String lname = e2.getText().toString();
                final String phone = e3.getText().toString();
                final String place = e4.getText().toString();
                final String post = e5.getText().toString();
                final String pin = e6.getText().toString();
                final String username = e7.getText().toString();
                final String password = e8.getText().toString();
                final String email = e9.getText().toString();
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
                } else if (place.equalsIgnoreCase("")) {
                    e4.setError("Enter Place");
                } else if (post.equalsIgnoreCase("")) {
                    e5.setError("Enter Post");
                } else if (pin.length() != 6) {
                    e6.setError("Enter Valid PIN");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        e9.setError("Enter Valid Email");
                } else if (username.equalsIgnoreCase("")) {
                    e7.setError("Enter username");
                } else if (password.equalsIgnoreCase("")) {
                    e8.setError("Enter password");
                } else {
//                    Toast.makeText(User_registration.this, "" + username + password, Toast.LENGTH_SHORT).show();
                    RequestQueue queue = Volley.newRequestQueue(User_registration.this);
                    String url = "http://" + ip + ":5000/userreg";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
//                                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_LONG).show();
                                String res = json.getString("task");
                                if (res .equals("error")) {
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
                            params.put("place", place);
                            params.put("post", post);
                            params.put("pin", pin);
                            params.put("email", email);
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