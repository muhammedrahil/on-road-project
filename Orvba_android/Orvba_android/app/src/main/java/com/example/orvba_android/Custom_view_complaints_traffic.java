package com.example.orvba_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;

public class Custom_view_complaints_traffic extends BaseAdapter{
    private Context context;

    ArrayList<String> a,b,c,d,e;




    public Custom_view_complaints_traffic(Context applicationContext, ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<String> d,ArrayList<String> e) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
        this.e=e;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return a.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getItemViewType(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(context);
            gridView=inflator.inflate(R.layout.activity_custom_view_complaints_traffic, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView53);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView56);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView57);
        TextView tv4=(TextView)gridView.findViewById(R.id.textView58);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://maps.google.com/maps?q="+d.get(position)+","+e.get(position)));
                context.startActivity(i);

            }
        });




        tv1.setText(a.get(position));
        tv2.setText(b.get(position));
        tv3.setText(c.get(position));



        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);










        return gridView;


    }


}












