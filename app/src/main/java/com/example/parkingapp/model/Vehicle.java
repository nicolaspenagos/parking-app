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
    private String type;
    private String responsableAtEnter;
    private String responsableAtEnterId;
    private String responsableAtExit;
    private String responsableAtExitId;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Vehicle(){

    }

    public Vehicle(String plate, String id, String ownerName, String ownerPhone, String type, String responsableAtEnter, String responsableAtEnterId){

        this.plate = plate;
        this.id = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.type = type;
        this.responsableAtEnter = responsableAtEnter;
        this.responsableAtEnterId = responsableAtEnterId;
        this.responsableAtExit = "";
        this.responsableAtExitId = "";

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResponsableAtEnter() { return responsableAtEnter; }

    public void setResponsableAtEnter(String responsableAtEnter) { this.responsableAtEnter = responsableAtEnter; }

    public String getResponsableAtEnterId() { return responsableAtEnterId; }

    public void setResponsableAtEnterId(String responsableAtEnterId) { this.responsableAtEnterId = responsableAtEnterId; }

    public String getResponsableAtExit() { return responsableAtExit; }

    public void setResponsableAtExit(String responsableAtExit) { this.responsableAtExit = responsableAtExit; }

    public String getResponsableAtExitId() { return responsableAtExitId; }

    public void setResponsableAtExitId(String responsableAtExitId) { this.responsableAtExitId = responsableAtExitId; }

}
