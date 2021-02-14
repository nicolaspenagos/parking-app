/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.model.Ticket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will allow to go to the whole record of the database.
 */
public class RecordActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener{

    // -------------------------------------
    // XML references
    // -------------------------------------
    private Button loadButton;
    private EditText day;
    private EditText month;
    private EditText year;
    private ImageView goBackButton;
    private ListView dataListView;
    private CheckBox entrancesCheckBox;
    private CheckBox exitsCheckBox;
    private TextView totalAmountOfVehicles;
    private TextView totalAmountMoney;

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private boolean dataToLoad;
    private ExitsAdapter exitsAdapter;
    private EntrancesAdapter entrancesAdapter;
    private int totalVehicles;
    private int totalMoney;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_record);

        if(isOnline()){

            auth =  FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();

            loadButton = findViewById(R.id.loadDataButton);
            goBackButton = findViewById(R.id.dataGoBackButton);
            day = findViewById(R.id.dayEditText);
            month = findViewById(R.id.monthEditText);
            year = findViewById(R.id.yearEditText);
            dataListView = findViewById(R.id.dataListView);
            entrancesCheckBox = findViewById(R.id.entrancesCheckBox);
            exitsCheckBox = findViewById(R.id.exitsCheckBox);
            totalAmountOfVehicles = findViewById(R.id.numberOfVehicles);
            totalAmountMoney = findViewById(R.id.money);

            loadButton.setOnTouchListener(this);
            goBackButton.setOnTouchListener(this);
            entrancesCheckBox.setOnClickListener(this);
            exitsCheckBox.setOnClickListener(this);

            exitsAdapter = new ExitsAdapter();
            dataListView.setAdapter(exitsAdapter);

            addDateCheckers();

            exitsCheckBox.setChecked(true);
            dataToLoad = true;

        }

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

            case R.id.loadDataButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                   loadButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    loadButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){
                       loadData();
                    }

                }

                break;

            case R.id.dataGoBackButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackButton.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }
                break;
        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.entrancesCheckBox:

                if(entrancesCheckBox.isChecked()){

                    exitsCheckBox.setChecked(false);
                    dataToLoad = true;
                    loadData();

                }


                break;

            case R.id.exitsCheckBox:

                if(exitsCheckBox.isChecked()){

                    entrancesCheckBox.setChecked(false);
                    dataToLoad = false;
                    loadData();

                }

                break;

        }

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void loadData() {


        if(checkDateData()){

            int yearInt = Integer.parseInt(year.getText().toString());
            int monthInt = Integer.parseInt(month.getText().toString());
            int dayInt = Integer.parseInt(day.getText().toString());

            String yearString = String.valueOf(yearInt);
            String monthString = (monthInt<9)?("0"+monthInt):String.valueOf(monthInt);
            String dayString = (dayInt<9)?("0"+dayInt):String.valueOf(dayInt);

            String date = dayString+"-"+monthString+"-"+yearString;

            try{

                if(dataToLoad){



                    DatabaseReference exitsRef = database.getReference().child("dates").child(date).child("exits");
                    Toast.makeText(this, "Hola"+ exitsRef.getKey(), Toast.LENGTH_LONG).show();
                    exitsRef.addValueEventListener(

                            new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot data) {

                                    totalMoney = 0;
                                    totalVehicles=0;
                                    exitsAdapter.clear();

                                    for(DataSnapshot child: data.getChildren()){

                                        Ticket ticket = child.getValue(Ticket.class);
                                        totalVehicles++;
                                        totalMoney += ticket.getCost();
                                        exitsAdapter.addExit(ticket);
                                    }

                                    runOnUiThread(()->{
                                        totalAmountOfVehicles.setText("Total Vehículos: "+totalVehicles);
                                        totalAmountMoney.setText("$"+totalMoney);
                                    });

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            }
                    );

                }else{

                }

            }catch (NumberFormatException e){
                Toast.makeText(this, "Ingrese un formato de fecha válidor", Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, "Ingrese todos los campos para continuar", Toast.LENGTH_LONG).show();
        }


    }

    public boolean checkDateData(){
        return (day.getText().toString() != null) && !day.getText().toString().equals("") && (month.getText().toString() != null) && !month.getText().toString().equals("") && (year.getText().toString() != null) && !year.getText().toString().equals("");
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