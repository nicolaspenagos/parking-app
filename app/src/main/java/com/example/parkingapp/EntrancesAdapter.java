/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.parkingapp.model.Vehicle;

import java.util.ArrayList;

/*
 * This class will represent show the history of the entrances.
 */
public class EntrancesAdapter extends BaseAdapter {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private ArrayList<Vehicle> entrances;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

}
