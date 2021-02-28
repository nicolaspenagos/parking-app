/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

/*
 * This class will represents each vehicle registered in the app.
 */
public class Vehicle implements Serializable {

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
    private long enterTime;
    private boolean hosted;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Vehicle(){

    }

    public Vehicle(String plate, String id, String ownerName, String ownerPhone, String type, String responsableAtEnter, String responsableAtEnterId, Long enterTime, boolean hosted){

        this.plate = plate;
        this.id = id;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
        this.type = type;
        this.responsableAtEnter = responsableAtEnter;
        this.responsableAtEnterId = responsableAtEnterId;
        this.responsableAtExit = "";
        this.responsableAtExitId = "";
        this.enterTime = enterTime;
        this.hosted = hosted;

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    @NonNull
    @Override
    public String toString() {
        return plate;
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

    public long getEnterTime() { return enterTime; }

    public void setEnterTime(long enterTime) { this.enterTime = enterTime; }

    public boolean isHosted() { return hosted; }

    public void setHosted(boolean hosted) { this.hosted = hosted; }

}
