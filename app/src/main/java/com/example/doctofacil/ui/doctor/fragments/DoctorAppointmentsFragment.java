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
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.doctor.fragments.adapters.DocAppointmentsAdapter;
import com.example.doctofacil.ui.doctor.fragments.adapters.DocAppointmentsTerminatedAdapter;
import com.example.doctofacil.ui.patient.fragments.PatientAppointmentsFragment;
import com.example.doctofacil.ui.patient.fragments.adapters.AppointmentsAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorAppointmentsFragment extends Fragment implements DocAppointmentsTerminatedAdapter.OnAddRecetaClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DOCTOR = "doctor";
    private static final String ARG_APPOINTMENT = "cita";

    public Doctor doctor;
    private List<Appointment> pendingAppointmentList, terminatedAppointmentList;
    private RecyclerView recyclerViewPending, recyclerViewTerminated;
    private DocAppointmentsAdapter appointmentsAdapterPending;
    private DocAppointmentsTerminatedAdapter appointmentsAdapterTerminated;
    private DBConnection dbConnection;

    public DoctorAppointmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DoctorAppointmentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorAppointmentsFragment newInstance() {
        DoctorAppointmentsFragment fragment = new DoctorAppointmentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
            dbConnection = new DBConnection(getContext());
            pendingAppointmentList = dbConnection.getAppointmentsForDoctor(doctor.getUser_id(), "pending");
            terminatedAppointmentList = dbConnection.getAppointmentsForDoctor(doctor.getUser_id(), "terminated");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_appointments, container, false);

        recyclerViewTerminated = view.findViewById(R.id.recyclerViewTerminatedAppointments);
        recyclerViewTerminated.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentsAdapterTerminated = new DocAppointmentsTerminatedAdapter(terminatedAppointmentList, this::onAddReceta, dbConnection);
        recyclerViewTerminated.setAdapter(appointmentsAdapterTerminated);


        recyclerViewPending = view.findViewById(R.id.recyclerViewPendingAppointments);
        recyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentsAdapterPending = new DocAppointmentsAdapter(pendingAppointmentList, terminatedAppointmentList,
                recyclerViewTerminated, dbConnection);
        recyclerViewPending.setAdapter(appointmentsAdapterPending);



        return view;
    }

    @Override
    public void onAddReceta(Appointment appointment) {
        Bundle bundle = new Bundle();

        bundle.putSerializable(ARG_APPOINTMENT, appointment);
        NavHostFragment.findNavController(DoctorAppointmentsFragment.this)
                .navigate(R.id.action_doctorAppointmentsFragment_to_addRecetaFragment, bundle);
    }
}