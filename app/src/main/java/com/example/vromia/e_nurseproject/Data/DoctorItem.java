package com.example.vromia.e_nurseproject.Data;

/**
 * Created by Vromia on 5/6/2015.
 */
public class DoctorItem {
    private int id;
    private String name;
    private String surname;

    public DoctorItem(int id,String name,String surname){
        this.id=id;
        this.name=name;
        this.surname=surname;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }



}
