package com.example.doctofacil;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.doctofacil.ui.customviews.DatePickerFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorSignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private EditText editTextDate;
    private String TAG = "DoctorSignupFragment";

    public DoctorSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DoctorSignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorSignupFragment newInstance() {
        DoctorSignupFragment fragment = new DoctorSignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_signup, container, false);

        editTextDate = view.findViewById(R.id.editTextBirthDate);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(editTextDate);
            }
        });

        return view;
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