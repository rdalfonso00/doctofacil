package com.example.doctofacil;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpTypeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpTypeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Button buttonSignupDoctor, buttonSignupPatient;

    public SignUpTypeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignUpTypeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpTypeFragment newInstance() {
        SignUpTypeFragment fragment = new SignUpTypeFragment();
        Bundle args = new Bundle();
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
        View view = inflater.inflate(R.layout.fragment_sign_up_type, container, false);

        buttonSignupDoctor = view.findViewById(R.id.buttonSignDoctor);
        buttonSignupPatient = view.findViewById(R.id.buttonSignPatient);

        buttonSignupDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpTypeFragment.this)
                        .navigate(R.id.action_signUpTypeFragment_to_doctorSignupFragment);
            }
        });

        buttonSignupPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SignUpTypeFragment.this)
                        .navigate(R.id.action_signUpTypeFragment_to_patientSignupFragment);
            }
        });

        return view;
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