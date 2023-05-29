package com.example.doctofacil.ui.patient.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Recipe;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecetaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecetaFragment extends Fragment {

    private static final String ARG_APPOINTMENT = "cita";

    // TODO: Rename and change types of parameters
    private Appointment appointment;
    private TextView tvDiagnosis, tvPrescription, tvDuration, tvDosage;

    public RecetaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecetaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecetaFragment newInstance() {
        RecetaFragment fragment = new RecetaFragment();
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
        View view = inflater.inflate(R.layout.fragment_receta, container, false);

        tvDiagnosis = view.findViewById(R.id.textViewDiagnosis);
        tvPrescription = view.findViewById(R.id.textViewPrescription);
        tvDuration = view.findViewById(R.id.textViewDuration);
        tvDosage = view.findViewById(R.id.textViewDosis);
        DBConnection dbConnection = new DBConnection(getContext());
        Recipe recipe = dbConnection.getRecipeForAppointment(appointment.getAppointmentId());
        if (recipe == null){
            showErrorDialog("Error, esta cita no tiene receta asignada");
            //Navigation.findNavController(view).popBackStack();
        }
        tvDiagnosis.setText(recipe.getDiagnosis());
        tvPrescription.setText(recipe.getPrescription());
        tvDuration.setText(recipe.getDuration());
        tvDosage.setText(recipe.getDosage());

        return view;
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }
}