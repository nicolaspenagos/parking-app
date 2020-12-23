/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will contains the entered vehicles and will have the main functions.
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

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

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private User currentUser;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

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

            logoutButton.setOnClickListener(this);

            recoverUser();
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.logoutButton:

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

                break;

        }

    }

    // -------------------------------------
    // USER AUTHENTICATION
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

        }else{

        }
    }

}