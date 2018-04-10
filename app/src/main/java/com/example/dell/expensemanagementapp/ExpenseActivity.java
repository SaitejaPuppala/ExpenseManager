package com.example.dell.expensemanagementapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ExpenseActivity extends AppCompatActivity {
    TextView tv,tvd;
    EditText et,et1;
    Button bts,btc,btset;
    SQLiteDatabase sq;
    SharedPreferences sp;
    String dll="2000",dl2;
    int date,mon,year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);
        tv=(TextView) findViewById(R.id.textView1);
        tvd=(TextView) findViewById(R.id.textView7);
        et=(EditText) findViewById(R.id.editText1);
        et1=(EditText) findViewById(R.id.editText2);
        bts=(Button) findViewById(R.id.button1);
        btset=(Button) findViewById(R.id.button2);
        btc=(Button) findViewById(R.id.button);
        et1.setVisibility(View.GONE);
        btset.setVisibility(View.GONE);
        sp=getSharedPreferences("MYPREF",MODE_PRIVATE);
        dl2=sp.getString(dll, "2000");
        tvd.setText("Current Daily Limit is Rs "+dl2);
        sq= openOrCreateDatabase("MYDB", MODE_PRIVATE,null);
        Intent i= getIntent();
        date=i.getIntExtra("date", 0);
        mon=i.getIntExtra("month", 0);
        year=i.getIntExtra("year", 0);
        tv.setText(date+"-"+mon+"-"+year);
        if(sq!=null)
        {
            createTable();
            String query="Select expenses from expense where date="+date+" and month="+mon+" and year="+year+"";
            Cursor cursor=sq.rawQuery(query, null);
            int row=cursor.getCount();
            if(row!=0)
            {
                cursor.moveToFirst();
                int exp=cursor.getInt(0);
                et.setText(""+exp);
            }
            else
            {
                int k1=0;
                et.setText(""+k1);
                String query1="insert into expense values("+date+","+mon+","+year+","+k1+")";
                sq.execSQL(query1);
            }
							/*Toast.makeText(getApplicationContext(), "Error in sel and ins", Toast.LENGTH_SHORT).show();
							e.printStackTrace();*/
            btset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences.Editor editor=sp.edit();
                    String data=et1.getText().toString();
                    editor.putString(dll,data);
                    editor.commit();
                    et1.setText("");
                    et1.setVisibility(View.GONE);
                    btset.setVisibility(View.GONE);
                    try {
                        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                        e.printStackTrace();
                    }
                    dl2=data;
                    tvd.setText("Current Daily Limit is Rs "+dl2);
                }
            });
            btc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    et1.setVisibility(View.VISIBLE);
                    btset.setVisibility(View.VISIBLE);
                }
            });
            bts.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    String k=et.getText().toString();
                    int m=Integer.parseInt(k);
                    int n=Integer.parseInt(dl2);
                    updateTable(date,mon,year,m);
                    Intent i1=new Intent(ExpenseActivity.this,RemarkActivity.class);
                    i1.putExtra("expense",m);
                    i1.putExtra("date", date);
                    i1.putExtra("month", mon);
                    i1.putExtra("year", year);
                    i1.putExtra("daily",n);
                    startActivity(i1);
                }

                private void updateTable(int date, int mon, int year, int m) {
                    // TODO Auto-generated method stub
                    String query="update expense set expenses="+m+" where date="+date+" and month="+mon+" and year="+year+"";
                    sq.execSQL(query);
                }
            });
        }
    }
    private void createTable() {
        // TODO Auto-generated method stub

        String query="create table if not exists expense(date int,month int,year int,expenses int)";
        sq.execSQL(query);
    }
}
