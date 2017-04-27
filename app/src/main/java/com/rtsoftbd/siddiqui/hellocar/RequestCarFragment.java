package com.rtsoftbd.siddiqui.hellocar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.ForwardingListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rtsoftbd.siddiqui.hellocar.models.CarType;
import com.rtsoftbd.siddiqui.hellocar.models.DurationAndCost;
import com.rtsoftbd.siddiqui.hellocar.models.UsingType;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    private String time, from, to;

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

        ms_WhereMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                ms_DurationMaterialSpinner.clearComposingText();
                durationAndCosts.clear();
                for (int i = 0; i<DurationAndCost.getDurationAndCosts().size(); i++) {
                    if (DurationAndCost.getDurationAndCosts().get(i).getDuration_Type_ID() ==
                            UsingType.getUsingTypes().get(position).getUsing_Type_ID())
                    durationAndCosts.add(DurationAndCost.getDurationAndCosts().get(i).getDuration_Name() +
                            " " + DurationAndCost.getDurationAndCosts().get(i).getCost() + " TK");
                }
                ms_DurationMaterialSpinner.setItems(durationAndCosts);
            }
        });

        ms_DateTextInputEditText.setClickable(false);
        ms_DateTextInputEditText.setFocusable(false);
        ms_TimeTextInputEditText.setClickable(false);
        ms_TimeTextInputEditText.setFocusable(false);

        ms_DateTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ms_TimeTextInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        ms_TimeTextInputEditText.setText( selectedHour + ":" + selectedMinute);
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
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        ms_DateTextInputEditText.setText(sdf.format(myCalendar.getTime()));
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
                break;
        }
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
