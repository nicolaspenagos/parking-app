/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package com.example.parkingapp.model;

import java.io.Serializable;

/*
 * This class will represents a removed vehicle.
 */
public class Ticket implements Serializable {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private String id;
    private Vehicle vehicle;
    private int cost;
    private String date;
    private int hours;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Ticket(){

    }

    public Ticket(String id, Vehicle vehicle, int cost, String date, int hours) {

        this.id = id;
        this.vehicle = vehicle;
        this.cost = cost;
        this.date = date;
        this.hours = hours;

    }

    // -------------------------------------
    // Getters and setters
    // -------------------------------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
