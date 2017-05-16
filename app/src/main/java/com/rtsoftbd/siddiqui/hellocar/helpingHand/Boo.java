package com.rtsoftbd.siddiqui.hellocar.helpingHand;

/**
 * Created by RTsoftBD_Siddiqui on 2017-04-24.
 */

public final class Boo {
    private static final String BASE = "http://hellocar24.com/Mobile_api/";

    public static final String PICTURE_URL = "http://hellocar24.com/assets/uploads/user/";

    public static final String MS_REG = BASE.concat("user_register");
    public static final String MS_BAG = BASE.concat("databag");
    public static final String MS_HISTORY = BASE.concat("request_state");
    public static final String MS_LOGIN = BASE.concat("loginverification");
    public static final String MS_REQUEST_CAR = BASE.concat("carRequest");
    public static final String MS_IS_CANCEL_REQUEST = BASE.concat("cancel_request");
    public static final String MS_CANCEL_REQUEST = BASE.concat("update_request");
    public static final String MS_UPDATE_SAFETY = BASE.concat("update_savety");
    public static final String MS_UPDATE_PROFILE = BASE.concat("user_profile_update");
    public static final String MS_UPDATE_PIN = BASE.concat("update_pin_state");

    /*Update Safety*/
    public static final String KEY_EMAIL = "email";

    /*login*/
    public static final String KEY_LOGIN_USER_NAME = "username";
    public static final String KEY_LOGIN_PASSWORD = "password";

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

    /*Common Key*/
    public static final String KEY_ID = "id";

    /*history*/
    public static final int PENDING = 0;
    public static final int ACCEPTED = 1;
    public static final int REJECT_BY_ADMIN = 2;
    public static final int REJECT_BY_CLIENT = 3;

    /*request*/
    public static final String CAR_TYPE = "car_type";
    public static final String DURATION  ="duration";
    public static final String USING_TYPE = "using_type";

    public static final String KEY_NOTES = "notes";

    /*Common Replay*/
    public static final String REPLAY_ERROR = "error";
    public static final String REPLAY_CONFIRM_STATUS = "confirm_status";
    public static final String REPLAY_SERVER_RESPONSE = "server_responses";

    /*request replay*/
    public static final String REPLAY_CAR_TYPE_ID = "Car_Type_ID";
    public static final String REPLAY_CAR_TYPE_NAME = "Car_Type_Name";
    public static final String REPLAY_DURATION_ID = "Duration_ID";
    public static final String REPLAY_DURATION_NAME = "Duration_Name";
    public static final String REPLAY_DURATION_TYPE_ID = "Duration_Type_ID";
    public static final String REPLAY_COST = "cost";
    public static final String REPLAY_USING_TYPE_ID = "Using_Type_ID";
    public static final String REPLAY_USING_TYPE_NAME = "Using_Type_Name";
    public static final String REPLAY_NID = KEY_USER_NID;

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
    public static final String REPLAY_LOGIN_USER_ID = "UserID";

    /*Request Car*/
    public static final String KEY_USER_ID = REPLAY_LOGIN_USER_ID;
    public static final String KEY_CAR_TYPE_ID = REPLAY_CAR_TYPE_ID;
    public static final String KEY_USING_TYPE_ID = REPLAY_USING_TYPE_ID;
    public static final String KEY_FROM = REPLAY_FROM_AREA;
    public static final String KEY_TO = REPLAY_TO_AREA;
    public static final String KEY_DURATION_ID = REPLAY_DURATION_ID;
    public static final String KEY_PICKUP_DATE = REPLAY_PICKUP_DATE;
    public static final String KEY_PICKUP_TIME = REPLAY_PICKUP_TIME;

    /*user state*/
    public static final int VALID_USER = 0;
    public static final int INVALID_USER = 2;
    public static final int BLOCK_USER = 1;

}