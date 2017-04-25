package com.rtsoftbd.siddiqui.hellocar.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public class CarType {
    private  String car_Type_Name;
    private  int car_Type_ID;

    private static List<CarType> carTypes = new ArrayList<>();

    public CarType() {
    }

    public CarType(String car_Type_Name, int car_Type_ID) {
        this.car_Type_Name = car_Type_Name;
        this.car_Type_ID = car_Type_ID;
    }

    public String getCar_Type_Name() {
        return car_Type_Name;
    }

    public void setCar_Type_Name(String car_Type_Name) {
        this.car_Type_Name = car_Type_Name;
    }

    public int getCar_Type_ID() {
        return car_Type_ID;
    }

    public void setCar_Type_ID(int car_Type_ID) {
        this.car_Type_ID = car_Type_ID;
    }

    public static List<CarType> getCarTypes() {
        return carTypes;
    }

    public static void setCarTypes(CarType carTypes) {
        CarType.carTypes.add(carTypes);
    }
}
