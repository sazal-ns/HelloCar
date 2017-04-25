package com.rtsoftbd.siddiqui.hellocar.models;

import java.sql.Time;
import java.sql.Date;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public class History {
    private int request_ID, user_ID, car_Type_ID, using_Type_ID, duration_ID, request_State, staff_ID;
    private String from_Area, to_Area;
    private Date pickup_Date;
    private Time pickup_Time;

    public History() {
    }

    public History(int request_ID, int user_ID, int car_Type_ID, int using_Type_ID, int duration_ID, int request_State, int staff_ID, String from_Area, String to_Area, Date pickup_Date, Time pickup_Time) {
        this.request_ID = request_ID;
        this.user_ID = user_ID;
        this.car_Type_ID = car_Type_ID;
        this.using_Type_ID = using_Type_ID;
        this.duration_ID = duration_ID;
        this.request_State = request_State;
        this.staff_ID = staff_ID;
        this.from_Area = from_Area;
        this.to_Area = to_Area;
        this.pickup_Date = pickup_Date;
        this.pickup_Time = pickup_Time;
    }

    public int getRequest_ID() {
        return request_ID;
    }

    public void setRequest_ID(int request_ID) {
        this.request_ID = request_ID;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getCar_Type_ID() {
        return car_Type_ID;
    }

    public void setCar_Type_ID(int car_Type_ID) {
        this.car_Type_ID = car_Type_ID;
    }

    public int getUsing_Type_ID() {
        return using_Type_ID;
    }

    public void setUsing_Type_ID(int using_Type_ID) {
        this.using_Type_ID = using_Type_ID;
    }

    public int getDuration_ID() {
        return duration_ID;
    }

    public void setDuration_ID(int duration_ID) {
        this.duration_ID = duration_ID;
    }

    public int getRequest_State() {
        return request_State;
    }

    public void setRequest_State(int request_State) {
        this.request_State = request_State;
    }

    public int getStaff_ID() {
        return staff_ID;
    }

    public void setStaff_ID(int staff_ID) {
        this.staff_ID = staff_ID;
    }

    public String getFrom_Area() {
        return from_Area;
    }

    public void setFrom_Area(String from_Area) {
        this.from_Area = from_Area;
    }

    public String getTo_Area() {
        return to_Area;
    }

    public void setTo_Area(String to_Area) {
        this.to_Area = to_Area;
    }

    public Date getPickup_Date() {
        return pickup_Date;
    }

    public void setPickup_Date(Date pickup_Date) {
        this.pickup_Date = pickup_Date;
    }

    public Time getPickup_Time() {
        return pickup_Time;
    }

    public void setPickup_Time(Time pickup_Time) {
        this.pickup_Time = pickup_Time;
    }
}
