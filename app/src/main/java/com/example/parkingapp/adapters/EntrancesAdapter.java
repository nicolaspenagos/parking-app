package com.example.parkingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parkingapp.R;
import com.example.parkingapp.model.Vehicle;
import com.example.parkingapp.utils.Time;


import java.util.ArrayList;

public class EntrancesAdapter extends BaseAdapter {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private ArrayList<Vehicle> entrances;

    // -------------------------------------
    // Constructor
    // -------------------------------------
    public EntrancesAdapter(){
        entrances = new ArrayList<Vehicle>();
    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void addEntrance(Vehicle vehicle){

        entrances.add(vehicle);
        notifyDataSetChanged();

    }

    public void clear(){

        entrances.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return entrances.size();
    }

    @Override
    public Object getItem(int position) {
        return entrances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.entrance_row, null);

        Vehicle vehicle = entrances.get(position);

        TextView positionTV = view.findViewById(R.id.entranceCounter);
        TextView entrancePlate = view.findViewById(R.id.entrancePlate);
        TextView entranceDate = view.findViewById(R.id.entranceDate);

        positionTV.setText(""+ (position+1));
        entrancePlate.setText(""+vehicle.getPlate().charAt(0)+vehicle.getPlate().charAt(1)+vehicle.getPlate().charAt(2)+"-"+vehicle.getPlate().charAt(3)+vehicle.getPlate().charAt(4)+vehicle.getPlate().charAt(5));
        entranceDate.setText(Time.toDate(vehicle.getEnterTime()));

        return view;

    }
}
