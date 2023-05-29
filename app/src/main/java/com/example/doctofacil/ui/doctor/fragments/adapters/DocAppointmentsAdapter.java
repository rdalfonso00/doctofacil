package com.example.doctofacil.ui.doctor.fragments.adapters;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;

import java.util.List;

public class DocAppointmentsAdapter extends RecyclerView.Adapter<DocAppointmentsAdapter.ViewHolder>{

    private List<Appointment> appointmentList, terminatedAppointmentsList;
    private RecyclerView terminatedAdapter;
    private DBConnection dbConnection;

    public DocAppointmentsAdapter(List<Appointment> appointmentList, List<Appointment>
            terminatedAppointmentsList, RecyclerView terminatedAdapter, DBConnection dbConnection) {
        this.appointmentList = appointmentList;
        this.terminatedAppointmentsList = terminatedAppointmentsList;
        this.terminatedAdapter = terminatedAdapter;
        this.dbConnection = dbConnection;
    }

    @NonNull
    @Override
    public DocAppointmentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment_fordoc, parent, false);
        return new DocAppointmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocAppointmentsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Appointment appointment = appointmentList.get(position);
        String[] dateSplit = appointment.getStartTime().toString().split(" ")[0].split("-");
        String[] months = {"enero", "febrero", "marzo","abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
        holder.tvFullDateAppointment.setText(String.format("%s de %s de %s",
                dateSplit[2], months[Integer.parseInt(dateSplit[1])], dateSplit[0]));
        Patient patient = dbConnection.getPatientById(appointment.getPatientId());
        //Doctor doctor = dbConnection.getDoctorById(appointment.getDoctorId());
        holder.tvPatName.setText(String.format("%s %s", patient.getName(),patient.getLastName()));
        holder.tvCellphonePat.setText(patient.getPhone());
        holder.tvDayAndHour.setText(appointment.getStartTime().toString());
        holder.tvIsOnline.setText(appointment.isOnline() ? "En línea" : "Presencial");
        holder.etComments.setText(appointment.getComments());
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

        holder.btCancel.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Borrar cita")
                    .setMessage("¿Estas seguro que deseas cancelar esta cita?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked the "Yes" button, perform the action
                            boolean res = dbConnection.updateAppointmentState(appointment.getAppointmentId(), "canceled");
                            if (res){
                                Toast.makeText(view.getContext(), "Cita cancelada con éxito", Toast.LENGTH_SHORT).show();

                                //terminatedAppointmentsList.add(appointmentList.get(position));
                                appointmentList.remove(position);
                                notifyDataSetChanged();
                                //terminatedAdapter.getAdapter().notifyDataSetChanged();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        });

        holder.btTerminate.setOnClickListener(view -> {
            new AlertDialog.Builder(view.getContext())
                    .setTitle("Borrar cita")
                    .setMessage("¿Estas seguro que deseas finalizar esta cita?")
                    .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // User clicked the "Yes" button, perform the action
                            boolean res = dbConnection.updateAppointmentState(appointment.getAppointmentId(), "terminated");
                            if (res){
                                Toast.makeText(view.getContext(), "Cita finalizada con éxito", Toast.LENGTH_SHORT).show();
                                terminatedAppointmentsList.add(appointmentList.get(position));
                                appointmentList.remove(position);
                                notifyDataSetChanged();
                                terminatedAdapter.getAdapter().notifyDataSetChanged();
                            }
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        });

        holder.btAddReceta.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton ibExpandCollapse;

        public ConstraintLayout constraintLayoutDetails;
        public TextView tvFullDateAppointment;
        public TextView tvPatName;
        public TextView tvCellphonePat;
        public TextView tvDayAndHour;
        public TextView tvIsOnline;
        public EditText etComments;
        public Button btCancel, btAddReceta, btTerminate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ibExpandCollapse = itemView.findViewById(R.id.imageButtonExpandCollapse);

            constraintLayoutDetails = itemView.findViewById(R.id.constraintLayoutDetails);
            tvFullDateAppointment = itemView.findViewById(R.id.textViewFullDateAppointment);
            tvPatName = itemView.findViewById(R.id.textViewNombreMedicoApt);
            tvCellphonePat = itemView.findViewById(R.id.textViewNumeroPaciente);
            tvDayAndHour = itemView.findViewById(R.id.textViewDiayHora);
            tvIsOnline = itemView.findViewById(R.id.textViewIsOnline);
            etComments = itemView.findViewById(R.id.editTextComments);
            //btEdit = itemView.findViewById(R.id.buttonCancelarCitaForPat);
            btAddReceta = itemView.findViewById(R.id.buttonAgregarReceta);
            btCancel = itemView.findViewById(R.id.buttonCancelarCita);
            btTerminate = itemView.findViewById(R.id.buttonTerminateAppointment);

            itemView.setOnClickListener(view -> {
                //Toast.makeText(view.getContext(), "appointment pressed", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
