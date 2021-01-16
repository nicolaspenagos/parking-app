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
import android.view.Window;
import android.widget.TextView;

import com.example.parkingapp.model.Vehicle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will show the vehicle information.
 */
public class VehicleActivity extends AppCompatActivity {

    // -------------------------------------
    // XML references
    // -------------------------------------
    private TextView ownerTextView;
    private TextView ownerPhoneTextView;
    private TextView plateTextView;
    private TextView typeTextView;
    private TextView responsableAtEnterTextView;
    private TextView timeTextView;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private String id;
    private Vehicle currentVehicle;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vehicle);

        isOnline();


        id = getIntent().getExtras().getString("id");
        loadData();


    }

    private void loadData() {
        FirebaseDatabase.getInstance().getReference().child("currentVehicles").child(id).addValueEventListener(

                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        currentVehicle = snapshot.getValue(Vehicle.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                }

        );
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