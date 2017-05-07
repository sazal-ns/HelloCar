package com.rtsoftbd.siddiqui.hellocar.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public class DurationAndCost {
    private  String duration_Name;
    private  int duration_ID, duration_Type_ID, cost, Car_Type_ID;

    private static List<DurationAndCost> durationAndCosts = new ArrayList<>();

    public DurationAndCost() {
    }

    public DurationAndCost(String duration_Name, int duration_ID, int duration_Type_ID, int cost, int car_type_id) {
        this.duration_Name = duration_Name;
        this.duration_ID = duration_ID;
        this.duration_Type_ID = duration_Type_ID;
        this.cost = cost;
        Car_Type_ID = car_type_id;
    }

    public int getCar_Type_ID() {
        return Car_Type_ID;
    }

    public void setCar_Type_ID(int car_Type_ID) {
        Car_Type_ID = car_Type_ID;
    }

    public String getDuration_Name() {
        return duration_Name;
    }

    public void setDuration_Name(String duration_Name) {
        this.duration_Name = duration_Name;
    }

    public int getDuration_ID() {
        return duration_ID;
    }

    public void setDuration_ID(int duration_ID) {
        this.duration_ID = duration_ID;
    }

    public int getDuration_Type_ID() {
        return duration_Type_ID;
    }

    public void setDuration_Type_ID(int duration_Type_ID) {
        this.duration_Type_ID = duration_Type_ID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public static List<DurationAndCost> getDurationAndCosts() {
        return durationAndCosts;
    }

    public static void setDurationAndCosts(DurationAndCost durationAndCosts) {
        DurationAndCost.durationAndCosts.add(durationAndCosts);
    }
}
