package com.example.vromia.e_nurseproject.Data;

/**
 * Created by Vromia on 17/12/2014.
 */
public class WorkoutItem {

    private String category;
    private String date;
    private String periodOfDay;
    private double workTime;

    public WorkoutItem(String category, String date, double workTime, String periodOfDay) {

        this.category = category;
        this.date = date;
        this.workTime = workTime;
        this.periodOfDay = periodOfDay;

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

    public double getWorkTime() {
        return workTime;
    }


}
