/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
 * This class will allow to go to the whole record of the database.
 */
public class RecordActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // XML references
    // -------------------------------------
    private Button loadButton;
    private EditText day;
    private EditText month;
    private EditText year;
    private ImageView goBackButton;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);

        loadButton = findViewById(R.id.loadDataButton);
        goBackButton = findViewById(R.id.dataGoBackButton);
        day = findViewById(R.id.dayEditText);
        month = findViewById(R.id.monthEditText);
        year = findViewById(R.id.yearEditText);

        loadButton.setOnTouchListener(this);
        goBackButton.setOnTouchListener(this);

        addDateCheckers();

    }

    public void addDateCheckers(){

        day.setInputType(InputType.TYPE_CLASS_NUMBER);
        month.setInputType(InputType.TYPE_CLASS_NUMBER);
        year.setInputType(InputType.TYPE_CLASS_NUMBER);

        day.addTextChangedListener(

                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String string = s.toString();

                        if (string.length() > 0) {
                            char character = string.charAt(0);
                            if (Integer.parseInt(string)<1 || Integer.parseInt(string)>31) {
                                day.getText().clear();
                            }
                        }

                    }

                }
        );

        month.addTextChangedListener(

                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String string = s.toString();

                        if (string.length() > 0) {
                            char character = string.charAt(0);
                            if (Integer.parseInt(string)<1 || Integer.parseInt(string)>12) {
                                month.getText().clear();
                            }
                        }

                    }

                }
        );

        year.addTextChangedListener(

                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String string = s.toString();

                        if (string.length() > 0) {
                            char character = string.charAt(0);
                            if (Integer.parseInt(string)<1 || Integer.parseInt(string)>2100) {
                                year.getText().clear();
                            }
                        }

                    }

                }
        );


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.dataGoBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackButton.setImageResource(R.drawable.go_back_arrow);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackButton.setImageResource(R.drawable.go_back_arrow_pressed);



                }

                break;

            case R.id.loadDataButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                   loadButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    loadButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){



                    }

                }

                break;
        }

        return false;
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