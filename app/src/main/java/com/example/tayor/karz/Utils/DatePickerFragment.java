package com.example.tayor.karz.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener  {
    int day,month,year_;
    public static String date;
    //private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.day = dayOfMonth;
        this.month = month;
        this.year_ = year;

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                    year_  = year;
                    month = monthOfYear;
                    day = dayOfMonth;
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year_,month,day);
                    date =  new SimpleDateFormat("dd MMM, yyyy").format(calendar.getTime());
                }
            };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(),mDateSetListener, year, month, day);
    }

    public static String getDate(){
        return date;
    }
}
