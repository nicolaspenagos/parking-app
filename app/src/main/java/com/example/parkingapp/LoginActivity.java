/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        loginButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.loginButton:

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

                    if(password == null || password.equals("") && email == null || email.equals("")){
                        errorTextView.setText("El correo y la contraseña están vacíos");
                    }else if( password == null || password.equals("") ){
                        errorTextView.setText("La contraseña ésta vacía");
                    }else{
                        errorTextView.setText("El correo ésta vacío");
                    }

                }

                break;
        }
    }
}