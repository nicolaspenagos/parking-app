/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/*
 * This class is responsible for logging in each user that has already been registered in the database by the admin.
 */
public class LoginActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView errorTextView;
    private TextView forgotPasswordTextView;
    private TextView recoverPasswordLink;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        errorTextView =  findViewById(R.id.errorTextView);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        recoverPasswordLink =  findViewById(R.id.recoverPassworLink);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        loginButton.setOnTouchListener(this);

        forgotPasswordTextView.setOnClickListener(this);
        recoverPasswordLink.setOnClickListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.loginButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    loginButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    loginButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){

                        String password = passwordEditText.getText().toString().trim();
                        String email = emailEditText.getText().toString().trim();

                        if(password != null && !password.equals("") && email != null && !email.equals("")){

                            errorTextView.setText("");

                            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                                    task -> {
                                        if(task.isSuccessful()){

                                            Intent intent = new Intent(this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();

                                        }else{
                                            Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                            );

                        }else{

                            if( (password == null || password.equals("")) && (email == null || email.equals(""))){
                                errorTextView.setText("El correo y la contraseña están vacíos");
                            }else if( password == null || password.equals("") ){
                                errorTextView.setText("La contraseña ésta vacía");
                            }else{
                                errorTextView.setText("El correo ésta vacío");
                            }

                        }
                    }

                }

                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.forgotPasswordTextView:
                goToForgotPasswordActivity();
                break;

            case R.id.recoverPassworLink:
                goToForgotPasswordActivity();
                break;

        }

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void goToForgotPasswordActivity(){

        if(isOnline()){

            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);

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

}