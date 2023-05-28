package com.example.doctofacil.ui.patient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddApointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddApointmentFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT = "patient";
    private static final String ARG_DOCTOR_FOR_PATIENT = "doc_for_patient";

    private TextView textViewName;
    private EditText editTextDateAppointment;
    private LinearLayout linearLayout;
    private CheckBox checkBox;
    private EditText editTextComentarios;
    private Button buttonAgendarCita;

    private Patient patient;
    private Doctor doctor;

    public AddApointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddApointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddApointmentFragment newInstance() {
        AddApointmentFragment fragment = new AddApointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable(ARG_PATIENT);
            doctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR_FOR_PATIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_apointment, container, false);

        textViewName = view.findViewById(R.id.textViewFullNameAppoint);
        editTextDateAppointment = view.findViewById(R.id.editTextDateAppoint);
        linearLayout = view.findViewById(R.id.linearLayout);
        checkBox = view.findViewById(R.id.checkBox);
        editTextComentarios = view.findViewById(R.id.editTextComentarios);
        buttonAgendarCita = view.findViewById(R.id.buttonAgendarCitaBottom);

        textViewName.setText(doctor.getName() + " " + doctor.getLastName());
        //TODO: date appointment show datepicker

        linearLayout.setOnClickListener(view1 -> {

        });

        buttonAgendarCita.setOnClickListener(view12 -> {
            Toast.makeText(getContext(), "agendar jijiji", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}