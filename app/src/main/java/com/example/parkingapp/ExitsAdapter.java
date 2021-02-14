package com.example.parkingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.parkingapp.model.Ticket;
import com.example.parkingapp.model.Vehicle;
import com.example.parkingapp.utils.Time;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ExitsAdapter extends BaseAdapter {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private ArrayList<Ticket> exits;

    // -------------------------------------
    // Constructor
    // -------------------------------------
    public ExitsAdapter(){
        exits = new ArrayList<Ticket>();
    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public void  addExit(Ticket exit){

        exits.add(exit);
        notifyDataSetChanged();

    }

    public void clear(){

        exits.clear();
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return exits.size();
    }

    @Override
    public Object getItem(int position) {
        return exits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.exit_row, null);

        Ticket ticket = exits.get(position);

        TextView plate = view.findViewById(R.id.exitPlate);
        TextView exit = view.findViewById(R.id.exitExitDate);
        TextView entrance = view.findViewById(R.id.exitExitEntrance);
        TextView time = view.findViewById(R.id.exitTime);
        TextView price = view.findViewById(R.id.exitPrice);

        plate.setText(""+ticket.getVehicle().getPlate().charAt(0)+ticket.getVehicle().getPlate().charAt(1)+ticket.getVehicle().getPlate().charAt(2)+"-"+ticket.getVehicle().getPlate().charAt(3)+ticket.getVehicle().getPlate().charAt(4)+ticket.getVehicle().getPlate().charAt(5));
        exit.setText(ticket.getDate());
        entrance.setText(Time.toDate(ticket.getVehicle().getEnterTime()));
        time.setText(""+ticket.getHours()+"h");
        price.setText("$"+ticket.getCost());

        return view;

    }
}
