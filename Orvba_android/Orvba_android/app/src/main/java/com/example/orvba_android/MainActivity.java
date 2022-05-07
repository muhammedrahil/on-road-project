package com.example.orvba_android;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText e1,e2;
    Button b1,b2,b3,b4;

    SharedPreferences sp;
    String ip="",url="",username="",password="";

    String permissions[]=null;
    public static final int MULTIPLE_PERMISSIONS=222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1=(EditText)findViewById(R.id.editTextTextPersonName);
        e2=(EditText)findViewById(R.id.editTextTextPassword);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);

        sp= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ip=sp.getString("ip","");
        Toast.makeText(this, ""+ip, Toast.LENGTH_SHORT).show();

        if (android.os.Build.VERSION.SDK_INT > 23) {
            permissions = new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION};

            if (checkPermissions()) {
                //  permissions  granted.
            }
        }

//        CheckGpsStatus();



        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PUBLICHOME.class));
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                username = e1.getText().toString();
                password = e2.getText().toString();
//                Toast.makeText(MainActivity.this, ""+username+password, Toast.LENGTH_SHORT).show();
                if (username.equalsIgnoreCase("")) {
                    e1.setError("Enter Username");
                } else if (password.equalsIgnoreCase("")) {
                    e2.setError("Enter Password");
                } else {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    String url = "http://" + ip + ":5000/login";

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.d("+++++++++++++++++", response);
                            try {
                                JSONObject json = new JSONObject(response);
                                String lid = json.getString("result");

                                if (lid.equalsIgnoreCase("invalid")) {
                                    Toast.makeText(getApplicationContext(), "invalid user", Toast.LENGTH_LONG).show();
                                } else {
                                    String type = json.getString("type");
                                    SharedPreferences.Editor ed1 = sp.edit();
                                    ed1.putString("lid", lid);
                                    ed1.commit();


                                    Intent i = new Intent(getApplicationContext(), LocactionService.class);
                                    startService(i);
                                    if (type.equalsIgnoreCase("traffic")) {
                                        startActivity(new Intent(getApplicationContext(), Traffic_home.class));
                                    } else if (type.equalsIgnoreCase("user")) {
                                        startActivity(new Intent(getApplicationContext(), User_home.class));
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "invalid user", Toast.LENGTH_LONG).show();
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("username", username);
                                params.put("password", password);
//                            Toast.makeText(MainActivity.this, ""+username+password, Toast.LENGTH_SHORT).show();

                                return params;
                            }
                        }
                    };
                    // Add the request to the RequestQueue.
                    queue.add(stringRequest);
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Traffic_registration1.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),User_registration.class));
            }
        });
    }


    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }
    LocationManager locationManager;

    public void CheckGpsStatus(){
        locationManager = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;
        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(!GpsStatus) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, Need to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();

//            Intent  intent1 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                        }
                    }
                    // Show permissionsDenied
//                    updateViews();
                }
                return;
            }
        }
    }





    public void onBackPressed() {
        // TODO Auto-generated method stub
        AlertDialog.Builder ald=new AlertDialog.Builder(MainActivity.this);
        ald.setTitle("Do you want to Exit")
                .setPositiveButton(" YES ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in=new Intent(Intent.ACTION_MAIN);
                        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        in.addCategory(Intent.CATEGORY_HOME);
                        startActivity(in);
                    }
                })
                .setNegativeButton(" NO ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog al=ald.create();
        al.show();

    }


}
