package com.example.doctofacil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.doctofacil.model.User;
import com.example.doctofacil.model.database.DBConnection;
import com.example.doctofacil.ui.doctor.MainDoctorActivity;
import com.example.doctofacil.ui.patient.MainPatientActivity;

public class MainActivity extends AppCompatActivity {

    private static final String ARG_USER_ID = "user_id";
    private static final String ARG_TOKEN = "auth_token";

    private AppBarConfiguration appBarConfiguration;

    private NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                // Set the ActionBar title based on the current destination
                getSupportActionBar().setTitle(destination.getLabel());
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt(ARG_USER_ID, -1);
        String authToken = sharedPreferences.getString(ARG_TOKEN, null);

        DBConnection dbConnection = new DBConnection(getBaseContext());

        if (userId != -1 && authToken != null) {
            // User is logged in, bypass login and navigate to the corresponding home screen
            User user = dbConnection.getUserRoleById(userId);
            Intent intent;
            Bundle bundle = new Bundle();
            if (user.getRole().equals("doctor")) {
                intent = new Intent(this, MainDoctorActivity.class);
            } else {
                intent = new Intent(this, MainPatientActivity.class);
            }
            intent.putExtra("user_id", user.getUser_id());
            startActivity(intent);

        } else {
            // User is not logged in, do nothing, continue ...
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(R.string.doctofacil);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}