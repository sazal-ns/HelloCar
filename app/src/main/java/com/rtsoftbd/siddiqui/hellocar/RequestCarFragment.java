package com.rtsoftbd.siddiqui.hellocar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.ForwardingListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.ApplicationController;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Boo;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Messages;
import com.rtsoftbd.siddiqui.hellocar.models.CarType;
import com.rtsoftbd.siddiqui.hellocar.models.DurationAndCost;
import com.rtsoftbd.siddiqui.hellocar.models.User;
import com.rtsoftbd.siddiqui.hellocar.models.UsingType;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestCarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestCarFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.carCatMaterialSpinner) MaterialSpinner ms_CarCatMaterialSpinner;
    @BindView(R.id.carCatInfoAppCompatImageButton) AppCompatImageButton ms_CarCatInfoAppCompatImageButton;
    @BindView(R.id.whereMaterialSpinner) MaterialSpinner ms_WhereMaterialSpinner;
    @BindView(R.id.whereInfoAppCompatImageButton) AppCompatImageButton ms_WhereInfoAppCompatImageButton;
    @BindView(R.id.durationMaterialSpinner) MaterialSpinner ms_DurationMaterialSpinner;
    @BindView(R.id.durationInfoAppCompatImageButton) AppCompatImageButton ms_DurationInfoAppCompatImageButton;
    @BindView(R.id.fromTextInputEditText) TextInputEditText ms_FromTextInputEditText;
    @BindView(R.id.toTextInputEditText) TextInputEditText ms_ToTextInputEditText;
    @BindView(R.id.dateTextInputEditText) TextInputEditText ms_DateTextInputEditText;
    @BindView(R.id.timeTextInputEditText) TextInputEditText ms_TimeTextInputEditText;
    @BindView(R.id.submitRequestAppCompatButton) AppCompatButton ms_SubmitRequestAppCompatButton;
    Unbinder unbinder;

    private String from, to, carTypeId, whereToID, durationID;

    private CarType carType = new CarType();
    private List<String> carTypes = new ArrayList<>();
    private DurationAndCost durationAndCost = new DurationAndCost();
    private List<String> durationAndCosts = new ArrayList<>();
    private UsingType usingType = new UsingType();
    private List<String> usingTypes = new ArrayList<>();

    private Calendar myCalendar = Calendar.getInstance();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    String myFormat;
    SimpleDateFormat sdf;

    public RequestCarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestCarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestCarFragment newInstance(String param1, String param2) {
        RequestCarFragment fragment = new RequestCarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_request_car, container, false);
        unbinder = ButterKnife.bind(this, view);

        ms_TimeTextInputEditText.setEnabled(false);

         myFormat = "yyyy-MM-dd"; //In which you need put here
         sdf = new SimpleDateFormat(myFormat, Locale.US);


        ms_CarCatMaterialSpinner.clearComposingText();
        ms_WhereMaterialSpinner.clearComposingText();
        ms_DurationMaterialSpinner.clearComposingText();
        carTypes.clear();
        durationAndCosts.clear();
        usingTypes.clear();

        for (int i = 0; i< CarType.getCarTypes().size(); i++)
            carTypes.add(CarType.getCarTypes().get(i).getCar_Type_Name());


        for (int i=0; i<UsingType.getUsingTypes().size(); i++)
            usingTypes.add(UsingType.getUsingTypes().get(i).getUsing_Type_Name());

        ms_CarCatMaterialSpinner.setItems(carTypes);
        ms_WhereMaterialSpinner.setItems(usingTypes);

        for (int i = 0; i<DurationAndCost.getDurationAndCosts().size(); i++) {
            if (DurationAndCost.getDurationAndCosts().get(i).getDuration_Type_ID() ==
                    UsingType.getUsingTypes().get(0).getUsing_Type_ID())
                durationAndCosts.add(DurationAndCost.getDurationAndCosts().get(i).getDuration_Name() +
                        " " + DurationAndCost.getDurationAndCosts().get(i).getCost() + " TK");
        }

        ms_DurationMaterialSpinner.setItems(durationAndCosts);
        durationID = String.valueOf(DurationAndCost.getDurationAndCosts().get(0).getDuration_ID());
        whereToID = String.valueOf(UsingType.getUsingTypes().get(0).getUsing_Type_ID());
        carTypeId = String.valueOf(CarType.getCarTypes().get(0).getCar_Type_ID());

        ms_WhereMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                ms_DurationMaterialSpinner.clearComposingText();
                ms_DurationMaterialSpinner.clearFocus();
                durationAndCosts.clear();
                int k = 0;
                int i;
                Log.i("post", ""+position);
                for (i = 0; i<DurationAndCost.getDurationAndCosts().size(); i++) {
                    if (DurationAndCost.getDurationAndCosts().get(i).getDuration_Type_ID() ==
                            UsingType.getUsingTypes().get(position).getUsing_Type_ID()) {
                        k++;
                        durationAndCosts.add(DurationAndCost.getDurationAndCosts().get(i).getDuration_Name() +
                                " " + DurationAndCost.getDurationAndCosts().get(i).getCost() + " TK");
                    }
                }
                whereToID = String.valueOf(UsingType.getUsingTypes().get(position).getUsing_Type_ID());
                durationID = String.valueOf(DurationAndCost.getDurationAndCosts().get(i-k).getDuration_ID());
                ms_DurationMaterialSpinner.setItems(durationAndCosts);
                Log.d("whertoUse", whereToID + "->" + k+"->"+i);
                Log.d("durationDD", durationID);
            }
        });

        ms_CarCatMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                carTypeId = String.valueOf(CarType.getCarTypes().get(position).getCar_Type_ID());
                Log.i("cartyep",carTypeId);
            }
        });


        ms_DurationMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                for (int i = 0; i<DurationAndCost.getDurationAndCosts().size(); i++ ){
                    Log.d("ON loop ", "onItemSelected:" + DurationAndCost.getDurationAndCosts().get(i).getDuration_Type_ID());
                    if (DurationAndCost.getDurationAndCosts().get(i).getDuration_Type_ID() == Integer.parseInt(whereToID)){
                        durationID = String.valueOf(DurationAndCost.getDurationAndCosts().get(i+position).getDuration_ID());
                        break;
                    }
                }
                Log.d("whereccc", whereToID);
                Log.i("durationCCC", durationID);
            }
        });



        ms_DateTextInputEditText.setClickable(false);
        ms_DateTextInputEditText.setFocusable(false);
        ms_TimeTextInputEditText.setClickable(false);
        ms_TimeTextInputEditText.setFocusable(false);

        final Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 4);

        ms_DateTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            DatePickerDialog dp = new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dp.getDatePicker().setMinDate(new Date().getTime());
                dp.getDatePicker().setMaxDate(c.getTimeInMillis());
                dp.show();
            }
        });

        ms_TimeTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                Calendar time = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                final int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                       String today = sdf.format(new Date(System.currentTimeMillis()));
                        if (today.contentEquals(ms_DateTextInputEditText.getText().toString().trim()))
                        {
                            ms_TimeTextInputEditText.setText( selectedHour + ":" + selectedMinute);
                        }
                        else ms_TimeTextInputEditText.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        return view;
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        ms_DateTextInputEditText.setText(sdf.format(myCalendar.getTime()));
        ms_TimeTextInputEditText.setEnabled(true);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.carCatInfoAppCompatImageButton, R.id.whereInfoAppCompatImageButton, R.id.durationInfoAppCompatImageButton, R.id.submitRequestAppCompatButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.carCatInfoAppCompatImageButton:
                break;
            case R.id.whereInfoAppCompatImageButton:
                break;
            case R.id.durationInfoAppCompatImageButton:
                break;
            case R.id.submitRequestAppCompatButton:
                getDate();
                break;
        }
    }

    private void getDate() {
        from = ms_FromTextInputEditText.getText().toString().trim();
        to = ms_ToTextInputEditText.getText().toString().trim();


        sentDate();
    }

    private void sentDate() {
        if (!valid()) return;

        StringRequest request = new StringRequest(Request.Method.POST, Boo.MS_REQUEST_CAR, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    new Messages(getActivity(), null, jsonObject.getString(Boo.REPLAY_SERVER_RESPONSE), true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(getActivity()).NoInternet();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();

                params.put(Boo.KEY_USER_ID, String.valueOf(User.getUserID()));
                params.put(Boo.KEY_CAR_TYPE_ID, carTypeId);
                params.put(Boo.KEY_USING_TYPE_ID, whereToID);
                params.put(Boo.KEY_FROM, from);
                params.put(Boo.KEY_TO, to);
                params.put(Boo.KEY_DURATION_ID, durationID);
                params.put(Boo.KEY_PICKUP_DATE, ms_DateTextInputEditText.getText().toString());
                params.put(Boo.KEY_PICKUP_TIME, ms_TimeTextInputEditText.getText().toString());

                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(request);
    }

    private boolean valid() {
        boolean valid = true;

        if (from.isEmpty())
        {
            ms_FromTextInputEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_FromTextInputEditText.setError(null);

        if (to.isEmpty()){
            ms_ToTextInputEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_ToTextInputEditText.setError(null);

        if (ms_DateTextInputEditText.getText().toString().isEmpty()){
            ms_DateTextInputEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_DateTextInputEditText.setError(null);

        if (ms_TimeTextInputEditText.getText().toString().isEmpty()){
            ms_TimeTextInputEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_TimeTextInputEditText.setError(null);

        return valid;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
