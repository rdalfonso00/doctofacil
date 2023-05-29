package com.example.doctofacil.ui.patient.fragments.adapters;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.database.DBConnection;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>{

    private List<Appointment> appointmentList;
    private DBConnection dbConnection;

    private int shortAnimationDuration;

    public AppointmentsAdapter(List<Appointment> appointmentList, DBConnection dbConnection) {
        this.appointmentList = appointmentList;
        this.dbConnection = dbConnection;
    }

    @NonNull
    @Override
    public AppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pending_appointment, parent, false);
        return new AppointmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        holder.btDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Borrar cita")
                    .setMessage("¿Estas seguro que deseas cancelar esta cita?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked the "Yes" button, perform the action
                            boolean res = dbConnection.deleteAppointment(appointment.getAppointmentId());
                            if (res){
                                Toast.makeText(view.getContext(), "Cita borrada con éxito", Toast.LENGTH_SHORT).show();
                                appointmentList.remove(position);
                                notifyDataSetChanged();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibExpandCollapse = itemView.findViewById(R.id.imageButtonExpandCollapse);

            constraintLayoutDetails = itemView.findViewById(R.id.constraintLayoutDetails);
            tvFullDateAppointment = itemView.findViewById(R.id.textViewFullDateAppointment);
            tvDrName = itemView.findViewById(R.id.textViewNombreMedicoApt);
            tvDrSpecialty = itemView.findViewById(R.id.textViewSpecialityApt);
            tvDayAndHour = itemView.findViewById(R.id.textViewDiayHora);
            tvIsOnline = itemView.findViewById(R.id.textViewIsOnline);
            btDelete = itemView.findViewById(R.id.buttonEliminarCita);

            itemView.setOnClickListener(view -> {
                //Toast.makeText(view.getContext(), "appointment pressed", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
