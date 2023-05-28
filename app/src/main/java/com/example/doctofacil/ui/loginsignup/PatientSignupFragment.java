package com.example.doctofacil.ui.loginsignup;

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
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.DatePickerFragment;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;
import com.example.doctofacil.ui.customviews.RegistrationResultDialog;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientSignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etBirthDate;
    private EditText etEmail;
    private EditText etPassword;
    private Button  btnSignup;

    private DBConnection dbConnection;

    public PatientSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PatientSignupFragment.
     */
    public static PatientSignupFragment newInstance() {
        PatientSignupFragment fragment = new PatientSignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_signup, container, false);

        dbConnection = new DBConnection(getContext());

        etFirstName = view.findViewById(R.id.editTextNamePat);
        etLastName = view.findViewById(R.id.editTextDateAppoint);
        etPhone = view.findViewById(R.id.editTextPhonePat);
        etEmail = view.findViewById(R.id.editTextEmailAddressPat);
        etPassword = view.findViewById(R.id.editTextPasswordPat);
        etBirthDate = view.findViewById(R.id.editTextBirthDatePat);
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(etBirthDate);
            }
        });
        btnSignup = view.findViewById(R.id.buttonRegisterPat);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                String birthDate = etBirthDate.getText().toString();

                savePatientToDatabase(name, lastName, phone, email, password, birthDate);

            }
        });
        return view;
    }

    private void savePatientToDatabase(String name, String lastName, String phone, String email,
                                       String password, String birthDate) {
        if (!validateFields(name, lastName, phone, email, password, birthDate)) {
            return;
        }
        long res = dbConnection.registerUser(name, lastName, email,password, "patient", "",birthDate, phone, "","");
        if (res == -1){ // error
            showRegistrationResultDialog(false, "Hubo un error al registrar al usuario, revise sus datos.");
        } else{
            showRegistrationResultDialog(true, "Registro realizado con éxito");
            Navigation.findNavController(requireView()).popBackStack();
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

    private boolean validateFields(String firstName, String lastName, String phone, String email, String password, String birthDate) {
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

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorDialog("Por favor, ingresa un correo válido");
            return false;
        }

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            showErrorDialog("Tu contraseña debe tener al menos 6 caracteres");
            return false;
        }
        return true;
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }
}