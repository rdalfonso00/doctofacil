package com.example.doctofacil.ui.doctor.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.DatePickerFragment;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;
import com.example.doctofacil.ui.customviews.RegistrationResultDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPatientFragment extends Fragment {

    private static final String ARG_PATIENT = "patient";

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etBirthDate;
    private Button btnEditPatient;

    private Patient patient;
    private DBConnection dbConnection;

    public EditPatientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EditPatientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPatientFragment newInstance() {
        EditPatientFragment fragment = new EditPatientFragment();
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
        View view = inflater.inflate(R.layout.fragment_edit_patient, container, false);

        dbConnection = new DBConnection(getContext());

        etFirstName = view.findViewById(R.id.editTextNamePat);
        etLastName = view.findViewById(R.id.editTextDateAppoint);
        etPhone = view.findViewById(R.id.editTextPhonePat);
        etBirthDate = view.findViewById(R.id.editTextBirthDatePat);

        etFirstName.setText(patient.getName());
        etLastName.setText(patient.getLastName());
        etPhone.setText(patient.getPhone());
        etBirthDate.setText(patient.getBirthDate());

        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(etBirthDate);
            }
        });
        btnEditPatient = view.findViewById(R.id.buttonRegisterPat);
        btnEditPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String birthDate = etBirthDate.getText().toString();

                updatePatient(name, lastName, phone,birthDate);

            }
        });
        return view;
    }

    private void updatePatient(String name, String lastName, String phone, String birthDate) {
        if (!validateFields(name, lastName, phone, birthDate)) {
            return;
        }
        boolean res = dbConnection.updateUser(patient.getUser_id(), name, lastName, phone, birthDate);
        if (!res){ // error
            showRegistrationResultDialog(false, "Hubo un error al modificar los datos.");
        } else{
            showRegistrationResultDialog(true, "Datos actualizados con éxito");
            Navigation.findNavController(requireView()).popBackStack();
        }

    }

    private void showRegistrationResultDialog(boolean success, String message) {
        RegistrationResultDialog dialog = RegistrationResultDialog.newInstance(success, message);
        dialog.show(getParentFragmentManager(), "registration_result_dialog");
    }

    private void showDatePickerDialog(final EditText editTextDate) {
        DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
            final String selectedDate = twoDigits(day) + "/" + twoDigits(month+1) + "/" + year;
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
                    Integer.parseInt(parts[2]),Integer.parseInt(parts[1])-1,Integer.parseInt(parts[0]));
        }

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    private boolean validateFields(String firstName, String lastName, String phone, String birthDate) {
        if (TextUtils.isEmpty(firstName)) {
            showErrorDialog("Por favor, ingresa tu nombre");
            return false;
        }

        if (TextUtils.isEmpty(lastName)) {
            showErrorDialog("Por favor, ingresa al menos un apellido");
            return false;
        }

        if (TextUtils.isEmpty(phone)) {
            showErrorDialog("Por favor, ingresa tu teléfono");
            return false;
        }

        if (TextUtils.isEmpty(birthDate)) {
            showErrorDialog("Por favor, ingresa tu fecha de cumpleaños");
            return false;
        }
        return true;
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }

}