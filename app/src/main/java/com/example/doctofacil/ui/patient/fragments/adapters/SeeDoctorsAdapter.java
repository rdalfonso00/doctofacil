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
    private SeeDoctorsAdapter.OnItemClickListener onItemClickListener;
    private SeeDoctorsAdapter.OnAgendarCitaClickListener onAgendarCitaClickListener;
    private Context context;
    private DBConnection conection;

    public BottomSheetDialog sheetDialog;
    public View contentView;

    public SeeDoctorsAdapter(List<Doctor> doctorList,
                             SeeDoctorsAdapter.OnItemClickListener onItemClickListener,
                             SeeDoctorsAdapter.OnAgendarCitaClickListener onAgendarCitaClickListener,
                             Context context) {
        this.doctorList = doctorList;
        this.onItemClickListener = onItemClickListener;
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

        // bottomSheetDialog agendarCita behaviour
        holder.btAgendar.setOnClickListener(view -> {
            onAgendarCitaClickListener.onAgendarCita(doctor);
        });
        holder.tvNameProfile.setText(doctor.getName());
        holder.tvLastNameProfile.setText(doctor.getLastName());
        holder.tvEmail.setText(doctor.getEmail());
        holder.tvPhone.setText(doctor.getPhone());
        holder.tvAddress.setText(doctor.getAddress());
        holder.tvSpeciality.setText(doctor.getSpecialty());
        holder.tvLicence.setText(doctor.getLicence());
        // end bottomSheetDialog agendarCita behaviour

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(doctor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView doctorImage;
        public TextView doctorName;
        public TextView doctorSpeciality;
        ///// bottom sheet items
        public TextView tvNameProfile;
        public TextView tvLastNameProfile;
        public Button btAgendar;
        public TextView tvEmail;
        public TextView tvPhone;
        public TextView tvAddress;
        public TextView tvSpeciality;
        public TextView tvLicence;

        public ViewHolder(@NonNull View view) {
            super(view);
            doctorImage = view.findViewById(R.id.icon_doctor_seedoc);
            doctorName = view.findViewById(R.id.doctor_name_seedocs);
            doctorSpeciality = view.findViewById(R.id.speciality_seedoc);

            setupBottomSheetDialog();

            tvNameProfile = contentView.findViewById(R.id.textViewNameProfile);
            tvLastNameProfile = contentView.findViewById(R.id.textViewLastNameProfile);
            btAgendar = contentView.findViewById(R.id.buttonAgendarCitaBottom);
            tvEmail = contentView.findViewById(R.id.textViewCorreoBottom);
            tvPhone = contentView.findViewById(R.id.textViewTelefonoBottom);
            tvAddress = contentView.findViewById(R.id.textViewDireccionBottom);
            tvSpeciality = contentView.findViewById(R.id.textViewEspecialidadBottom);
            tvLicence = contentView.findViewById(R.id.textViewCedulaBottom);

        }

        public void setupBottomSheetDialog() {
            sheetDialog = new BottomSheetDialog(itemView.getContext());
            contentView = LayoutInflater.from(itemView.getContext()).inflate(R.layout.one_doctor_bottom, null);
            sheetDialog.setContentView(contentView);
            //contentView.requestFocus();
            //sheetDialog.getBehavior().setPeekHeight(700, true);
            //contentView.setBackgroundResource(R.drawable.bottom_dialog_background);
            contentView.setBackgroundColor(Color.TRANSPARENT);
            // show the sheet only when button is pressed
        }
    }

    public void showBottomSheetDialog(){
        sheetDialog.show();
    }

    public void dismissBottomSheetDialog(){
        sheetDialog.dismiss();
    }

    public interface OnItemClickListener{
        void onItemClick(Doctor doctor);
    }

    public interface OnAgendarCitaClickListener{
        void onAgendarCita(Doctor doctor);
    }

}
