package com.example.parkingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.parkingapp.model.Ticket;
import com.example.parkingapp.utils.Payment;
import com.example.parkingapp.utils.Time;

public class ExitActivity extends AppCompatActivity implements View.OnTouchListener{

    private Ticket ticket;

    private ImageView goBackArrowImageView;
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
    private  TextView tx1;
    private  TextView tx2;
    private  TextView tx3;
    private  TextView tx4;
    private  TextView tx5;
    private  TextView tx6;
    private  TextView tx7;
    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_exit);

        goBackArrowImageView =  findViewById(R.id.exitGoBackArrow);
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
        goBackButton =  findViewById(R.id.exitGoBack);
        tx1 = findViewById(R.id.tx1);
        tx2 = findViewById(R.id.tx2);
        tx3 = findViewById(R.id.tx3);
        tx4 = findViewById(R.id.tx4);
        tx5 = findViewById(R.id.tx5);
        tx6 = findViewById(R.id.tx6);
        tx7 = findViewById(R.id.tx7);

        goBackButton.setOnTouchListener(this);
        goBackArrowImageView.setOnTouchListener(this);

        ticket = (Ticket) getIntent().getSerializableExtra("ticket");
        loadData();

    }

    private void loadData() {

        tx1.setText("Cliente:");
        tx2.setText("Celular:");
        tx3.setText("Placas:");
        tx4.setText("Tipo:");
        tx5.setText("Entrada:");
        tx6.setText("Ingreso:");
        tx7.setText("Salida:");

        ticketIdTextView.setText(""+ticket.getId());
        ownerNameTextView.setText(ticket.getVehicle().getOwnerName());
        ownerPhoneTextView.setText(ticket.getVehicle().getOwnerPhone());
        platesTextView.setText(""+ticket.getVehicle().getPlate().charAt(0)+ticket.getVehicle().getPlate().charAt(1)+ticket.getVehicle().getPlate().charAt(2)+"-"+ticket.getVehicle().getPlate().charAt(3)+ticket.getVehicle().getPlate().charAt(4)+ticket.getVehicle().getPlate().charAt(5));
        typeTextView.setText(ticket.getVehicle().getType());
        enterDateTextView.setText(Time.toDate(ticket.getVehicle().getEnterTime()));
        responsableAtEnterTextView.setText(ticket.getVehicle().getResponsableAtEnter().toUpperCase());
        responsableAtExitTextView.setText(ticket.getVehicle().getResponsableAtExit().toUpperCase());
        currentDateTextView.setText(ticket.getDate());
        vehicleTimeTextView.setText(""+ticket.getHours() +"h");
        costTextView.setText(new Payment().numberFormat(ticket.getCost()));

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()){

            case R.id.exitGoBackArrow:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackArrowImageView.setImageResource(R.drawable.go_back_arrow_pressed);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackArrowImageView.setImageResource(R.drawable.go_back_arrow);
                    finish();

                }

                break;

            case R.id.exitGoBack:

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    goBackButton.setBackgroundResource(R.drawable.pressed_button_background);
                }else if(event.getAction() == MotionEvent.ACTION_UP){

                    goBackButton.setBackgroundResource(R.drawable.button_background);
                    finish();

                }

                break;

        }

        return true;
    }
}