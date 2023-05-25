package com.example.doctofacil.ui.loginsignup;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.doctofacil.R;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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

                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", email);
                        String role = dbConnection.getUserRole(email);
                        if (role.equals("doctor")) {
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.action_loginFragment_to_mainDoctorFragment, bundle, navOptions);
                        } else {
                            Navigation.findNavController(requireView())
                                    .navigate(R.id.action_loginFragment_to_mainPatientFragment, bundle, navOptions);
                        }
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