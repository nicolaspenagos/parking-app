/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.Toast;

import com.example.parkingapp.model.Vehicle;

import org.w3c.dom.Text;

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

        addTextCheckers();

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

                    String ownerName = ownerNameEditText.getText().toString();
                    String ownerPhone = ownerPhoneEditText.getText().toString();
                    String plate1 = plate1EditText.getText().toString();
                    String plate2 = plate2EditText.getText().toString();
                    String plate3 = plate3EditText.getText().toString();
                    String plate4 = plate4EditText.getText().toString();
                    String plate5 = plate5EditText.getText().toString();
                    String plate6 = plate6EditText.getText().toString();

                    char type=' ';

                    if(mulaCheckBox.isChecked()){
                        type = Vehicle.MULA;
                    }else if(automovilCheckBox.isChecked()){
                        type = Vehicle.AUTOMOVIL;
                    }else if(turboCheckBox.isChecked()){
                        type = Vehicle.TURBO;
                    }

                    if(verifyVehicleData(ownerName, ownerPhone, plate1, plate2, plate3, plate4, plate5, plate6)){

                        Intent intent = new Intent(this, EnterVehicleConfirmationActivity.class);

                        intent.putExtra("ownerName", ownerName);
                        intent.putExtra("ownerPhone", ownerPhone);
                        intent.putExtra("plate", plate1+plate2+plate3+plate4+plate5+plate6);
                        intent.putExtra("type", type);

                        startActivityForResult(intent, 1);

                    }else{
                        Toast.makeText(this, "Todos los campos deben ser diligenciados para continuar.", Toast.LENGTH_LONG).show();
                    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){

        }

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void addTextCheckers(){

        ownerNameEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        ownerPhoneEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        plate4EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        plate5EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        plate6EditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        plate1EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate1EditText.setText(string);
                        }

                        if(!string.equals(string.toUpperCase())){
                            string = string.toUpperCase();
                            plate1EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isLetter(character)){
                                plate1EditText.getText().clear();
                            }
                        }

                    }

                }
        );

        plate2EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate2EditText.setText(string);
                        }

                        if(!string.equals(string.toUpperCase())){
                            string = string.toUpperCase();
                            plate2EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isLetter(character)){
                                plate2EditText.getText().clear();
                            }
                        }
                    }

                }
        );

        plate3EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate3EditText.setText(string);
                        }

                        if(!string.equals(string.toUpperCase())){
                            string = string.toUpperCase();
                            plate3EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isLetter(character)){
                                plate3EditText.getText().clear();
                            }
                        }
                    }

                }
        );

        plate4EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate4EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isDigit(character)){
                                plate4EditText.getText().clear();
                            }
                        }
                    }

                }
        );

        plate5EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate5EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isDigit(character)){
                                plate5EditText.getText().clear();
                            }
                        }
                    }

                }
        );

        plate6EditText.addTextChangedListener(

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

                        if(string.length()>1){
                            string = ""+string.charAt(0);
                            plate6EditText.setText(string);
                        }

                        if(string.length()>0){
                            char character = string.charAt(0);
                            if(!Character.isDigit(character)){
                                plate6EditText.getText().clear();
                            }
                        }
                    }

                }
        );


    }

    public boolean verifyVehicleData(String ownerName, String ownerPhone, String plate1, String plate2, String plate3, String plate4, String plate5, String plate6){

        boolean checkBoxesOk = !(!mulaCheckBox.isChecked() && !automovilCheckBox.isChecked() && !turboCheckBox.isChecked());
       return ownerName!=null && !ownerName.equals("") && ownerPhone!=null && !ownerPhone.equals("") && plate1!=null && !plate1.equals("") && plate2!=null && !plate2.equals("") && plate3!=null && !plate3.equals("") && plate4!=null && !plate4.equals("") && plate5!=null && !plate5.equals("") && plate6!=null && !plate6.equals("") && checkBoxesOk;

    }

}