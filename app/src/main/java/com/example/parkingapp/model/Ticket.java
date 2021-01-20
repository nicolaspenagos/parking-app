/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package com.example.parkingapp.model;

/*
 * This class will represents a removed vehicle.
 */
public class Ticket {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private String id;
    private Vehicle vehicle;
    private int cost;
    private String date;


    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Ticket(){

    }

    public Ticket(String id, Vehicle vehicle, int cost, String date) {

        this.id = id;
        this.vehicle = vehicle;
        this.cost = cost;
        this.date = date;

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

}
