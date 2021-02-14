/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.parkingapp.model.Vehicle;

import java.util.ArrayList;

/*
 * This class will represent the vehicles list on the screen.
 */
public class VehicleAdapter extends BaseAdapter {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private ArrayList<Vehicle> vehicles;

    // -------------------------------------
    // Constructor
    // -------------------------------------
    public VehicleAdapter(){
        vehicles = new ArrayList<>();
    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void addVehicle(Vehicle vehicle){

        vehicles.add(vehicle);
        notifyDataSetChanged();

    }

    public void clear(){

        vehicles.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return vehicles.size();
    }

    @Override
    public Object getItem(int position) {
        return vehicles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View row, ViewGroup list) {

        LayoutInflater inflater = LayoutInflater.from(list.getContext());
        View rowView = inflater.inflate(R.layout.plate_layout, null);

        TextView plateTextView = rowView.findViewById(R.id.plateRowTextView);
        TextView typeTextView = rowView.findViewById(R.id.typeRowTextView);
        ConstraintLayout cardLayout = rowView.findViewById(R.id.cardRowLayout);

        Vehicle vehicle = vehicles.get(position);

        String plateToShow = "";
        for(int i=0; i<vehicle.getPlate().length(); i++){

            if(i!=3){
                plateToShow += vehicle.getPlate().charAt(i);
            }else{

                plateToShow += "-";
                plateToShow += vehicle.getPlate().charAt(i);

            }

        }

        if(vehicle.getType().equals("AUTOMÓVIL")){
            cardLayout.setBackgroundResource(R.drawable.row_background3);
        }else if(vehicle.getType().equals("MULA")){
            cardLayout.setBackgroundResource(R.drawable.row_background2);
        }else if(vehicle.getType().equals("TURBO")){
            cardLayout.setBackgroundResource(R.drawable.row_background1);
        }

        plateTextView.setText(plateToShow);
        typeTextView.setText(vehicle.getType());

        View.OnClickListener listener = (v)->{

            Intent intent = new Intent(list.getContext(), VehicleActivity.class);
            intent.putExtra("id", vehicle.getPlate());
            list.getContext().startActivity(intent);

        };

        cardLayout.setOnClickListener(listener);
        plateTextView.setOnClickListener(listener);
        typeTextView.setOnClickListener(listener);

        return rowView;

    }



}
