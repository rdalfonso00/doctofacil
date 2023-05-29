package com.example.doctofacil.ui.patient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.Recipe;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;
import com.example.doctofacil.ui.patient.fragments.adapters.AppointmentsAdapter;
import com.example.doctofacil.ui.patient.fragments.adapters.AppointmentsTerminatedAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientAppointmentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientAppointmentsFragment extends Fragment implements AppointmentsTerminatedAdapter.OnVerRecetaClickListener{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT = "patient";
    private static final String ARG_APPOINTMENT = "cita";

    private Patient patient;
    private List<Appointment> pendingAppointmentList, terminatedAppointmentList;
    private RecyclerView recyclerViewPending, recyclerViewTerminated;
    private AppointmentsAdapter appointmentsAdapterPending;
    private AppointmentsTerminatedAdapter appointmentsAdapterTerminated;
    private DBConnection dbConnection;

    public PatientAppointmentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientAppointmentsFragment.
     */
    public static PatientAppointmentsFragment newInstance() {
        PatientAppointmentsFragment fragment = new PatientAppointmentsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable(ARG_PATIENT);
            dbConnection = new DBConnection(getContext());
            pendingAppointmentList = dbConnection.getAppointmentsForPatient(patient.getUser_id(), "pending");
            terminatedAppointmentList = dbConnection.getAppointmentsForPatient(patient.getUser_id(), "terminated");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_appointments, container, false);

        // pending recycler
        recyclerViewPending = view.findViewById(R.id.recyclerViewPendingAppointments);
        recyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentsAdapterPending = new AppointmentsAdapter(pendingAppointmentList, dbConnection);
        recyclerViewPending.setAdapter(appointmentsAdapterPending);

        recyclerViewTerminated = view.findViewById(R.id.recyclerViewTerminatedAppointments);
        recyclerViewTerminated.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentsAdapterTerminated = new AppointmentsTerminatedAdapter(terminatedAppointmentList, this::onVerReceta, dbConnection);
        recyclerViewTerminated.setAdapter(appointmentsAdapterTerminated);


        return view;
    }

    @Override
    public void onVerReceta(Appointment appointment) {
        DBConnection dbConnection = new DBConnection(getContext());
        Recipe recipe = dbConnection.getRecipeForAppointment(appointment.getAppointmentId());
        if (recipe == null){
            showErrorDialog("Error, esta cita no tiene receta asignada");
            return;
        }

        Bundle bundle = new Bundle();

        bundle.putSerializable(ARG_APPOINTMENT, appointment);
        NavHostFragment.findNavController(PatientAppointmentsFragment.this)
                .navigate(R.id.action_patientAppointmentsFragment_to_recetaFragment, bundle);
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }
}