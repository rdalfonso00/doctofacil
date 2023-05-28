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
 * Use the {@link DoctorSignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorSignupFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DBConnection dbConnection;

    private EditText etFirstName;
    private EditText etLastName;
    private EditText etPhone;
    private EditText etBirthDate;
    private EditText etEmail;
    private EditText etPassword;

    private EditText etCedula;
    private EditText etSpecialty;
    private EditText etAddress;

    private Button btnSignup;

    private String TAG = "DoctorSignupFragment";

    public DoctorSignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DoctorSignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorSignupFragment newInstance() {
        DoctorSignupFragment fragment = new DoctorSignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_doctor_signup, container, false);

        dbConnection = new DBConnection(getContext());

        etFirstName = view.findViewById(R.id.editTextNameDoc);
        etLastName = view.findViewById(R.id.editTextLastNameDoc);
        etPhone = view.findViewById(R.id.editTextPhoneDoc);

        etCedula = view.findViewById(R.id.editTextCedula);
        etSpecialty = view.findViewById(R.id.editTextEspecialidad);
        etAddress = view.findViewById(R.id.editTextAddress);


        etEmail = view.findViewById(R.id.editTextEmailDoc);
        etPassword = view.findViewById(R.id.editTextPasswordDoc);
        etBirthDate = view.findViewById(R.id.editTextBirthDateDoc);
        etBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(etBirthDate);
            }
        });

        btnSignup = view.findViewById(R.id.buttonRegisterDoctor);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etFirstName.getText().toString();
                String lastName = etLastName.getText().toString();
                String phone = etPhone.getText().toString();

                String cedula = etPhone.getText().toString();
                String address = etAddress.getText().toString();
                String specialty = etSpecialty.getText().toString();

                String birthDate = etBirthDate.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();


                saveDoctorToDatabase(name ,lastName,phone,cedula,address,specialty,birthDate,email,password);
            }
        });

        return view;
    }

    private void saveDoctorToDatabase(String firstName, String lastName, String phone, String cedula,
                                      String address, String specialty, String birthDate, String email, String password) {
        if (validateFields(firstName,lastName,email,password,address,birthDate,phone,specialty,cedula)) {
            String roles = "doctor";
            long id = dbConnection.registerUser(firstName,lastName,email,password,roles,address,birthDate,phone,specialty,cedula);

            if (id != -1) {
                // Registration successful
                showRegistrationResultDialog(true, "Registro realizado con éxito");

                Navigation.findNavController(requireView()).popBackStack();
                Navigation.findNavController(requireView()).popBackStack();

            } else {
                // Registration failed
                showRegistrationResultDialog(false, "Hubo un error al registrar al usuario, revise sus datos.");
            }
        }
    }

    private boolean validateFields(String firstName, String lastName, String email, String password,
                                   String address, String birthDate,String phone,String specialty,String cedula) {
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

        if (TextUtils.isEmpty(cedula)) {
            showErrorDialog("Por favor, ingresa tu número de cédula");
            return false;
        }
        if (TextUtils.isEmpty(specialty)) {
            showErrorDialog("Por favor, ingresa tu especialidad");
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            showErrorDialog("Por favor, ingresa tu dirección");
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

    private void showRegistrationResultDialog(boolean success, String message) {
        RegistrationResultDialog dialogFragment = RegistrationResultDialog.newInstance(success, message);
        dialogFragment.show(getParentFragmentManager(), "registration_result_dialog");
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }


    private void showDatePickerDialog(final EditText editTextDate) {
         DatePickerDialog.OnDateSetListener onDateSetListener = (datePicker, year, month, day) -> {
             final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
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
}