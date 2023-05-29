package com.example.doctofacil.ui.doctor.fragments.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Appointment;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.patient.fragments.adapters.AppointmentsTerminatedAdapter;

import java.util.List;

public class DocAppointmentsTerminatedAdapter extends RecyclerView.Adapter<DocAppointmentsTerminatedAdapter.ViewHolder>{

    private List<Appointment> appointmentList;
    private DocAppointmentsTerminatedAdapter.OnAddRecetaClickListener onAddRecetaClickListener;
    private DBConnection dbConnection;

    private int shortAnimationDuration;

    public DocAppointmentsTerminatedAdapter(List<Appointment> appointmentList,
                                            DocAppointmentsTerminatedAdapter.OnAddRecetaClickListener onAddRecetaClickListener,
                                            DBConnection dbConnection) {
        this.appointmentList = appointmentList;
        this.onAddRecetaClickListener = onAddRecetaClickListener;
        this.dbConnection = dbConnection;
    }

    @NonNull
    @Override
    public DocAppointmentsTerminatedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment_fordoc, parent, false);
        return new DocAppointmentsTerminatedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocAppointmentsTerminatedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
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

        holder.btAddReceta.setOnClickListener(view -> {
            onAddRecetaClickListener.onAddReceta(appointment);
        });


        holder.btCancel.setVisibility(View.GONE);
        holder.btTerminate.setVisibility(View.GONE);
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
        public Button btCancel, btTerminate, btAddReceta;


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
            btCancel = itemView.findViewById(R.id.buttonCancelarCita);
            btTerminate = itemView.findViewById(R.id.buttonTerminateAppointment);
            btAddReceta = itemView.findViewById(R.id.buttonAgregarReceta);



            itemView.setOnClickListener(view -> {
                //Toast.makeText(view.getContext(), "appointment pressed", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public interface OnAddRecetaClickListener {
        void onAddReceta(Appointment appointment);
    }

}
