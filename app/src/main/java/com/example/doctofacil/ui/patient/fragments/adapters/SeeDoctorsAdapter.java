package com.example.doctofacil.ui.patient.fragments.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;

import java.util.List;

public class SeeDoctorsAdapter extends RecyclerView.Adapter<SeeDoctorsAdapter.ViewHolder>{

    private List<Doctor> doctorList;

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
        // end bottomSheetDialog agendarCita behaviour

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: go to agendar cita
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

        public ViewHolder(@NonNull View view) {
            super(view);
            doctorImage = view.findViewById(R.id.icon_doctor_seedoc);
            doctorName = view.findViewById(R.id.doctor_name_seedocs);
            doctorSpeciality = view.findViewById(R.id.speciality_seedoc);

            //TODO: setup bottomsheetdialog here
        }
    }
}
