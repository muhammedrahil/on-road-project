package com.example.orvba_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PUBLICHOME extends AppCompatActivity {
    Button b1,b2,b3,b4,b5,b6,b7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_home);
        b1=(Button)findViewById(R.id.button10);
        b2=(Button)findViewById(R.id.button11);
        b4=(Button)findViewById(R.id.button13);
        b5=(Button)findViewById(R.id.button14);
        b6=(Button)findViewById(R.id.button15);
        b5.setVisibility(View.GONE);
        Intent i = new Intent(getApplicationContext(), LocactionService.class);
        startService(i);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),pView_service_center.class));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),View_petrol_pump.class));
            }
        });


        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),pFeedback.class));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),View_mechanic.class));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}