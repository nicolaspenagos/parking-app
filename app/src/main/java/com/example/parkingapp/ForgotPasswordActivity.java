/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * This class is responsible for recover the password once is needed.
 */
public class ForgotPasswordActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private EditText emailEditText;
    private Button sendButton;
    private ImageView forgottenPasswordGoBackButton;
    private TextView emailErrorTextView;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_forgot_password);

        auth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        sendButton = findViewById(R.id.sendButton);
        forgottenPasswordGoBackButton = findViewById(R.id.forgottenPasswordgoBackButton);
        emailErrorTextView = findViewById(R.id.emailErrorTextView);

        forgottenPasswordGoBackButton.setOnTouchListener(this);
        sendButton.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.forgottenPasswordgoBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    forgottenPasswordGoBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    forgottenPasswordGoBackButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;

            case R.id.sendButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    sendButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    sendButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){

                        String email = emailEditText.getText().toString().trim();
                        if(email!=null && !email.equals("")){

                            emailErrorTextView.setText("");
                            auth.sendPasswordResetEmail(email).addOnCompleteListener(
                                    task -> {
                                        if(task.isSuccessful()){

                                            Toast.makeText(this, "Email de enviado, revise su correo en los próximos minutos para configurar una nueva contraseña.", Toast.LENGTH_LONG).show();
                                            finish();

                                        }else{
                                            Toast.makeText(this,  "Error, inténtelo de nuevo más tarde", Toast.LENGTH_LONG).show();
                                        }

                                    }
                            );

                        }else{
                            emailErrorTextView.setText("El correo no puede estar vacío.");
                        }
                    }

                }

                break;
        }

        return true;
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

}