/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 *
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * *~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will show the user information and will allow to change the password
 */
public class ProfileActivity extends AppCompatActivity implements View.OnTouchListener {

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private TextView usernameTextView;
    private TextView wholeNameTextView;
    private TextView emailTextView;
    private ConstraintLayout changePasswordButton;
    private ImageView goBackButton;
    private ImageView profileLogoutButton;

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
        setContentView(R.layout.activity_profile);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if (auth.getCurrentUser() == null)
            goToLogin();
        else {

            usernameTextView = findViewById(R.id.usernameTextView);
            wholeNameTextView = findViewById(R.id.wholeNameTextView);
            emailTextView = findViewById(R.id.emailTextView);
            changePasswordButton = findViewById(R.id.changePasswordButton);
            goBackButton = findViewById(R.id.goBackButton);
            profileLogoutButton = findViewById(R.id.profileLogoutButton);

            changePasswordButton.setOnTouchListener(this);
            goBackButton.setOnTouchListener(this);
            profileLogoutButton.setOnTouchListener(this);

            recoverUser();

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {

            case R.id.changePasswordButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    changePasswordButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    changePasswordButton.setBackgroundResource(R.drawable.button_background);

                }

                break;

            case R.id.goBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackButton.setImageResource(R.drawable.go_back_arrow);
                    Intent intent =  new Intent(this, HomeActivity.class);
                    startActivity(intent);
                    finish();

                }

                break;

            case R.id.profileLogoutButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    profileLogoutButton.setImageResource(R.drawable.log_out_pressed_icon);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    profileLogoutButton.setImageResource(R.drawable.log_out_icon);

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

                break;

        }

        return true;
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

        if (auth.getCurrentUser() != null) {

            String id = auth.getCurrentUser().getUid();
            database.getReference().child("users").child(id).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            currentUser = snapshot.getValue(User.class);
                            updateInfo();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }

    }

    // -------------------------------------
    // METHODS
    // -------------------------------------
    public void updateInfo(){

        String wholeName = currentUser.getName();
        String name = wholeName.split(" ")[0];
        String email = currentUser.getEmail();

        usernameTextView.setText("Hola, "+name);
        wholeNameTextView.setText(wholeName);
        emailTextView.setText(email);

    }

}
