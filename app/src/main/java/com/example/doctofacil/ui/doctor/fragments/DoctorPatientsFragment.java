package com.example.doctofacil.ui.doctor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.doctor.fragments.adapters.SeePatientsAdapter;
import com.example.doctofacil.ui.patient.fragments.PatientSeeDoctorsFragment;
import com.example.doctofacil.ui.patient.fragments.adapters.SeeDoctorsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorPatientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorPatientsFragment extends Fragment implements SeePatientsAdapter.OnEditPacienteListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DOCTOR = "doctor";
    private static final String ARG_PATIENT_FOR_DOCTOR = "patient_for_doc";
    private static final String ARG_PATIENT = "patient";

    private Doctor doctor;

    private RecyclerView recyclerView;
    private SeePatientsAdapter adapter;
    private List<Patient> patientsList;
    private DBConnection dbConnection;

    public DoctorPatientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DoctorPatientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorPatientsFragment newInstance() {
        DoctorPatientsFragment fragment = new DoctorPatientsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_patients, container, false);
        dbConnection = new DBConnection(getContext());

        recyclerView = view.findViewById(R.id.recycler_view_see_patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        patientsList = dbConnection.getPatientsForDoctor(doctor.getUser_id());
        adapter = new SeePatientsAdapter(patientsList, this::onEditPaciente, getContext());
        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onEditPaciente(Patient patient){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_PATIENT, patient);
        NavHostFragment.findNavController(DoctorPatientsFragment.this)
                .navigate(R.id.action_doctorPatientsFragment_to_editPatientFragment, bundle);
    }
}