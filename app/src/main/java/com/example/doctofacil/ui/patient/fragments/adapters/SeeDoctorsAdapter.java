package com.example.doctofacil.ui.patient.fragments.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.patient.fragments.PatientSeeDoctorsFragment;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class SeeDoctorsAdapter extends RecyclerView.Adapter<SeeDoctorsAdapter.ViewHolder>{

    private List<Doctor> doctorList;
    private SeeDoctorsAdapter.OnAgendarCitaClickListener onAgendarCitaClickListener;
    private Context context;
    private DBConnection conection;


    public View contentView;

    public SeeDoctorsAdapter(List<Doctor> doctorList,
                             SeeDoctorsAdapter.OnAgendarCitaClickListener onAgendarCitaClickListener,
                             Context context) {
        this.doctorList = doctorList;
        this.onAgendarCitaClickListener = onAgendarCitaClickListener;
        this.context = context;
    }


    @NonNull
    @Override
    public SeeDoctorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SeeDoctorsAdapter.ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.doctorName.setText(doctor.getName() + " " + doctor.getLastName());
        holder.doctorSpeciality.setText(doctor.getSpecialty());

        /*******         bottomSheetDialog agendarCita behaviour         *******/
        ///// bottom sheet items
        TextView tvNameProfile;
        TextView tvLastNameProfile;
        Button btAgendar;
        TextView tvEmail;
        TextView tvPhone;
        TextView tvAddress;
        TextView tvSpeciality;
        TextView tvLicence;

        tvNameProfile = contentView.findViewById(R.id.textViewNameProfile);
        tvLastNameProfile = contentView.findViewById(R.id.textViewLastNameProfile);
        btAgendar = contentView.findViewById(R.id.buttonAgendarCitaBottom);
        tvEmail = contentView.findViewById(R.id.textViewCorreoBottom);
        tvPhone = contentView.findViewById(R.id.textViewTelefonoBottom);
        tvAddress = contentView.findViewById(R.id.textViewDireccionBottom);
        tvSpeciality = contentView.findViewById(R.id.textViewEspecialidadBottom);
        tvLicence = contentView.findViewById(R.id.textViewCedulaBottom);

        tvNameProfile.setText(doctor.getName());
        tvLastNameProfile.setText(doctor.getLastName());
        tvEmail.setText(doctor.getEmail());
        tvPhone.setText(doctor.getPhone());
        tvAddress.setText(doctor.getAddress());
        tvSpeciality.setText(doctor.getSpecialty());
        tvLicence.setText(doctor.getLicence());

        btAgendar.setOnClickListener(view -> {
            holder.sheetDialog.dismiss();
            onAgendarCitaClickListener.onAgendarCita(doctor);
        });
        // end bottomSheetDialog agendarCita behaviour
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView doctorImage;
        public TextView doctorName;
        public TextView doctorSpeciality;

        public BottomSheetDialog sheetDialog;

        public ViewHolder(@NonNull View view) {
            super(view);
            doctorImage = view.findViewById(R.id.icon_doctor_seedoc);
            doctorName = view.findViewById(R.id.doctor_name_seedocs);
            doctorSpeciality = view.findViewById(R.id.speciality_seedoc);

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
            contentView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.one_doctor_bottom, null);
            sheetDialog.setContentView(contentView);
            //contentView.requestFocus();
            sheetDialog.getBehavior().setPeekHeight(2000, true);
            //contentView.setBackgroundResource(R.drawable.bottom_dialog_background);
            contentView.setBackgroundColor(Color.TRANSPARENT);
            // show the sheet only when button is pressed
        }
    }

    public interface OnAgendarCitaClickListener{
        void onAgendarCita(Doctor doctor);
    }

}
