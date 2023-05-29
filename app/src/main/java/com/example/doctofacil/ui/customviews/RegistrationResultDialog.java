package com.example.doctofacil.ui.customviews;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

public class RegistrationResultDialog extends DialogFragment {
    private static final String ARG_SUCCESS = "success";
    private static final String ARG_MESSAGE = "message";

    public static RegistrationResultDialog newInstance(boolean success, String message) {
        RegistrationResultDialog dialog = new RegistrationResultDialog();
        Bundle args = new Bundle();
        args.putBoolean(ARG_SUCCESS, success);
        args.putString(ARG_MESSAGE, message);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        boolean success = getArguments().getBoolean(ARG_SUCCESS);
        String message = getArguments().getString(ARG_MESSAGE);

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(success ? "Éxito" : "Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });

        return builder.create();
    }
}