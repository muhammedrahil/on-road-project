package com.example.orvba_android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Custom_view_petrolpump extends BaseAdapter{
    private Context context;

    ArrayList<String> a,b,c,d;




    public Custom_view_petrolpump(Context applicationContext, ArrayList<String> a, ArrayList<String> b,ArrayList<String> c,ArrayList<String> d) {
        // TODO Auto-generated constructor stub
        this.context=applicationContext;
        this.a=a;
        this.b=b;
        this.c=c;
        this.d=d;
//        Toast.makeText(applicationContext, ""+a+b+c+d, Toast.LENGTH_SHORT).show();


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
            gridView=inflator.inflate(R.layout.activity_custom_view_petrolpump, null);

        }
        else
        {
            gridView=(View)convertview;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView24);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView25);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView26);


        tv1.setText(a.get(position));
        tv2.setText(b.get(position));




        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);


        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://maps.google.com/maps?q="+c.get(position)+","+d.get(position)));
                context.startActivity(i);
            }
        });










        return gridView;


    }


}







