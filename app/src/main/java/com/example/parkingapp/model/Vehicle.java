/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp.model;

/*
 * This class will represents each vehicle registered in the app.
 */
public class Vehicle {

    // -------------------------------------
    // Constants
    // -------------------------------------
    public final static char TURBO = 't';
    public final static char MULA = 'm';
    public final static char AUTOMOVIL = 'a';

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private String plate;
    private String id;
    private String ownerName;
    private String ownerPhone;
    private char type;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Vehicle(){

    }

    public Vehicle(String plate, String id, String ownerName, String ownerPhone, char type){

        this.plate = plate;
        this.id = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.type = type;

    }

    // -------------------------------------
    // Getters and setters
    // -------------------------------------
    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

}
