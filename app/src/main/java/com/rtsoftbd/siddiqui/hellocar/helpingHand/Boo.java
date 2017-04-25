package com.rtsoftbd.siddiqui.hellocar.helpingHand;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public final class Boo {
    private static final String BASE = "http://hellocar.ga/Mobile_api/";

    public static final String REG = BASE.concat("user_register");
    public static final String BAG = BASE.concat("databag");
    public static final String REQUEST = BASE.concat("request_state");

    /*Reg*/
    public static final String KEY_USER_FILE = "userfile";
    public static final String KEY_USER_ADDRESS = "User_Address";
    public static final String KEY_USER_EMERGENCY_NUMBER = "User_Emergncy_Number";
    public static final String KEY_USER_EMAIL = "User_Email";
    public static final String KEY_USER_NID = "User_NID";
    public static final String KEY_MOBILE_NUMBER = "Mobile_Number";
    public static final String KEY_FULL_NAME = "Full_Name";
    public static final String KEY_USER_PASSWORD = "User_Password";
    public static final String KEY_USER_NAME = "User_Name";

    /*Category*/
    public static final String KEY_TABLE = "table";

    /*Request State*/
    public static final String KEY_STATE = "state";
    /*sender*/

    /*history*/
    public static final int PENDING = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECT_BY_ADMIN = 2;
    public static final int REJECT_BY_CLIENT = 3;

    /*request*/
    public static final String CAR_TYPE = "car_type";
    public static final String DURATION  ="duration";
    public static final String USING_TYPE = "using_type";

    /*Common Replay*/
    public static final String REPLAY_ERROR = "error";
    public static final String REPLAY_CONFIRM_STATUS = "confirm_status";

    /*request replay*/
    public static final String REPLAY_CAR_TYPE_ID = "Car_Type_ID";
    public static final String REPLAY_CAR_TYPE_NAME = "Car_Type_Name";
    public static final String REPLAY_DURATION_ID = "Duration_ID";
    public static final String REPLAY_DURATION_NAME = "Duration_Name";
    public static final String REPLAY_DURATION_TYPE_ID = "Duration_Type_ID";
    public static final String REPLAY_COST = "cost";
    public static final String REPLAY_USING_TYPE_ID = "Using_Type_ID";
    public static final String REPLAY_USING_TYPE_NAME = "Using_Type_Name";

    /*history replay*/
    public static final String REPLAY_REQUEST_ID = "Request_ID";
    public static final String REPLAY_USER_ID = "User_ID";
    public static final String REPLAY_FROM_AREA = "From_Area";
    public static final String REPLAY_TO_AREA = "To_Area";
    public static final String REPLAY_PICKUP_DATE = "Pickup_Date";
    public static final String REPLAY_PICKUP_TIME = "Pickup_Time";
    public static final String REPLAY_REQUEST_STATE = "Request_State";
    public static final String REPLAY_STAFF_ID = "Staff_ID";

    /*user*/
    public static final String REPLAY_FULL_NAME = KEY_FULL_NAME;
    public static final String REPLAY_USER_NAME = KEY_USER_NAME;
    public static final String REPLAY_USER_PASSWORD = KEY_USER_PASSWORD;
    public static final String REPLAY_USER_EMAIL = KEY_USER_EMAIL;
    public static final String REPLAY_USER_EMERGENCY_NUMBER = KEY_USER_EMERGENCY_NUMBER;
    public static final String REPLAY_USER_ADDRESS = KEY_USER_ADDRESS;
    public static final String REPLAY_USER_IMAGE = "User_Image";
    public static final String REPLAY_USER_PIN = "User_Pin";
    public static final String REPLAY_STATE = KEY_STATE;
    public static final String REPLAY_MOBILE_NUMBER = KEY_MOBILE_NUMBER;

    /*user state*/
    public static final int VALID_USER = 0;
    public static final int INVALID_USER = 2;
    public static final int BLOCK_USER = 1;

}