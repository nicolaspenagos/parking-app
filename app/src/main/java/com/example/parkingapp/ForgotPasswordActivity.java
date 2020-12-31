/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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

                }else if(event.getAction() == MotionEvent.ACTION_UP){

                }

                break;

        }

        return true;
    }
}