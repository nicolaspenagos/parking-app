package com.example.parkingapp.adapters;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkingapp.ExitActivity;
import com.example.parkingapp.R;
import com.example.parkingapp.model.Ticket;
import com.example.parkingapp.utils.Payment;
import com.example.parkingapp.utils.Time;

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
        Payment payment = new Payment();

        TextView plate = view.findViewById(R.id.exitPlate);
        TextView exit = view.findViewById(R.id.exitExitDate);
        TextView entrance = view.findViewById(R.id.exitExitEntrance);
        TextView time = view.findViewById(R.id.exitTime);
        TextView price = view.findViewById(R.id.exitPrice);
        TextView counter = view.findViewById(R.id.counterTextView);

        plate.setText(""+ticket.getVehicle().getPlate().charAt(0)+ticket.getVehicle().getPlate().charAt(1)+ticket.getVehicle().getPlate().charAt(2)+"-"+ticket.getVehicle().getPlate().charAt(3)+ticket.getVehicle().getPlate().charAt(4)+ticket.getVehicle().getPlate().charAt(5));
        exit.setText(ticket.getDate());
        entrance.setText(Time.toDate(ticket.getVehicle().getEnterTime()));
        time.setText(""+ticket.getHours()+"h");
        price.setText(payment.numberFormat(ticket.getCost()));
        counter.setText(""+(position+1));

        view.setOnClickListener(
                (v)->{


                    Log.e("debug", "hola");
                    Intent intent = new Intent(view.getContext(), ExitActivity.class);
                    intent.putExtra("ticket", ticket);
                    view.getContext().startActivity(intent);

                }
        );

        return view;

    }
}
