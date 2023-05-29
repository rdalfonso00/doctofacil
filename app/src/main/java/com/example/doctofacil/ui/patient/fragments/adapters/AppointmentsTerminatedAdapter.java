package com.example.doctofacil.ui.patient.fragments.adapters;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.database.DBConnection;

import java.util.List;

public class AppointmentsTerminatedAdapter extends RecyclerView.Adapter<AppointmentsTerminatedAdapter.ViewHolder>{


    private List<Appointment> appointmentList;
    private AppointmentsTerminatedAdapter.OnVerRecetaClickListener onVerRecetaClickListener;
    private DBConnection dbConnection;

    private int shortAnimationDuration;

    public AppointmentsTerminatedAdapter(List<Appointment> appointmentList,
                                         AppointmentsTerminatedAdapter.OnVerRecetaClickListener onVerRecetaClickListener,
                                         DBConnection dbConnection) {
        this.appointmentList = appointmentList;
        this.onVerRecetaClickListener = onVerRecetaClickListener;
        this.dbConnection = dbConnection;
    }

    @NonNull
    @Override
    public AppointmentsTerminatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment_forpat, parent, false);
        return new AppointmentsTerminatedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsTerminatedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Appointment appointment = appointmentList.get(position);
        String[] dateSplit = appointment.getStartTime().toString().split(" ")[0].split("-");
        String[] months = {"enero", "febrero", "marzo","abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        holder.tvFullDateAppointment.setText(String.format("%s de %s de %s",
                dateSplit[2], months[Integer.parseInt(dateSplit[1])], dateSplit[0]));
        Doctor doctor = dbConnection.getDoctorById(appointment.getDoctorId());
        holder.tvDrName.setText(String.format("%s %s", doctor.getName(),doctor.getLastName()));
        holder.tvDayAndHour.setText(appointment.getStartTime().toString());
        holder.tvDrSpecialty.setText(doctor.getSpecialty());
        holder.tvIsOnline.setText(appointment.isOnline() ? "En línea" : "Presencial");
        holder.constraintLayoutDetails.setVisibility(View.GONE);
        // expand/collapse when pressed
        holder.ibExpandCollapse.setOnClickListener(view -> {
            if (holder.constraintLayoutDetails.getVisibility() == View.GONE) {

                holder.constraintLayoutDetails.setVisibility(View.VISIBLE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        0,
                        holder.constraintLayoutDetails.getHeight(),
                        0);
                animate.setDuration(500);
                animate.setFillAfter(true);
                holder.constraintLayoutDetails.startAnimation(animate);
            } else {
                //Animation slideUpAnimation = AnimationUtils.loadAnimation(view.getContext(), R.anim.slide_up);
                //holder.constraintLayoutDetails.startAnimation(slideUpAnimation);
                holder.constraintLayoutDetails.setVisibility(View.GONE);
                TranslateAnimation animate = new TranslateAnimation(
                        0,
                        0,
                        0,
                        holder.constraintLayoutDetails.getHeight());
                animate.setDuration(5000);
                animate.setFillAfter(true);
                holder.constraintLayoutDetails.startAnimation(animate);
            }
        });

        holder.btDelete.setVisibility(View.GONE);

        holder.btReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "RECETAAA", Toast.LENGTH_SHORT).show();
                onVerRecetaClickListener.onVerReceta(appointment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ibExpandCollapse;

        public ConstraintLayout constraintLayoutDetails;
        public TextView tvFullDateAppointment;
        public TextView tvDrName;
        public TextView tvDrSpecialty;
        public TextView tvDayAndHour;
        public TextView tvIsOnline;
        public Button btDelete;
        public Button btReceta;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibExpandCollapse = itemView.findViewById(R.id.imageButtonExpandCollapse);

            constraintLayoutDetails = itemView.findViewById(R.id.constraintLayoutDetails);
            tvFullDateAppointment = itemView.findViewById(R.id.textViewFullDateAppointment);
            tvDrName = itemView.findViewById(R.id.textViewNombreMedicoApt);
            tvDrSpecialty = itemView.findViewById(R.id.textViewSpecialityApt);
            tvDayAndHour = itemView.findViewById(R.id.textViewDiayHora);
            tvIsOnline = itemView.findViewById(R.id.textViewIsOnline);
            btDelete = itemView.findViewById(R.id.buttonCancelarCitaForPat);
            btReceta = itemView.findViewById(R.id.buttonVerReceta);

            itemView.setOnClickListener(view -> {
                //Toast.makeText(view.getContext(), "appointment pressed", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public interface OnVerRecetaClickListener{
        void onVerReceta(Appointment appointment);
    }
}
