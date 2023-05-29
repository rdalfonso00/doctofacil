package com.example.doctofacil.ui.doctor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorProfileFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DOCTOR = "doctor";

    private Doctor doctor;

    private TextView tvHola;
    private TextView tvName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvBirthDate;
    private TextView tvCellphone;
    private TextView tvAddress;
    private TextView tvSpecialty;
    private TextView tvLicence;


    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DoctorProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorProfileFragment newInstance() {
        DoctorProfileFragment fragment = new DoctorProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
            if (doctor == null)
                Log.i("poncho", "no patient");
            Log.i("poncho", "YES patient");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_profile, container, false);

        if (doctor == null)
            return view;
        tvHola = view.findViewById(R.id.textViewHola);
        tvName = view.findViewById(R.id.textViewNameProfile);
        tvLastName = view.findViewById(R.id.textViewLastNameProfile);
        tvEmail = view.findViewById(R.id.textViewCorreoProfile);
        tvBirthDate = view.findViewById(R.id.textViewCumpleProfile);
        tvCellphone = view.findViewById(R.id.textViewTelProfile);
        tvAddress = view.findViewById(R.id.textViewAddressProfile);
        tvSpecialty = view.findViewById(R.id.textViewSpecialityProfile);
        tvLicence = view.findViewById(R.id.textViewLicenceProfile);

        tvHola.setText("Hola, Dr(a) " + doctor.getName());
        tvName.setText(doctor.getName());
        tvLastName.setText(doctor.getLastName());
        tvEmail.setText(doctor.getEmail());
        tvBirthDate.setText(doctor.getBirthDate());
        tvCellphone.setText(doctor.getPhone());
        tvAddress.setText(doctor.getAddress());
        tvSpecialty.setText(doctor.getSpecialty());
        tvLicence.setText(doctor.getLicence());

        return view;
    }
}