package com.example.doctofacil.ui.loginsignup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctofacil.R;
import com.example.doctofacil.model.User;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;
import com.example.doctofacil.ui.doctor.MainDoctorActivity;
import com.example.doctofacil.ui.patient.MainPatientActivity;
import com.example.doctofacil.utils.TokenGenerator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_TOKEN = "auth_token";

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;

    private DBConnection dbConnection;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        dbConnection = new DBConnection(getContext());

        etEmail = view.findViewById(R.id.editTextEmailAddressLogin);
        etPassword = view.findViewById(R.id.editTextPasswordLogin);
        btnLogin = view.findViewById(R.id.buttonLoginNow);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (validateFields(email, password)) {
                    boolean success = dbConnection.validateUser(email, password);

                    if (success) {
                        // Login successful
                        NavOptions navOptions = new NavOptions.Builder()
                                .setPopUpTo(R.id.firstFragment, true) // Specify the starting fragment ID
                                .setLaunchSingleTop(true) // Use single top launch mode
                                .build();
                        User user = dbConnection.getUserByEmail(email);

                        Intent intent;
                        Bundle bundle = new Bundle();
                        Log.i("poncho", "LOGIN userid" + user.getUser_id());
                        bundle.putInt("user_id", user.getUser_id());

                        if (user.getRole().equals("doctor")) {
                            intent = new Intent(getActivity(), MainDoctorActivity.class);
                            /*Navigation.findNavController(requireView())
                                    .navigate(R.id.action_loginFragment_to_mainDoctorFragment, bundle, navOptions);

                             */
                        } else {
                            intent = new Intent(getActivity(), MainPatientActivity.class);
                        }

                        saveUserSession(user.getUser_id());

                        intent.putExtra("user_id", user.getUser_id());
                        startActivity(intent);
                        // Proceed with desired action, such as navigating to another screen
                    } else {
                        // Login failed
                        showErrorDialog("Tu correo o contraseña son inválidos, revisa tus datos.");
                    }
                }
            }
        });
        return view;
    }

    // save user session with auth token in sharedprefs, then retrieve it in MainActivity
    private void saveUserSession(int userId) {
        String authToken = TokenGenerator.generateAuthToken(userId);
        // Store the authToken and userId in shared preferences
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(ARG_USER_ID, userId);
        editor.putString(ARG_TOKEN, authToken);
        editor.apply();
    }

    private boolean validateFields(String email, String password) {
        if (TextUtils.isEmpty(email) || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showErrorDialog("Por favor, ingresa un correo válido");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            showErrorDialog("Por favor, ingresa tu contraseña");
            return false;
        }

        return true;
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (!actionBar.isShowing()) {
            actionBar.show();
        }
    }
}