package com.rtsoftbd.siddiqui.hellocar.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public class UsingType {
    private  int using_Type_ID;
    private  String Using_Type_Name;
    private static List<UsingType> usingTypes = new ArrayList<>();

    public UsingType() {
    }

    public UsingType(int using_Type_ID, String using_Type_Name) {
        this.using_Type_ID = using_Type_ID;
        Using_Type_Name = using_Type_Name;
    }

    public int getUsing_Type_ID() {
        return using_Type_ID;
    }

    public void setUsing_Type_ID(int using_Type_ID) {
        this.using_Type_ID = using_Type_ID;
    }

    public String getUsing_Type_Name() {
        return Using_Type_Name;
    }

    public void setUsing_Type_Name(String using_Type_Name) {
        Using_Type_Name = using_Type_Name;
    }

    public static List<UsingType> getUsingTypes() {
        return usingTypes;
    }

    public static void setUsingTypes(UsingType usingTypes) {
        UsingType.usingTypes.add(usingTypes);
    }
}
