package com.example.dell.expensemanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RemarkActivity extends AppCompatActivity {
    SQLiteDatabase sql;
    TextView tv1,tv2,tv3,tv4,tv5;
    int expd,date,mon,year,expt,expty,exptt,daily,r;
    Button bget,bshow,bback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark);
        tv1=(TextView) findViewById(R.id.textView12);
        tv2=(TextView) findViewById(R.id.textView2);
        tv3=(TextView) findViewById(R.id.textView4);
        tv4=(TextView) findViewById(R.id.textView5);
        tv5=(TextView) findViewById(R.id.textView6);
        bget=(Button) findViewById(R.id.button11);
        bshow=(Button) findViewById(R.id.button5);
        bback=(Button) findViewById(R.id.button6);
        Intent i= getIntent();
        expd=i.getIntExtra("expense",0);
        date=i.getIntExtra("date", 1);
        mon=i.getIntExtra("month", 1);
        year=i.getIntExtra("year", 1);
        daily=i.getIntExtra("daily",2000);
        sql= openOrCreateDatabase("MYDB", MODE_PRIVATE,null);
        if(sql!=null)
        {
            String query1="select sum(expenses) from expense where month="+mon+" and year="+year+"";
            Cursor cursor=sql.rawQuery(query1, null);
            int row=cursor.getCount();
            if(row!=0)
            {
                cursor.moveToFirst();
                expt=cursor.getInt(0);
                tv1.setText("Rs "+expt);
            }
            String query12="select expenses from expense where month="+mon+" and year="+year+"";
            Cursor cursor3=sql.rawQuery(query12, null);
            r=cursor3.getCount();
            bget.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String query2="select sum(expenses) from expense where year="+year+"";
                    Cursor cursor1=sql.rawQuery(query2, null);
                    int row1=cursor1.getCount();
                    if(row1!=0)
                    {
                        cursor1.moveToFirst();
                        expty=cursor1.getInt(0);
                        Toast.makeText(getApplicationContext(), "Expenses this year are Rs "+expty,Toast.LENGTH_LONG).show();
                    }
                    String query3="select sum(expenses) from expense";
                    Cursor cursor2=sql.rawQuery(query3, null);
                    int row2=cursor2.getCount();
                    if(row2!=0)
                    {
                        cursor2.moveToFirst();
                        exptt=cursor2.getInt(0);
                        Toast.makeText(getApplicationContext(), "Total Expenses are Rs "+exptt,Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        if(expd<=daily)
        {
            int k=daily-expd;
            tv2.setText("1.Your expenses on "+date+"-"+mon+"-"+year+" are not more than Daily expenses Limit");
            tv3.setText("2.You have saved Rs "+k+" this day");
        }
        else
        {
            int k=expd-daily;
            tv2.setTextColor(Color.RED);
            tv3.setTextColor(Color.RED);
            tv2.setText("1.Your expenses on "+date+"-"+mon+"-"+year+" are more than Daily expenses Limit");
            tv3.setText("2.Your extra expense is Rs "+k+" this day");
        }
        if(expt<=daily*r)
        {
            int k1=(daily*r)-expt;
            tv4.setText("3.Your expenses this month are not more than Monthly expenses Limit");
            tv5.setText("4.You have saved Rs "+k1+" in this month according to expenses updated");
        }
        else
        {
            int k1=expt-(daily*r);
            tv4.setTextColor(Color.RED);
            tv5.setTextColor(Color.RED);
            tv4.setText("3.Your expenses this month are more than Daily expenses Limit");
            tv5.setText("4.Your extra expense this month is Rs "+k1+" according to expenses updated");
        }
        bback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RemarkActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(RemarkActivity.this,ViewActivity.class);
                startActivity(intent1);
            }
        });
    }
}
