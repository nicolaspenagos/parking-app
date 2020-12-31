/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
 * This class is responsible for changing the password once the user needs.
 */
public class ChangePasswordActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseUser user;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private ImageView changePasswordGoBackButton;
    private EditText newPasswordEditText;
    private EditText reNewPasswordEditText;
    private Button changePasswordButton;
    private TextView changePasswordErrorTextView;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_password);

        changePasswordGoBackButton = findViewById(R.id.goBackChangePasswordButton);
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        reNewPasswordEditText = findViewById(R.id.reNewPasswordEditText);
        changePasswordButton = findViewById(R.id.changePasswordButton);
        changePasswordErrorTextView = findViewById(R.id.changePasswordErrorText);

        changePasswordGoBackButton.setOnTouchListener(this);
        changePasswordButton.setOnTouchListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.goBackChangePasswordButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    changePasswordGoBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    changePasswordGoBackButton.setImageResource(R.drawable.go_back_arrow);
                    finish();


                }

                break;

            case R.id.changePasswordButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    changePasswordButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    changePasswordButton.setBackgroundResource(R.drawable.button_background);

                    String password = newPasswordEditText.getText().toString().trim();
                    String rePassword = reNewPasswordEditText.getText().toString().trim();

                    if(password!=null && !password.equals("") && rePassword!=null && !rePassword.equals("")){

                        changePasswordErrorTextView.setText("");
                        if (password.equals(rePassword)) {

                            user.updatePassword(password).addOnCompleteListener(
                                    task -> {

                                        if(task.isSuccessful()){

                                            Toast.makeText(this, "Contraseña cambiada exitosamente.", Toast.LENGTH_LONG).show();
                                            user.reload().addOnCompleteListener(
                                                    reloadTask ->{
                                                        finish();
                                                    }
                                            );

                                        }else{
                                            Toast.makeText(this, "Error, inicie sesión nuevamente y vuelva a intentarlo.", Toast.LENGTH_LONG).show();
                                        }

                                    }
                            );

                        }else{
                            changePasswordErrorTextView.setText("Las contraseñas no son iguales");
                        }

                    }else{
                        changePasswordErrorTextView.setText("No pueden haber campos vacíos");
                    }


                }

                break;

        }

        return true;

    }

}