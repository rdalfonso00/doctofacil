package com.example.doctofacil.ui.patient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.model.database.DBHelper;
import com.example.doctofacil.ui.patient.fragments.PatientAppointmentsFragment;
import com.example.doctofacil.ui.patient.fragments.PatientProfileFragment;
import com.example.doctofacil.ui.patient.fragments.PatientSeeDoctorsFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainPatientActivity extends AppCompatActivity {

    private static final String ARG_PATIENT = "patient";

    ///////

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;

    // bundled user id
    public static int user_id;
    public static Patient patient;
    private Bundle bundle;

    private DBConnection dbConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);
        dbConnection = new DBConnection(getBaseContext());
        setupNavigation();

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",-1);
        Log.i("poncho", "recivedid +++ "+user_id);
        patient = dbConnection.getPatientById(user_id);
        Log.i("poncho", "patient name query +++ "+patient.getName());

        // update navHeader with patient data
        View headerView = navigationView.getHeaderView(0);

        TextView tvFullName = headerView.findViewById(R.id.name);
        TextView tvEmail = headerView.findViewById(R.id.useremail);
        tvFullName.setText(patient.getName() + " " + patient.getLastName());
        tvEmail.setText(patient.getEmail());

        bundle = new Bundle();
        bundle.putSerializable(ARG_PATIENT, patient);
        navController.navigate(R.id.patientProfileFragment, bundle);
    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_patient);


        setSupportActionBar(toolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @org.jetbrains.annotations.NotNull MenuItem item) {

                int id = item.getItemId();
                item.setChecked(true);
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (id)
                {
                    case R.id.nav_profile_pat:
                        navController.navigate(R.id.patientProfileFragment, bundle); break;
                    case R.id.nav_pat_see_doctors:
                        navController.navigate(R.id.patientSeeDoctorsFragment, bundle); break;
                    case R.id.nav_patient_appointments:
                        navController.navigate(R.id.patientAppointmentsFragment, bundle); break;

                    case R.id.nav_pat_settings:
                        Toast.makeText(getBaseContext(), "settings",Toast.LENGTH_SHORT).show(); break;
                    case R.id.nav_pat_logout:
                        Toast.makeText(getBaseContext(), "Logout was pressed :0 o:", Toast.LENGTH_SHORT).show(); break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}