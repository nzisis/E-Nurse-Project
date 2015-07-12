package com.example.vromia.e_nurseproject.Data;

/**
 * Created by Vromia on 17/12/2014.
 */
public class DrugsItem {

    private String category;
    private String date;
    private String periodOfDay;
    private double quantity;
    private String cause;

    public DrugsItem(String category, String date, double quantity, String periodOfDay,String cause) {

        this.category = category;
        this.date = date;
        this.quantity = quantity;
        this.periodOfDay = periodOfDay;
        this.cause=cause;

    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getPeriodOfDay() {
        return periodOfDay;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getCause(){
        return  cause;
    }


}