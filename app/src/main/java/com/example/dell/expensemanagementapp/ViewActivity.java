package com.example.dell.expensemanagementapp;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewActivity extends AppCompatActivity {
    SQLiteDatabase sql;
    ListView lv;
    ArrayList<String> sugg = new ArrayList<String>();
    String []dat={"Date  -  Expenses"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        lv=(ListView)findViewById(R.id.listView1);
        sql= openOrCreateDatabase("MYDB", MODE_PRIVATE,null);
        sugg.addAll(Arrays.asList(dat));
        if(sql!=null)
        {
            String query1="select * from expense order by month,date";
            Cursor cursor=sql.rawQuery(query1, null);
            while(cursor!=null&&cursor.moveToNext())
            {
                int date=cursor.getInt(0);
                int mon=cursor.getInt(1);
                int year=cursor.getInt(2);
                int expenses=cursor.getInt(3);
                String data=date+"-"+mon+"-"+year+"  -  Rs "+expenses;
                sugg.add(data);
            }
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,sugg);
            lv.setAdapter(adapter);

        }
    }
}
