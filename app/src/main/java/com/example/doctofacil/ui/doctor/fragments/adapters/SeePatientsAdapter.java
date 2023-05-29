package com.example.doctofacil.ui.doctor.fragments.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class SeePatientsAdapter extends RecyclerView.Adapter<SeePatientsAdapter.ViewHolder>{

    private List<Patient> patientList;
    private SeePatientsAdapter.OnEditPacienteListener onEditPacienteListener;
    private Context context;
    private DBConnection conection;


    public View contentView;

    public SeePatientsAdapter(List<Patient> patientList,
                              SeePatientsAdapter.OnEditPacienteListener onEditPacienteListener,
                              Context context) {
        this.patientList = patientList;
        this.onEditPacienteListener = onEditPacienteListener;
        this.context = context;
    }


    @NonNull
    @Override
    public SeePatientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeePatientsAdapter.ViewHolder holder, int position) {
        Patient patient = patientList.get(position);

        holder.patientName.setText(String.format("%s %s", patient.getName(), patient.getLastName()));

        /*******         bottomSheetDialog agendarCita behaviour         *******/
        ///// bottom sheet items
        TextView tvNameProfile;
        TextView tvLastNameProfile;
        Button btEditarPaciente;
        TextView tvEmail;
        TextView tvPhone;
        TextView tvCumple;

        tvNameProfile = contentView.findViewById(R.id.textViewNameProfile);
        tvLastNameProfile = contentView.findViewById(R.id.textViewLastNameProfile);
        btEditarPaciente = contentView.findViewById(R.id.buttonEditarPacienteBottom);
        tvEmail = contentView.findViewById(R.id.textViewCorreoBottom);
        tvPhone = contentView.findViewById(R.id.textViewTelefonoBottom);
        tvCumple = contentView.findViewById(R.id.textViewCumpleañosBottom);

        tvNameProfile.setText(patient.getName());
        tvLastNameProfile.setText(patient.getLastName());
        tvEmail.setText(patient.getEmail());
        tvPhone.setText(patient.getPhone());
        tvCumple.setText(patient.getBirthDate());

        btEditarPaciente.setOnClickListener(view -> {
            holder.sheetDialog.dismiss();
            onEditPacienteListener.onEditPaciente(patient);
        });
        // end bottomSheetDialog agendarCita behaviour
    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView patientImage;
        public TextView patientName;

        public BottomSheetDialog sheetDialog;

        public ViewHolder(@NonNull View view) {
            super(view);
            patientImage = view.findViewById(R.id.icon_doctor_seedoc);
            patientName = view.findViewById(R.id.patient_name_see);

            setupBottomSheetDialog();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheetDialog.show();
                }
            });
        }

        public void setupBottomSheetDialog() {
            sheetDialog = new BottomSheetDialog(itemView.getContext());
            contentView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.one_patient_bottom, null);
            sheetDialog.setContentView(contentView);
            //contentView.requestFocus();
            //sheetDialog.getBehavior().setPeekHeight(2000, true);
            //contentView.setBackgroundResource(R.drawable.bottom_dialog_background);
            contentView.setBackgroundColor(Color.TRANSPARENT);
            // show the sheet only when button is pressed
        }
    }

    public interface OnEditPacienteListener{
        void onEditPaciente(Patient patient);
    }

}
