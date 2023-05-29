package com.example.doctofacil.ui.doctor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.RegistrationResultDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddRecetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRecetaFragment extends Fragment {

    private static final String ARG_APPOINTMENT = "cita";

    // TODO: Rename and change types of parameters
    private Appointment appointment;
    private EditText etDiagnosis, etPrescription, etDuration, etDosage;

    public AddRecetaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddRecetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRecetaFragment newInstance() {
        AddRecetaFragment fragment = new AddRecetaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appointment = (Appointment) getArguments().getSerializable(ARG_APPOINTMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_receta, container, false);

        etDiagnosis = view.findViewById(R.id.textViewDiagnosis);
        etPrescription = view.findViewById(R.id.textViewPrescription);
        etDuration = view.findViewById(R.id.textViewDuration);
        etDosage = view.findViewById(R.id.textViewDosis);
        DBConnection dbConnection = new DBConnection(getContext());
        Button btAddRecepie = view.findViewById(R.id.buttonAddReceta);
        btAddRecepie.setOnClickListener(view1 -> {
            String diagnosis = etDiagnosis.getText().toString();
            String prescription = etPrescription.getText().toString();
            int duration = Integer.parseInt(etDuration.getText().toString());
            String dosage = etDosage.getText().toString();
            long res = dbConnection.addRecipe(appointment.getAppointmentId(), -1, diagnosis,prescription,duration,dosage);
            if (res == -1){ // error
                showRegistrationResultDialog(false, "Hubo un error al registrar la receta, revise sus datos.");
            } else{
                showRegistrationResultDialog(true, "Receta añadida con éxito");
                Navigation.findNavController(requireView()).popBackStack();
                Navigation.findNavController(requireView()).popBackStack();
            }
        });


        return view;
    }

    private void showRegistrationResultDialog(boolean success, String message) {
        RegistrationResultDialog dialog = RegistrationResultDialog.newInstance(success, message);
        dialog.show(getParentFragmentManager(), "registration_result_dialog");
    }
}