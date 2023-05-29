package com.example.doctofacil.ui.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctofacil.R;
import com.example.doctofacil.model.Doctor;
import com.example.doctofacil.model.Patient;
import com.example.doctofacil.model.database.DBConnection;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainDoctorActivity extends AppCompatActivity {

    private static final String ARG_DOCTOR = "doctor";
    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_TOKEN = "auth_token";
    ///////

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavController navController;
    private NavigationView navigationView;

    // bundled user id
    public static int user_id;
    public static Doctor doctor;
    private Bundle bundle;

    private DBConnection dbConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

        dbConnection = new DBConnection(getBaseContext());
        setupNavigation();

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",-1);
        Log.i("poncho", "recivedid +++ "+user_id);
        doctor = dbConnection.getDoctorById(user_id);
        Log.i("poncho", "patient name query +++ "+doctor.getName());

        // update navHeader with patient data
        View headerView = navigationView.getHeaderView(0);

        TextView tvFullName = headerView.findViewById(R.id.name);
        TextView tvEmail = headerView.findViewById(R.id.useremail);
        tvFullName.setText(doctor.getName() + " " + doctor.getLastName());
        tvEmail.setText(doctor.getEmail());

        bundle = new Bundle();
        bundle.putSerializable(ARG_DOCTOR, doctor);
        navController.navigate(R.id.doctorProfileFragment, bundle);
    }

    private void setupNavigation() {
        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_doctor);


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
                    case R.id.nav_profile_doc:
                        navController.navigate(R.id.doctorProfileFragment, bundle); break;
                    case R.id.nav_doc_appointments:
                        navController.navigate(R.id.doctorAppointmentsFragment, bundle); break;
                    case R.id.nav_doc_see_patients:
                        navController.navigate(R.id.doctorPatientsFragment, bundle); break;

                    //case R.id.nav_doc_settings:
                    //    Toast.makeText(getBaseContext(), "settings",Toast.LENGTH_SHORT).show(); break;
                    case R.id.nav_doc_logout:
                        logout();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void logout() {
        SharedPreferences sharedPreferences = getBaseContext()
                .getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ARG_TOKEN);
        editor.remove(ARG_USER_ID);
        editor.apply();
        finish(); // end activity
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