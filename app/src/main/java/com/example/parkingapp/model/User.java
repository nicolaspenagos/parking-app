/**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 **~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.parkingapp.model;

/*
 * This class will represents each employee of the app.
 */
public class User {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    public String email;
    public String id;
    public String name;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public User(){

    }

    public User(String email, String id, String name) {

        this.email = email;
        this.id = id;
        this.name = name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
