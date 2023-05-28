package com.example.doctofacil.ui.patient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.patient.fragments.adapters.SeeDoctorsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientSeeDoctorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientSeeDoctorsFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT = "patient";

    private Patient patient;

    private RecyclerView recyclerView;
    private SeeDoctorsAdapter adapter;
    private List<Doctor> doctorList;
    private DBConnection dbConnection;

    public PatientSeeDoctorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientSeeDoctorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientSeeDoctorsFragment newInstance() {
        PatientSeeDoctorsFragment fragment = new PatientSeeDoctorsFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_see_doctors, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_see_doctors);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //doctorList = dbConnection.getAllDoctors();

        return view;
    }
}