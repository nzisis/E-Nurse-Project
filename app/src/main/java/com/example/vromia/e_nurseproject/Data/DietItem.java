package com.example.vromia.e_nurseproject.Data;

/**
 * Created by Vromia on 17/12/2014.
 */
public class DietItem {

    private String category;
    private String date;
    private double amount;
    private String time;


    public DietItem(String category,String date,double amount,String time){
        this.category=category;
        this.date=date;
        this.amount=amount;
        this.time=time;

    }

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;
    }

    public double getAmount(){
        return amount;
    }

    public String getTime(){
        return time;
    }


}
