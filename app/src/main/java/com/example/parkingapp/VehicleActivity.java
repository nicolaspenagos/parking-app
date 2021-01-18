/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.model.Time;
import com.example.parkingapp.model.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will show the vehicle information.
 */
public class VehicleActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseDatabase database;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private TextView ownerTextView;
    private TextView ownerPhoneTextView;
    private TextView plateTextView;
    private TextView typeTextView;
    private TextView responsableAtEnterTextView;
    private TextView timeTextView;
    private Button goBackButton;
    private Button removeVehicleButton;
    private ImageView goBackArrowButton;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private String id;
    private Vehicle currentVehicle;
    private boolean killThread;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vehicle);

        if(isOnline()){

            id = getIntent().getExtras().getString("id");
            killThread = false;

            database = FirebaseDatabase.getInstance();

            ownerTextView = findViewById(R.id.vehicleOwnerTextView);
            ownerPhoneTextView = findViewById(R.id.vehicleOwnerPhoneTextView);
            plateTextView = findViewById(R.id.vehiclePlatesTextView);
            typeTextView = findViewById(R.id.vehicleTypeTextView);
            responsableAtEnterTextView = findViewById(R.id.enterVehicleResponsableTextView);
            timeTextView = findViewById(R.id.vehicleTimeTextView);
            goBackButton = findViewById(R.id.vehicleInfoGoBackButton);
            removeVehicleButton = findViewById(R.id.removeVehicleButton);
            goBackArrowButton = findViewById(R.id.vehicleGoBackArrowButton);

            goBackArrowButton.setOnTouchListener(this);
            goBackButton.setOnTouchListener(this);
            removeVehicleButton.setOnTouchListener(this);

            loadDataBase();

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.vehicleGoBackArrowButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goBackArrowButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    goBackArrowButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;

            case R.id.vehicleInfoGoBackButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goBackButton.setBackgroundResource(R.drawable.button_background);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    goBackButton.setBackgroundResource(R.drawable.pressed_button_background);
                    finish();

                }

                break;

            case R.id.removeVehicleButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    removeVehicleButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    removeVehicleButton.setBackgroundResource(R.drawable.button_background);

                    Intent intent = new Intent(this, RemoveVehicleActivity.class);
                    startActivity(intent);
                    finish();

                }

                break;

        }

        return true;
    }

    @Override
    public void finish() {
        killThread = true;
        super.finish();
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

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void loadDataBase(){

        DatabaseReference ref = database.getReference().child("currentVehicles").child(id);

        ref.addValueEventListener(

                new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        currentVehicle = snapshot.getValue(Vehicle.class);
                        updateInfo();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }

        );

    }

    public void updateInfo(){

        ownerTextView.setText(currentVehicle.getOwnerName());
        ownerPhoneTextView.setText(currentVehicle.getOwnerPhone());
        plateTextView.setText(currentVehicle.getPlate().charAt(0)+currentVehicle.getPlate().charAt(1)+currentVehicle.getPlate().charAt(2)+"-"+currentVehicle.getPlate().charAt(3)+currentVehicle.getPlate().charAt(4)+currentVehicle.getPlate().charAt(5));
        typeTextView.setText(currentVehicle.getType().toUpperCase());
        responsableAtEnterTextView.setText(currentVehicle.getResponsableAtEnter().toUpperCase());
        timeThread();

    }

    public void timeThread(){

        new Thread(

                ()->{

                    while (!killThread){

                        try {

                            runOnUiThread(

                                    ()->{
                                        timeTextView.setText(Time.getTimeDayHourMinuteSecond(currentVehicle.getEnterTime(), System.currentTimeMillis()));
                                    }

                            );
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }

        ).start();

    }

    /*
    private void loadDatabase() {

        DatabaseReference ref = database.getReference().child("currentVehicles");

        ref.addValueEventListener(

                new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot data) {

                        adapter.clear();
                        for(DataSnapshot child: data.getChildren()){

                            Vehicle vehicle = child.getValue(Vehicle.class);
                            adapter.addVehicle(vehicle);

                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }
        );
    }
     */

}