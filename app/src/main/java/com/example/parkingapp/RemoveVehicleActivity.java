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
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.model.Ticket;
import com.example.parkingapp.model.User;
import com.example.parkingapp.model.Vehicle;
import com.example.parkingapp.utils.Payment;
import com.example.parkingapp.utils.Time;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will allow to remove a vehicle from the parking.
 */
public class RemoveVehicleActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseDatabase database;
    private FirebaseAuth auth;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private ImageView goArrowBackButton;
    private TextView ticketIdTextView;
    private TextView ownerNameTextView;
    private TextView ownerPhoneTextView;
    private TextView platesTextView;
    private TextView typeTextView;
    private TextView enterDateTextView;
    private TextView responsableAtEnterTextView;
    private TextView responsableAtExitTextView;
    private TextView currentDateTextView;
    private TextView vehicleTimeTextView;
    private TextView costTextView;
    private Button removeVehicleButton;
    private Button goBackButton;
    private  TextView tx1;
    private  TextView tx2;
    private  TextView tx3;
    private  TextView tx4;
    private  TextView tx5;
    private  TextView tx6;
    private  TextView tx7;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private String id;
    private Vehicle currentVehicle;
    private User currentUser;
    private DatabaseReference reference;
    private Ticket currentTicket;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_remove_vehicle);

        if(isOnline()){

            database = FirebaseDatabase.getInstance();
            auth = FirebaseAuth.getInstance();

            goArrowBackButton = findViewById(R.id.removeVehicleGoBackImageView);
            ticketIdTextView = findViewById(R.id.removeVehicleTicketIdTextView);
            ownerNameTextView = findViewById(R.id.removeVehicleOwnerTextView);
            ownerPhoneTextView = findViewById(R.id.removeVehicleOwnerPhoneTextView);
            platesTextView = findViewById(R.id.removeVehiclePlateTextView);
            typeTextView = findViewById(R.id.removeVehicleTypeTextView);
            enterDateTextView = findViewById(R.id.removeVehicleEnterDateTextView);
            responsableAtEnterTextView = findViewById(R.id.removeVehicleResponsablEnterTextView);
            responsableAtExitTextView = findViewById(R.id.removeVehicleResponsableExitTextView);
            currentDateTextView = findViewById(R.id.removeVehicleCurrentDateTextView);
            vehicleTimeTextView = findViewById(R.id.removeVehicleTimeTextView);
            costTextView = findViewById(R.id.removeVehicleCostTextView);
            removeVehicleButton = findViewById(R.id.removeVehicleConfirmationButton);
            goBackButton = findViewById(R.id.removeVehicleGoBackButton);
            tx1 = findViewById(R.id.tx1);
            tx2 = findViewById(R.id.tx2);
            tx3 = findViewById(R.id.tx3);
            tx4 = findViewById(R.id.tx4);
            tx5 = findViewById(R.id.tx5);
            tx6 = findViewById(R.id.tx6);
            tx7 = findViewById(R.id.tx7);

            goArrowBackButton.setOnTouchListener(this);
            removeVehicleButton.setOnTouchListener(this);
            goBackButton.setOnTouchListener(this);

            id = getIntent().getExtras().getString("id");
            recoverUser();

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.removeVehicleConfirmationButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    removeVehicleButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    removeVehicleButton.setBackgroundResource(R.drawable.button_background);
                    reference.setValue(currentTicket).addOnCompleteListener(

                            task -> {

                                if(task.isSuccessful()){

                                    Toast.makeText(this, "Vehículo retirado.", Toast.LENGTH_LONG).show();
                                    database.getReference().child("currentVehicles").child(currentVehicle.getPlate()).setValue(null);
                                    finish();

                                }else{

                                    Toast.makeText(this, "Error, intentelo más tarde.", Toast.LENGTH_LONG).show();
                                    finish();

                                }

                            }

                    );


                }

                break;

            case R.id.removeVehicleGoBackImageView:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goArrowBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    goArrowBackButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;

            case R.id.removeVehicleGoBackButton:

                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    goBackButton.setBackgroundResource(R.drawable.button_background);
                }else if(event.getAction()==MotionEvent.ACTION_UP){

                    goBackButton.setBackgroundResource(R.drawable.pressed_button_background);
                    finish();

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
                        if(currentVehicle!=null)
                         updateInfo();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }

        );

    }

    public void updateInfo(){

        long enterTime = currentVehicle.getEnterTime();
        String enterTimeConverted = Time.toDate(enterTime);
        reference = database.getReference().child("tickets").push();



        ticketIdTextView.setText(reference.getKey());
        currentDateTextView.setText(Time.getCurrentTime().toUpperCase());
        ownerNameTextView.setText(currentVehicle.getOwnerName().toUpperCase()+(currentVehicle.isHosted()?" (Hospedado)":""));
        ownerPhoneTextView.setText(currentVehicle.getOwnerPhone());
        platesTextView.setText(""+currentVehicle.getPlate().charAt(0)+currentVehicle.getPlate().charAt(1)+currentVehicle.getPlate().charAt(2)+"-"+currentVehicle.getPlate().charAt(3)+currentVehicle.getPlate().charAt(4)+currentVehicle.getPlate().charAt(5));
        typeTextView.setText(currentVehicle.getType().toUpperCase());
        enterDateTextView.setText(enterTimeConverted.toUpperCase());
        responsableAtEnterTextView.setText(currentVehicle.getResponsableAtEnter().toUpperCase());
        responsableAtExitTextView.setText(currentUser.getName().toUpperCase());
        tx1.setText("Cliente:");
        tx2.setText("Celular:");
        tx3.setText("Placas:");
        tx4.setText("Tipo:");
        tx5.setText("Entrada:");
        tx6.setText("Ingreso:");
        tx7.setText("Salida:");

        long currentTime = System.currentTimeMillis();
        int hours = Time.timeToHours(currentVehicle.getEnterTime(), currentTime);
        vehicleTimeTextView.setText(Time.getTimeDayHourMinuteSecond(currentVehicle.getEnterTime(), currentTime)+ "   ( "+hours+"h )");

        Payment payment = new Payment(3000,5000,8000,12000,2500,4500,6000,10000,2000,4000,5000,8000,500,10000,8000,7000);
        costTextView.setText(payment.numberFormat(payment.costByHours(currentVehicle.getType().charAt(0), hours, currentVehicle.isHosted())));

        currentVehicle.setResponsableAtExitId(currentUser.getName().toUpperCase());
        currentVehicle.setResponsableAtExitId(currentUser.getId());
        currentTicket = new Ticket(reference.getKey(), currentVehicle, 24000, Time.getCurrentTime().toUpperCase());

    }

    // -------------------------------------
    // User Authentication
    // -------------------------------------
    private void recoverUser() {

        if(auth.getCurrentUser() != null){

            String idU = auth.getCurrentUser().getUid();
            database.getReference().child("users").child(idU).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            currentUser =  snapshot.getValue(User.class);
                            loadDataBase();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }

    }

}