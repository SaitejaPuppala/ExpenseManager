package com.example.dell.expensemanagementapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

public class MainActivity extends AppCompatActivity {
    CalendarView cv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cv=(CalendarView) findViewById(R.id.calendarView);
        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                // TODO Auto-generated method stub
                int date=dayOfMonth;
                int mon=month;
                int yr=year;
                Intent i=new Intent(MainActivity.this,ExpenseActivity.class);
                i.putExtra("date", date);
                i.putExtra("month", mon);
                i.putExtra("year", yr);
                startActivity(i);
            }
        });
    }
}
