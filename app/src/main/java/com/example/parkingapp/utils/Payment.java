/* * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * @author Nicolás Penagos Montoya
 * nicolas.penagosm98@gmail.com
 * * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package com.example.parkingapp.utils;

import com.example.parkingapp.model.Vehicle;

public class Payment {

    // -------------------------------------
    // Atributtes
    // -------------------------------------
    private int mula1To4;
    private int mula4To8;
    private int mula8To12;
    private int mula12To24;

    private int turbo1To4;
    private int turbo4To8;
    private int turbo8To12;
    private int turbo12To24;

    private int automovil1To4;
    private int automovil4To8;
    private int automovil8To12;
    private int automovil12To24;

    private int extraHour;

    private int hostedMula;
    private int hostedTurbo;
    private int hostedAutomovil;

    // -------------------------------------
    // Constructors
    // -------------------------------------
    public Payment(){

    }

    public Payment(int mula1To4, int mula4To8, int mula8To12, int mula12To24, int turbo1To4, int turbo4To8, int turbo8To12, int turbo12To24, int automovil1To4, int automovil4To8, int automovil8To12, int automovil12To24, int extraHour, int hostedMula, int hostedTurbo, int hostedAutomovil) {

        this.mula1To4 = mula1To4;
        this.mula4To8 = mula4To8;
        this.mula8To12 = mula8To12;
        this.mula12To24 = mula12To24;
        this.turbo1To4 = turbo1To4;
        this.turbo4To8 = turbo4To8;
        this.turbo8To12 = turbo8To12;
        this.turbo12To24 = turbo12To24;
        this.automovil1To4 = automovil1To4;
        this.automovil4To8 = automovil4To8;
        this.automovil8To12 = automovil8To12;
        this.automovil12To24 = automovil12To24;
        this.extraHour = extraHour;
        this.hostedMula = hostedMula;
        this.hostedTurbo = hostedTurbo;
        this.hostedAutomovil = hostedAutomovil;

    }

    // -------------------------------------
    // Methods
    // -------------------------------------
    public int costByHours(char type, int hours){

        int cost = 0;

        switch (type){

            case 'M':

                if(hours<=4){
                    return mula1To4;
                }else if(hours<=8){
                    return mula4To8;
                }else if(hours<=12) {
                    return mula8To12;
                }else if(hours<=24){
                    return mula12To24;
                }else{
                    return mula12To24 + ((hours-24)*extraHour);
                }

            case 'T':

                if(hours<=4){
                    return turbo1To4;
                }else if(hours<=8){
                    return turbo4To8;
                }else if(hours<=12) {
                    return turbo8To12;
                }else if(hours<=24){
                    return turbo12To24;
                }else{
                    return turbo12To24 + ((hours-24)*extraHour);
                }

            case 'A':

                if(hours<=4){
                    return automovil1To4;
                }else if(hours<=8){
                    return automovil4To8;
                }else if(hours<=12) {
                    return automovil8To12;
                }else if(hours<=24){
                    return automovil12To24;
                }else{
                    return automovil12To24 + ((hours-24)*extraHour);
                }

        }

        return cost;

    }

    public String numberFormat(int number){

        String money = ""+number;
        String moneyReverse = "";

        for (int i=0; i<money.length(); i++){
            moneyReverse+=money.charAt(money.length()-i-1);
        }


        String format = "";

            for(int i = 0; i<moneyReverse.length(); i++){
                if(i%3==0 && i!=0){
                    format += "."+moneyReverse.charAt(i);
                }else{
                    format += moneyReverse.charAt(i);
                }
            }

            String formatReverse = "";

        for (int i=0; i<format.length(); i++){
            formatReverse+=format.charAt(format.length()-i-1);
        }

        return "$"+ formatReverse;
    }

    // -------------------------------------
    // Getters and setters
    // -------------------------------------
    public int getMula1To4() {
        return mula1To4;
    }

    public void setMula1To4(int mula1To4) {
        this.mula1To4 = mula1To4;
    }

    public int getMula4To8() {
        return mula4To8;
    }

    public void setMula4To8(int mula4To8) {
        this.mula4To8 = mula4To8;
    }

    public int getMula8To12() {
        return mula8To12;
    }

    public void setMula8To12(int mula8To12) {
        this.mula8To12 = mula8To12;
    }

    public int getMula12To24() {
        return mula12To24;
    }

    public void setMula12To24(int mula12To24) {
        this.mula12To24 = mula12To24;
    }

    public int getTurbo1To4() {
        return turbo1To4;
    }

    public void setTurbo1To4(int turbo1To4) {
        this.turbo1To4 = turbo1To4;
    }

    public int getTurbo4To8() {
        return turbo4To8;
    }

    public void setTurbo4To8(int turbo4To8) {
        this.turbo4To8 = turbo4To8;
    }

    public int getTurbo8To12() {
        return turbo8To12;
    }

    public void setTurbo8To12(int turbo8To12) {
        this.turbo8To12 = turbo8To12;
    }

    public int getTurbo12To24() {
        return turbo12To24;
    }

    public void setTurbo12To24(int turbo12To24) {
        this.turbo12To24 = turbo12To24;
    }

    public int getAutomovil1To4() {
        return automovil1To4;
    }

    public void setAutomovil1To4(int automovil1To4) {
        this.automovil1To4 = automovil1To4;
    }

    public int getAutomovil4To8() {
        return automovil4To8;
    }

    public void setAutomovil4To8(int automovil4To8) {
        this.automovil4To8 = automovil4To8;
    }

    public int getAutomovil8To12() {
        return automovil8To12;
    }

    public void setAutomovil8To12(int automovil8To12) {
        this.automovil8To12 = automovil8To12;
    }

    public int getAutomovil12To24() {
        return automovil12To24;
    }

    public void setAutomovil12To24(int automovil12To24) {
        this.automovil12To24 = automovil12To24;
    }

    public int getExtraHour() {
        return extraHour;
    }

    public void setExtraHour(int extraHour) {
        this.extraHour = extraHour;
    }

    public int getHostedMula() {
        return hostedMula;
    }

    public void setHostedMula(int hostedMula) {
        this.hostedMula = hostedMula;
    }

    public int getHostedTurbo() {
        return hostedTurbo;
    }

    public void setHostedTurbo(int hostedTurbo) {
        this.hostedTurbo = hostedTurbo;
    }

    public int getHostedAutomovil() {
        return hostedAutomovil;
    }

    public void setHostedAutomovil(int hostedAutomovil) {
        this.hostedAutomovil = hostedAutomovil;
    }
}
