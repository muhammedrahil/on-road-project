package com.example.orvba_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ip_page extends AppCompatActivity {
    EditText e1;
    Button b1;
    SharedPreferences sp;
    RelativeLayout r1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_page);
        e1 = (EditText) findViewById(R.id.editTextTextPersonName27);
        b1 = (Button) findViewById(R.id.button21);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ip = e1.getText().toString();
                if (ip.equalsIgnoreCase("")) {
                    e1.setError("Enter IP Address");
                } else {
                    SharedPreferences.Editor e = sp.edit();
                    e.putString("ip", ip);
                    e.commit();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);

                }
            }
        });
    }
}