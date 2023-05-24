package com.example.doctofacil.ui.customviews;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private DatePickerDialog.OnDateSetListener listener;
    private int initialYear = -1;
    private int initialMonth = -1;
    private int initialDay = -1;

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        return fragment;
    }

    public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener, int year, int month, int day) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setListener(listener);
        fragment.initialYear = year;
        fragment.initialMonth = month;
        fragment.initialDay = day;
        return fragment;
    }


    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);

        if (initialYear == -1) {
            Log.i("poncho","y-1 "+initialYear);
            initialYear = year;
        }

        if (initialMonth == -1) {
            Log.i("poncho","m-1 "+initialMonth);
            initialMonth = c.get(Calendar.MONTH);
        }

        if (initialDay == -1) {
            Log.i("poncho","d-1 "+initialDay);
            initialDay = c.get(Calendar.DAY_OF_MONTH);
        }

        return new DatePickerDialog(getActivity(), listener, initialYear, initialMonth, initialDay);
    }

    private void showDatePickerDialog(final EditText editTextDate) {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            final String selectedDate = twoDigits(day) + "/" + twoDigits(month) + "/" + year;
            Log.i("poncho", "date: "+selectedDate);
            editTextDate.setText(selectedDate);
        };

        String birthDate = String.valueOf(editTextDate.getText());
        DatePickerFragment newFragment;
        if (birthDate.isEmpty()){
            newFragment = DatePickerFragment.newInstance(onDateSetListener);
        }else {
            String[] parts = birthDate.split("/");
            newFragment = DatePickerFragment.newInstance(onDateSetListener,
                    Integer.parseInt(parts[2]),Integer.parseInt(parts[1]),Integer.parseInt(parts[0]));
        }


        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }


    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }
}
