package com.example.tayor.karz.views.car;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tayor.karz.BaseActivity;
import com.example.tayor.karz.Model.Car;
import com.example.tayor.karz.Model.Reservation;
import com.example.tayor.karz.R;
import com.example.tayor.karz.views.payment.PaymentActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Nullable;

public class BookCarActivity extends BaseActivity {
    public int key;
    TextView carName, model, color, mileage;
    Button next;
    TextView startDateTime,depositCharge, summary, billOverview;
    TextView endDateTime;
    ImageView carImage;
    String startDateTime_ = "", endDateTime_ = "";
    String formattedStartDate,formattedEndDate;
    LinearLayout estimateLayout;
    FirebaseUser user;
    private FirebaseAuth mAuth;
    private Car car;
    private double hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_car);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initializeComponents();

        car = getIntent().getParcelableExtra(getString(R.string.s_car_tag));

        carName.setText(car.getName());
        model.setText(car.getModel());
        color.setText(car.getColor());
        mileage.setText(String.valueOf(car.getMileage()));
        Picasso.get().load(car.getImage()).centerCrop().fit().into(carImage);

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
                getUseDocumentId();
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
        } else {
            endDateTime_ = endDateTime.getText().toString() + " " + time;
            endDateTime.append("\n" + time);
        }
        if ((!(startDateTime_.isEmpty()) && (!(endDateTime_.isEmpty())))) {
            try {
                validateDateAndTime(startDateTime_, endDateTime_);
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                startDateTime.setText("");
                endDateTime.setText("");
                startDateTime_ = "";
                endDateTime_ = "";
                estimateLayout.setVisibility(View.INVISIBLE);
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

    private void getUseDocumentId(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").whereEqualTo("userId",user.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isComplete()){
                    String userId = task.getResult().getDocuments().get(0).getReference().getId();
                    if(!(startDateTime_.isEmpty() && endDateTime_.isEmpty())) {
                        Reservation reservation = new Reservation();
                        reservation.setUserId(userId);
                        reservation.setStartDateTime(formattedStartDate);
                        reservation.setEndDateTime(formattedEndDate);
                        reservation.setCarId(car.getId());
                        reservation.setDeposit(depositCharge.getText().toString());
                        reservation.setBillingOverview(billOverview.getText().toString());
                        reservation.setHours(String.valueOf(hours));
                        reservation.setMileageReturned("");
                        Intent intent = new Intent(BookCarActivity.this, PaymentActivity.class);
                        intent.putExtra("reservation", reservation);
                        startActivity(intent);
                    } else{
                        Toast.makeText(getApplicationContext(), "Please select your renting period", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void getPayment() {

    }


    private void validateDateAndTime(String startDate, String endDate) throws Exception {
        try {
            Date sDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(startDate);
            Date eDate = new SimpleDateFormat("dd MMM, yyyy hh:mm a").parse(endDate);

            if (sDate.before(Calendar.getInstance().getTime()))
                throw new Exception("Start date cannot come before current date, please review");
            else if (eDate.before(Calendar.getInstance().getTime()))
                throw new Exception("end date cannot come before start date, please review");
            else if (sDate.after(eDate))
                throw new Exception("Start date cannot come after the end date, please review");
            else
                showEstimate(sDate, eDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void showEstimate(Date sDate, Date eDate) {
        String startDate = new SimpleDateFormat("dd MMM, yyyy").format(sDate);
        String endDate = new SimpleDateFormat("dd MMM, yyyy").format(eDate);
        String sTime = new SimpleDateFormat("hh:mm a").format(sDate);
        String eTime = new SimpleDateFormat("hh:mm a").format(eDate);

        showFormattedStartDateTime(startDate,sTime);
        showFormattedEndDateTime(endDate,eTime);

        estimateLayout.setVisibility(View.VISIBLE);

        DateTime sdt = new DateTime(sDate.getTime());
        DateTime edt = new DateTime(eDate.getTime());

        int days = Days.daysBetween(sdt, edt).getDays();
        summary.setText("You have hired " + carName.getText().toString() + " for " + days + " day(s) starting from " + startDate + " at " + sTime + " to " + endDate + " at " + eTime);

        hours = hoursDifference(eDate, sDate);
        billOverview.setText(String.valueOf(hours * 1.5));
    }

    private void showFormattedStartDateTime(String startDate, String startTime){
        formattedStartDate = startDate +" "+ startTime;
    }

    private void showFormattedEndDateTime(String endDate, String endTime){
        formattedEndDate = endDate +" "+ endTime;
    }


    private int hoursDifference(Date date1, Date date2) {
        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        return (int) (date1.getTime() - date2.getTime()) / MILLI_TO_HOUR;
    }


    void initializeComponents() {
        carName = findViewById(R.id.car_name);
        model = findViewById(R.id.model_name);
        color = findViewById(R.id.car_color);
        mileage = findViewById(R.id.mileage_name);
        next = findViewById(R.id.next_btn);
        carImage = findViewById(R.id.car_image);
        startDateTime = findViewById(R.id.start_date_time);
        endDateTime = findViewById(R.id.end_date_time);
        estimateLayout = findViewById(R.id.estimate_layout);
        summary = findViewById(R.id.summary);
        billOverview = findViewById(R.id.billing_overview);
        depositCharge = findViewById(R.id.fixed_deposit_charge);
    }
}
