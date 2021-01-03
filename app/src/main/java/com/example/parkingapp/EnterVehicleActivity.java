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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.parkingapp.model.Vehicle;

/*
 * This class is responsible for enter a vehicle and register its data.
 */
public class EnterVehicleActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    // -------------------------------------
    // XML references
    // -------------------------------------
    private ImageView enterVehicleGoBackButton;
    private EditText ownerNameEditText;
    private EditText ownerPhoneEditText;
    private EditText plate1EditText;
    private EditText plate2EditText;
    private EditText plate3EditText;
    private EditText plate4EditText;
    private EditText plate5EditText;
    private EditText plate6EditText;
    private CheckBox turboCheckBox;
    private CheckBox automovilCheckBox;
    private CheckBox mulaCheckBox;
    private Button nextButton;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private char currentType;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enter_vehicle);

        enterVehicleGoBackButton = findViewById(R.id.enterVehicleGoBackButton);
        ownerNameEditText = findViewById(R.id.ownerNameEditText);
        ownerPhoneEditText = findViewById(R.id.ownerPhoneEditText);
        plate1EditText = findViewById(R.id.plate1EnterVehicleEditText);
        plate2EditText = findViewById(R.id.plate2EnterVehicleEditText);
        plate3EditText = findViewById(R.id.plate3EnterVehicleEditText);
        plate4EditText = findViewById(R.id.plate4EnterVehicleEditText);
        plate5EditText = findViewById(R.id.plate5EnterVehicleEditText);
        plate6EditText = findViewById(R.id.plate6EnterVehicleEditText);
        turboCheckBox = findViewById(R.id.turboCheckBox);
        automovilCheckBox = findViewById(R.id.automovilCheckBox);
        mulaCheckBox = findViewById(R.id.mulaCheckBox);
        nextButton = findViewById(R.id.nextButton);

        enterVehicleGoBackButton.setOnTouchListener(this);
        nextButton.setOnTouchListener(this);

        turboCheckBox.setOnClickListener(this);
        automovilCheckBox.setOnClickListener(this);
        mulaCheckBox.setOnClickListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.enterVehicleGoBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    enterVehicleGoBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    enterVehicleGoBackButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;

            case R.id.nextButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    nextButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                   nextButton.setBackgroundResource(R.drawable.button_background);
                   finish();

                }

                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.mulaCheckBox:

                if(mulaCheckBox.isChecked()){

                    automovilCheckBox.setChecked(false);
                    turboCheckBox.setChecked(false);
                    currentType = Vehicle.MULA;
                }

                break;

            case R.id.automovilCheckBox:

                if(automovilCheckBox.isChecked()){

                    mulaCheckBox.setChecked(false);
                    turboCheckBox.setChecked(false);
                    currentType = Vehicle.AUTOMOVIL;
                }

                break;

            case R.id.turboCheckBox:

                if(turboCheckBox.isChecked()){

                    mulaCheckBox.setChecked(false);
                    automovilCheckBox.setChecked(false);
                    currentType = Vehicle.TURBO;
                }

                break;
        }

    }

}