/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.model.User;
import com.example.parkingapp.model.Vehicle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/*
 * This class will contains the entered vehicles and will have the main functions.
 */
public class HomeActivity extends AppCompatActivity implements View.OnTouchListener{

    // -------------------------------------
    // Firebase
    // -------------------------------------
    private FirebaseAuth auth;
    private FirebaseDatabase database;

    // -------------------------------------
    // XML references
    // -------------------------------------
    private ImageView logoutButton;
    private ImageView profileButton;
    private TextView currentUserTextView;
    private ConstraintLayout enterVehicleButton;
    private Button searchButton;
    private EditText plate1EditText;
    private EditText plate2EditText;
    private EditText plate3EditText;
    private EditText plate4EditText;
    private EditText plate5EditText;
    private EditText plate6EditText;
    private GridView vehiclesGridView;
    private TextView numbersOfVehiclesTextView;
    private Button recordButton;

    // -------------------------------------
    // Global assets
    // -------------------------------------
    private User currentUser;
    private VehicleAdapter adapter;
    private Vehicle vehicleToSearch;

    // -------------------------------------
    // Android methods
    // -------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        isOnline();

        auth =  FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        if(auth.getCurrentUser() == null)
            goToLogin();
        else{

            logoutButton =  findViewById(R.id.logoutButton);
            profileButton = findViewById(R.id.profileButton);
            currentUserTextView = findViewById(R.id.currentUserTextView);
            enterVehicleButton = findViewById(R.id.enterVehicleButton);
            searchButton = findViewById(R.id.searchButton);
            plate1EditText = findViewById(R.id.plate1EditText);
            plate2EditText = findViewById(R.id.plate2EditText);
            plate3EditText = findViewById(R.id.plate3EditText);
            plate4EditText = findViewById(R.id.plate4EditText);
            plate5EditText = findViewById(R.id.plate5EditText);
            plate6EditText = findViewById(R.id.plate6EditText);
            vehiclesGridView = findViewById(R.id.vehiclesGridView);
            numbersOfVehiclesTextView = findViewById(R.id.numberOfVehiclesTextView);
            recordButton = findViewById(R.id.recordButton);

            logoutButton.setOnTouchListener(this);
            profileButton.setOnTouchListener(this);
            enterVehicleButton.setOnTouchListener(this);
            searchButton.setOnTouchListener(this);
            recordButton.setOnTouchListener(this);

            adapter = new VehicleAdapter();
            vehiclesGridView.setAdapter(adapter);

            addTextCheckers();
            recoverUser();
            loadDatabase();

        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.logoutButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    logoutButton.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    logoutButton.setImageResource(R.drawable.go_back_arrow);

                    if(isOnline()){

                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialog)
                                .setTitle("Cerrar Sesión")
                                .setMessage("¿Está seguro que desea cerrar sesión?")
                                .setNegativeButton("No", (dialog, id)->{
                                    dialog.dismiss();
                                })
                                .setPositiveButton("Si", (dialog, id)->{

                                    auth.signOut();
                                    goToLogin();

                                });

                        builder.show();

                    }

                }

                break;

            case R.id.profileButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    profileButton.setImageResource(R.drawable.user_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    profileButton.setImageResource(R.drawable.user);

                    if(isOnline()){
                        Intent intent = new Intent(this, ProfileActivity.class);
                        startActivity(intent);
                    }

                }

                break;

            case R.id.enterVehicleButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    enterVehicleButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    enterVehicleButton.setBackgroundResource(R.drawable.button_background);

                    if(isOnline()){

                        Intent intent = new Intent(this, EnterVehicleActivity.class);
                        intent.putExtra("userId", currentUser.getEmail());
                        intent.putExtra("userName", currentUser.getName());
                        startActivity(intent);

                    }

                }

                break;

            case R.id.searchButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    searchButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    searchButton.setBackgroundResource(R.drawable.button_background);
                    searchVehicle();

                }

                break;

            case R.id.recordButton:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    recordButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    recordButton.setBackgroundResource(R.drawable.button_background);
                    if(isOnline()){

                        Intent intent = new Intent(this, RecordActivity.class);
                        startActivity(intent);
                        finish();

                    }

                }

                break;

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        plate1EditText.setText("");
        plate2EditText.setText("");
        plate3EditText.setText("");
        plate4EditText.setText("");
        plate5EditText.setText("");
        plate6EditText.setText("");

    }

    // -------------------------------------
    // User Authentication
    // -------------------------------------
    private void goToLogin() {

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }


    private void recoverUser() {

        if(auth.getCurrentUser() != null){

            String id = auth.getCurrentUser().getUid();
            database.getReference().child("users").child(id).addListenerForSingleValueEvent(

                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                           currentUser =  snapshot.getValue(User.class);
                           currentUserTextView.setText(currentUser.getName());

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    }
            );

        }

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
    public void loadDatabase() {

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
                        updatedNumberOfVehicles(adapter.getCount());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                }
        );
    }

    public void searchVehicle(){

        if(isOnline()){

            String p1, p2,p3,p4,p5,p6;

            p1 = plate1EditText.getText().toString();
            p2 = plate2EditText.getText().toString();
            p3 = plate3EditText.getText().toString();
            p4 = plate4EditText.getText().toString();
            p5 = plate5EditText.getText().toString();
            p6 = plate6EditText.getText().toString();


           if(checkSearchData(p1,p2,p3,p4,p5,p6)){

               String plate = p1+p2+p3+p4+p5+p6;

               vehicleToSearch = null;
               DatabaseReference ref = database.getReference().child("currentVehicles").child(plate);

               ref.addValueEventListener(

                       new ValueEventListener() {

                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {

                               vehicleToSearch = snapshot.getValue(Vehicle.class);
                               if(vehicleToSearch!=null){
                                   showVehicle(plate);
                               }else{
                                   showMessage();
                               }

                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }

                       }

               );


           }else{
               Toast.makeText(this, "La placa debe estar completa para continuar", Toast.LENGTH_LONG).show();
           }

        }

    }

    public void showMessage(){
        Toast.makeText(this, "Esta placa NO se encuentra registrada.", Toast.LENGTH_LONG).show();
    }

    public void showVehicle(String plate){

        Intent intent = new Intent(this, VehicleActivity.class);
        intent.putExtra("id", plate);
        startActivity(intent);

    }

    public boolean checkSearchData(String p1, String p2, String p3, String p4, String p5, String p6){
        return p1!=null&&!p1.equals("")&&p2!=null&&!p2.equals("")&&p3!=null&&!p3.equals("")&&p4!=null&&!p4.equals("")&&p5!=null&&!p5.equals("")&&p6!=null&&!p6.equals("");
    }

    public void addTextCheckers() {

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

                        if (string.length() > 1) {
                            string = "" + string.charAt(0);
                            plate1EditText.setText(string);
                        }

                        if (!string.equals(string.toUpperCase())) {
                            string = string.toUpperCase();
                            plate1EditText.setText(string);
                        }

                        if (string.length() > 0) {
                            char character = string.charAt(0);
                            if (!Character.isLetter(character)) {
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

    public void updatedNumberOfVehicles(int numberOfVehicles){
        numbersOfVehiclesTextView.setText(""+numberOfVehicles+" vehículos");
    }

}