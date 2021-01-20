/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.model.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

/*
 * This class will show the data information before the confirmation of the vehicle.
 */
public class EnterVehicleConfirmationActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private TextView nameConfirmationTextView;
    private TextView telConfirmationTextView;
    private TextView plateConfirmationTextView;
    private TextView typeConfirmationTextView;
    private Button goBackConfirmationButton;
    private Button confirmationButton;
    private ImageView goBackArrowConfirmationButton;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private String currentName;
    private String currentPhone;
    private String currentPlate;
    private String currentType;
    private boolean isNew;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_enter_vehicle_confirmation);

        nameConfirmationTextView = findViewById(R.id.nameConfirmationTextView);
        telConfirmationTextView = findViewById(R.id.phoneConfirmationEditText);
        plateConfirmationTextView = findViewById(R.id.plateConfirmationTextView);
        typeConfirmationTextView = findViewById(R.id.typeConfirmationTextView);
        goBackConfirmationButton = findViewById(R.id.confirmationGoBackButton);
        confirmationButton = findViewById(R.id.confirmationButton);
        goBackArrowConfirmationButton = findViewById(R.id.goBackArrowConfirmationButton);

        Bundle extras = getIntent().getExtras();

        nameConfirmationTextView.setText(extras.getString("ownerName"));
        telConfirmationTextView.setText(extras.getString("ownerPhone"));
        currentPhone = extras.getString("ownerPhone");
        currentName = extras.getString("ownerName");

        String plate = extras.getString("plate");
        String plateToShow = "";
        currentPlate = plate;

        isNew = false;

        for (int i = 0; i<plate.length(); i++){

            if(i==3){
                plateToShow+="-"+plate.charAt(i);
            }else{
                plateToShow+=""+plate.charAt(i);
            }

        }

        plateConfirmationTextView.setText(plateToShow);

        char type = extras.getChar("type");
        String typeToShow = "";

        if(type == Vehicle.TURBO){
            typeToShow = "TURBO";
        }else if(type == Vehicle.AUTOMOVIL){
            typeToShow = "AUTOMÓVIL";
        }else if(type == Vehicle.MULA){
            typeToShow = "MULA";
        }
        currentType = typeToShow;
        typeConfirmationTextView.setText(typeToShow);

        confirmationButton.setOnTouchListener(this);
        goBackConfirmationButton.setOnTouchListener(this);
        goBackArrowConfirmationButton.setOnTouchListener(this);

        database = FirebaseDatabase.getInstance();

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.confirmationButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    confirmationButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    confirmationButton.setBackgroundResource(R.drawable.button_background);
                    verify();

                }

                break;

            case R.id.confirmationGoBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackConfirmationButton.setBackgroundResource(R.drawable.button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackConfirmationButton.setBackgroundResource(R.drawable.pressed_button_background);
                    finish();

                }

                break;

            case R.id.goBackArrowConfirmationButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackArrowConfirmationButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackArrowConfirmationButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;
        }

        return true;
    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void verify(){

        DatabaseReference ref = database.getReference().child("currentVehicles").child(currentPlate);


        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        Vehicle vehicle = snapshot.getValue(Vehicle.class);
                        if (vehicle == null){
                            isNew = true;
                        }
                        enterVehicle();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                       error();
                    }
                }
        );

    }

    public void enterVehicle(){

        DatabaseReference ref = database.getReference().child("currentVehicles").child(currentPlate);

        if(isNew){

            Vehicle vehicle = new Vehicle( currentPlate, UUID.randomUUID().toString(),currentName, currentPhone, currentType, getIntent().getExtras().getString("userName"), getIntent().getExtras().getString("userId"), System.currentTimeMillis());
            vehicle.setResponsableAtEnterId(getIntent().getExtras().getString("userId"));

            ref.setValue(vehicle).addOnCompleteListener(

                    task -> {
                        if(task.isSuccessful()){

                            Toast.makeText(this, "Vehículo ingresado.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();

                        }else{

                            Toast.makeText(this, "Error, intentelo más tarde.", Toast.LENGTH_LONG).show();
                            finish();

                        }
                    }

            );

        }else{
            Toast.makeText(this, "Este vehículo ya está ingresado.", Toast.LENGTH_LONG).show();
        }


    }

    public void error(){
        Toast.makeText(this, "Error, intentelo más tarde.", Toast.LENGTH_LONG).show();
    }

}
