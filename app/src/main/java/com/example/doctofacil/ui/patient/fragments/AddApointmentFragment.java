package com.example.doctofacil.ui.patient.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.customviews.DatePickerFragment;
import com.example.doctofacil.ui.customviews.ErrorDialogFragment;
import com.example.doctofacil.ui.customviews.RegistrationResultDialog;
import com.example.doctofacil.utils.DateTimeTranslator;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddApointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddApointmentFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PATIENT = "patient";
    private static final String ARG_DOCTOR_FOR_PATIENT = "doc_for_patient";

    private TextView textViewName;
    private EditText editTextDateAppointment;
    private LinearLayout linearLayout;
    private LinearLayout linearLayoutNumberPicker;
    private NumberPicker numberPickerHour;
    //private NumberPicker numberPickerAMPM;
    private CheckBox checkBox;
    private EditText editTextComentarios;
    private Button buttonAgendarCita;

    private Patient patient;
    private Doctor doctor;

    private DBConnection dbConnection;

    public AddApointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddApointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddApointmentFragment newInstance() {
        AddApointmentFragment fragment = new AddApointmentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            patient = (Patient) getArguments().getSerializable(ARG_PATIENT);
            doctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR_FOR_PATIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_apointment, container, false);

        dbConnection = new DBConnection(getContext());

        textViewName = view.findViewById(R.id.textViewFullNameAppoint);
        editTextDateAppointment = view.findViewById(R.id.editTextDateAppoint);
        linearLayout = view.findViewById(R.id.linearLayout);
        linearLayoutNumberPicker = view.findViewById(R.id.linearLayoutNumberPicker);
        numberPickerHour = view.findViewById(R.id.numPickerHour);
        //numberPickerAMPM = view.findViewById(R.id.numPickerAmPm);
        checkBox = view.findViewById(R.id.checkBox);
        editTextComentarios = view.findViewById(R.id.editTextComentarios);
        buttonAgendarCita = view.findViewById(R.id.buttonAgendarCitaBottom);

        textViewName.setText(doctor.getName() + " " + doctor.getLastName());


        linearLayout.setOnClickListener(view1 -> {
            showDatePickerDialog(editTextDateAppointment);
        });


        /** TODO: change to retrieve hours from database
        initAllPicker(doctor.getMinHour, doctor,getMaxHour);
         */
        Calendar calendar = Calendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int minHour = 8;
        int maxHour = 17;

        String appointmentDate1 = editTextDateAppointment.getText().toString();
        if (isDateToday(appointmentDate1)) {
            initAllPicker(currentHour + 1, maxHour);
        }
        else {
            initAllPicker(minHour, maxHour);
        }

        editTextDateAppointment.setOnClickListener(view1 -> {
            showDatePickerDialog(editTextDateAppointment);
        });

        buttonAgendarCita.setOnClickListener(view12 -> {
            // validar entradas (comentarios no necesario)
            String appointmentDate = editTextDateAppointment.getText().toString();
            if (appointmentDate == null){
                showErrorDialog("Por favor, ingresa una fecha para tu cita");
            }
            boolean isAppointmentOnline = checkBox.isChecked();
            String comments = editTextComentarios.getText().toString();
            int hour = numberPickerHour.getValue();

            String appointmentTime = ( (hour < 10 ? "0" + hour : hour + "") + "00");

            Timestamp appointmentTimestap = DateTimeTranslator.translateToTimestamp(appointmentDate, appointmentTime);

            Timestamp appointmentEndTimestap = new Timestamp(appointmentTimestap.getTime() + TimeUnit.HOURS.toMillis(1));
            // validar hora disponible TODO: revalidate
            boolean isDoctorAviable = dbConnection.isDoctorAvailable(doctor.getUser_id(), appointmentTimestap, appointmentEndTimestap);
            //boolean isDoctorAviable = dbConnection.isAppointmentTimeOverlapping(doctor.getUser_id(), appointmentTimestap.getTime(), appointmentEndTimestap.getTime());
            // agregar cita

            if (isDoctorAviable){
                long res = dbConnection.addAppointment(
                        patient.getUser_id(),
                        doctor.getUser_id(),
                        appointmentTimestap,
                        appointmentEndTimestap,
                        comments,
                        isAppointmentOnline
                );
                if (res == -1){ // error
                    showRegistrationResultDialog(false, "Hubo un error en el registro, intente más tarde");
                }else{
                    showRegistrationResultDialog(true, "Registro de cita realizado con éxito");
                    Navigation.findNavController(requireView()).popBackStack();
                }

            } else {
                showErrorDialog("El doctor no se encuentra disponible en el horario marcado.");
            }
        });

        return view;
    }

    private void initPicker(int min, int max, NumberPicker p) {
        p.setMinValue(min);
        p.setMaxValue(max);
        p.setFormatter(i -> String.format("%02d", i));
    }

    private void initPickerWithString(int min, int max, NumberPicker p, String[] str) {
        p.setMinValue(min);
        p.setMaxValue(max);
        p.setDisplayedValues(str);
    }

    private void initAllPicker(int minHour, int maxHour) {
        String[] str = new String[]{"AM", "PM"};
        initPicker(minHour, maxHour, numberPickerHour);
        //initPickerWithString(0, (str.length - 1), numberPickerAMPM, str);
    }


    private void showDatePickerDialog(final EditText editTextDate) {
        // Get the current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Create a DatePickerDialog with today's date as the initial selection
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Date selection callback
                    String selectedDate = twoDigits(selectedDay) + "/" + twoDigits(selectedMonth + 1) + "/" + selectedYear;
                    editTextDate.setText(selectedDate);
                    // Handle the selected date here


                    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minHour = 8;
                    int maxHour = 17;
                    String appointmentDate = editTextDateAppointment.getText().toString();
                    if (isDateToday(appointmentDate)) {
                        initAllPicker(currentHour + 1, maxHour);
                    }
                    else {
                        initAllPicker(minHour, maxHour);
                    }
                },
                year, month, day
        ) {
            @Override
            public void onDateChanged(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                super.onDateChanged(view, selectedYear, selectedMonth, selectedDay);

                // Get the current date
                Calendar currentDate = Calendar.getInstance();
                currentDate.set(Calendar.HOUR_OF_DAY, 0);
                currentDate.set(Calendar.MINUTE, 0);
                currentDate.set(Calendar.SECOND, 0);
                currentDate.set(Calendar.MILLISECOND, 0);

                // Create a selected date Calendar instance
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, selectedMonth, selectedDay, 0, 0, 0);
                selectedDate.set(Calendar.MILLISECOND, 0);

                // Disable previous dates
                if (selectedDate.before(currentDate)) {
                    view.init(year, month, day, this);
                }

            }
        };

        // Set the minimum selectable date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }

    private String twoDigits(int n) {
        return (n<=9) ? ("0"+n) : String.valueOf(n);
    }

    public boolean isDateToday(String dateString) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = dateFormat.parse(dateString);
            // Get the current date
            Date currentDate = new Date();
            // Compare the dates
            return dateFormat.format(date).equals(dateFormat.format(currentDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showRegistrationResultDialog(boolean success, String message) {
        RegistrationResultDialog dialog = RegistrationResultDialog.newInstance(success, message);
        dialog.show(getParentFragmentManager(), "registration_result_dialog");
    }

    private void showErrorDialog(String message) {
        ErrorDialogFragment dialogFragment = ErrorDialogFragment.newInstance(message);
        dialogFragment.show(getParentFragmentManager(), "error_dialog");
    }

}