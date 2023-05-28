package com.example.doctofacil.ui.patient.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.ui.patient.MainPatientActivity;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfileFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT = "patient";

    private Patient patient;

    private TextView tvHola;
    private TextView tvName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvBirthDate;
    private TextView tvCellphone;

    public PatientProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfileFragment newInstance() {
        PatientProfileFragment fragment = new PatientProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable(ARG_PATIENT);
            if (patient == null)
                Log.i("poncho", "no patient");
            Log.i("poncho", "YES patient");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        if (patient == null)
            return view;
        tvHola = view.findViewById(R.id.textViewHola);
        tvName = view.findViewById(R.id.textViewNameProfile);
        tvLastName = view.findViewById(R.id.textViewLastNameProfile);
        tvEmail = view.findViewById(R.id.textViewCorreoProfile);
        tvBirthDate = view.findViewById(R.id.textViewCumpleProfile);
        tvCellphone = view.findViewById(R.id.textViewTelProfile);

        tvHola.setText("Hola, " + patient.getName());
        tvName.setText(patient.getName());
        tvLastName.setText(patient.getLastName());
        tvEmail.setText(patient.getEmail());
        tvBirthDate.setText(patient.getBirthDate());
        tvCellphone.setText(patient.getPhone());

        return view;
    }

}