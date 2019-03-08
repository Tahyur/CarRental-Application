package com.example.tayor.karz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tayor.karz.Model.Car;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class BookCarActivity extends BaseActivity {
    public int key;
    TextView carName, model;
    Button next;
    TextView startDateTime,summary, billOverview;
    TextView endDateTime;
    String startDateTime_ ="",endDateTime_="";
    LinearLayout estimateLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();

        Car car = getIntent().getParcelableExtra(getString(R.string.s_car_tag));

        carName.setText(car.getName());
        model.setText(car.getModel());

        startDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 1;
                showDialog(0);
            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                key = 0;
                showDialog(0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPayment();
            }
        });

    }

    private void updateDate(String date, int flag) {
        if (flag == 1) {
            startDateTime.setText(date);
            setTime();
        } else {
            endDateTime.setText(date);
            setTime();
        }
    }

    private void updateTime(String time, int flag) {
        if (flag == 1) {
            startDateTime_ = startDateTime.getText().toString() + " " + time;
            startDateTime.append("\n" + time);
        }else {
            endDateTime_ = endDateTime.getText().toString() + " " + time;
            endDateTime.append("\n" + time);
        }
        if((!(startDateTime_.isEmpty()) && (!(endDateTime_.isEmpty())))) {
            try {
                validateDateAndTime(startDateTime_,endDateTime_);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                startDateTime.setText("");
                endDateTime.setText("");
            }
        }

    }

    private void setTime() {
        showDialog(1);
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            if (key == 1)
                updateDate(new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime()), key);
            else
                updateDate(new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime()), 0);
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(0, 0, 0, hourOfDay, minute);
            if (key == 1)
                updateTime(new SimpleDateFormat("hh:mm a").format(calendar.getTime()), key);
            else
                updateTime(new SimpleDateFormat("hh:mm a").format(calendar.getTime()), 0);
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0:
                final Calendar c = Calendar.getInstance();
                int startYear = c.get(Calendar.YEAR);
                int startMonth = c.get(Calendar.MONTH);
                int startDate = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this,
                        mDateSetListener, startYear, startMonth, startDate);
            case 1:
                final Calendar ca = Calendar.getInstance();
                int startMin = ca.get(Calendar.MINUTE);
                int startHour = ca.get(Calendar.HOUR);
                return new TimePickerDialog(this, mTimeListener, startHour, startMin, false);
        }
        return null;
    }

    private void getPayment() {
        Intent intent = new Intent(BookCarActivity.this, PaymentActivity.class);
        startActivity(intent);
    }

    private void validateDateAndTime(String startDate, String endDate) throws Exception{
       // Toast.makeText(this, startDate, Toast.LENGTH_SHORT).show();
        try {
            Date sDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(startDate);
            Date eDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(endDate);

            if(sDate.after(eDate))
                throw new Exception("Start date cannot come before the end date, please review");
            else
                showEstimate(sDate,eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showEstimate(Date sDate, Date eDate){
        String startDate = new SimpleDateFormat("dd MMM, yyyy").format(sDate);
        String endDate =  new SimpleDateFormat("dd MMM, yyyy").format(eDate);
        String sTime =  new SimpleDateFormat("hh:mm a").format(sDate);
        String eTime =  new SimpleDateFormat("hh:mm a").format(eDate);

        estimateLayout.setVisibility(View.VISIBLE);

        DateTime sdt = new DateTime(sDate.getTime());
        DateTime edt = new DateTime(eDate.getTime());

        int days = Days.daysBetween(sdt,edt).getDays();
        summary.setText("You have hired "+carName.getText().toString()+" for "+days+" day(s) starting from "+startDate+" at "+sTime+ " to "+endDate+" at "+eTime);

        double hours = hoursDifference(eDate,sDate);
        billOverview.setText("The estimate bill for the hire duration is $"+  hours * 1.5);
    }

    private int hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }


    void initializeComponents() {
        carName = findViewById(R.id.car_name);
        model = findViewById(R.id.model_name);
        next = findViewById(R.id.next_btn);
        startDateTime = findViewById(R.id.start_date_time);
        endDateTime = findViewById(R.id.end_date_time);
        estimateLayout = findViewById(R.id.estimate_layout);
        summary = findViewById(R.id.summary);
        billOverview = findViewById(R.id.billing_overview);
    }
}
