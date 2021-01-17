/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.model.User;
import com.example.parkingapp.model.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will contains the entered vehicles and will have the main functions.
 */
public class HomeActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private ImageView logoutButton;
    private ImageView profileButton;
    private TextView currentUserTextView;
    private ConstraintLayout enterVehicleButton;
    private Button searchButton;
    private EditText plate1EditText;
    private EditText plate2EditText;
    private EditText plate3EditText;
    private EditText plate4EditText;
    private EditText plate5EditText;
    private EditText plate6EditText;
    private GridView vehiclesGridView;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private User currentUser;
    private VehicleAdapter adapter;


    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        isOnline();

        auth =  FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser() == null)
            goToLogin();
        else{

            logoutButton =  findViewById(R.id.logoutButton);
            profileButton = findViewById(R.id.profileButton);
            currentUserTextView = findViewById(R.id.currentUserTextView);
            enterVehicleButton = findViewById(R.id.enterVehicleButton);
            searchButton = findViewById(R.id.searchButton);
            plate1EditText = findViewById(R.id.plate1EditText);
            plate2EditText = findViewById(R.id.plate2EditText);
            plate3EditText = findViewById(R.id.plate3EditText);
            plate4EditText = findViewById(R.id.plate4EditText);
            plate5EditText = findViewById(R.id.plate5EditText);
            plate6EditText = findViewById(R.id.plate6EditText);
            vehiclesGridView = findViewById(R.id.vehiclesGridView);

            logoutButton.setOnTouchListener(this);
            profileButton.setOnTouchListener(this);
            enterVehicleButton.setOnTouchListener(this);
            searchButton.setOnTouchListener(this);

            adapter = new VehicleAdapter();
            vehiclesGridView.setAdapter(adapter);

            recoverUser();
            loadDatabase();
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.logoutButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    logoutButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    logoutButton.setImageResource(R.drawable.go_back_arrow);

                    if(isOnline()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog)
                                .setTitle("Cerrar Sesión")
                                .setMessage("¿Está seguro que desea cerrar sesión?")
                                .setNegativeButton("No", (dialog, id)->{
                                    dialog.dismiss();
                                })
                                .setPositiveButton("Si", (dialog, id)->{

                                    auth.signOut();
                                    goToLogin();

                                });

                        builder.show();

                    }

                }

                break;

            case R.id.profileButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    profileButton.setImageResource(R.drawable.user_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    profileButton.setImageResource(R.drawable.user);

                    if(isOnline()){
                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                    }

                }

                break;

            case R.id.enterVehicleButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    enterVehicleButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    enterVehicleButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){

                        Intent intent = new Intent(this, EnterVehicleActivity.class);
                        intent.putExtra("userId", currentUser.getEmail());
                        intent.putExtra("userName", currentUser.getName());
                        startActivity(intent);

                    }

                }

                break;

            case R.id.searchButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    searchButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    searchButton.setBackgroundResource(R.drawable.button_background);

                }

                break;
        }
        return true;
    }


    // -------------------------------------
    // User Authentication
    // -------------------------------------
    private void goToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


    private void recoverUser() {

        if(auth.getCurrentUser() != null){

            String id = auth.getCurrentUser().getUid();
            database.getReference().child("users").child(id).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                           currentUser =  snapshot.getValue(User.class);
                           currentUserTextView.setText(currentUser.getName());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }

    }

    // -------------------------------------
    // Connectivity state
    // -------------------------------------
    public boolean isOnline(){

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {

            Intent intent =  new Intent(this, NoInternetActivity.class);
            startActivity(intent);
            finish();
            return false;

        }

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void loadDatabase() {

        DatabaseReference ref = database.getReference().child("currentVehicles");

        ref.addValueEventListener(

                new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {

                        adapter.clear();
                        for(DataSnapshot child: data.getChildren()){

                            Vehicle vehicle = child.getValue(Vehicle.class);
                            adapter.addVehicle(vehicle);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }
        );
    }

}