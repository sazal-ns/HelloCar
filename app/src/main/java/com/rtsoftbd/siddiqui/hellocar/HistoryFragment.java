package com.rtsoftbd.siddiqui.hellocar;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.rtsoftbd.siddiqui.hellocar.customAdapter.HistoryAdapter;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.ApplicationController;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Boo;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Messages;
import com.rtsoftbd.siddiqui.hellocar.models.CarType;
import com.rtsoftbd.siddiqui.hellocar.models.DurationAndCost;
import com.rtsoftbd.siddiqui.hellocar.models.History;
import com.rtsoftbd.siddiqui.hellocar.models.User;
import com.rtsoftbd.siddiqui.hellocar.models.UsingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.historyMaterialSpinner) MaterialSpinner ms_HistoryMaterialSpinner;
    @BindView(R.id.list) ListView ms_List;
    Unbinder unbinder;

    private LayoutInflater layoutInflater;
    private LayoutInflater listHead;
    private HistoryAdapter historyAdapter;
    private List<History> histories = new ArrayList<>();

    private History history;

    TextView carType;
    TextView usingType;
    TextView duration ;
    TextView dateTime;
    TextView from;
    TextView to;
    TextView cost;
    AppCompatButton button;
    View viewl;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HistoryFragment newInstance(String param1, String param2) {
        HistoryFragment fragment = new HistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        unbinder = ButterKnife.bind(this, view);

        history = new History();
        ms_HistoryMaterialSpinner.setItems("Pending", "Accepted", "Rejected", "Cancel");

        loadHistory(Boo.PENDING);

        listHead = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = listHead.inflate(R.layout.list_row, null);
        TextView tv1 = (TextView) v.findViewById(R.id.fromTextView);
        TextView tv2 = (TextView) v.findViewById(R.id.toTextView);
        TextView tv3 = (TextView) v.findViewById(R.id.dateTextView);

        tv1.setText(getResources().getString(R.string.from));
        tv2.setText(getResources().getString(R.string.to));
        tv3.setText(getResources().getString(R.string.date));

        ms_List.addHeaderView(v);

        ms_HistoryMaterialSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                loadHistory(position);
            }
        });

        historyAdapter = new HistoryAdapter(getActivity(), histories);
        ms_List.setAdapter(historyAdapter);

        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewl = layoutInflater.inflate(R.layout.details, null);
        button = (AppCompatButton) viewl.findViewById(R.id.cancelAppCompatButton);
        carType = (TextView) viewl.findViewById(R.id.carTypeNameTextView);
        usingType = (TextView) viewl.findViewById(R.id.usingTypeNameTextView);
        duration = (TextView) viewl.findViewById(R.id.durationNameAppCompatTextView);
        dateTime = (TextView) viewl.findViewById(R.id.dateTimeNameTextView);
        from = (TextView) viewl.findViewById(R.id.fromNameTextView);
        to = (TextView) viewl.findViewById(R.id.toNameTextView);
        cost = (TextView) viewl.findViewById(R.id.costNameTextView);

        ms_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                history = (History) parent.getItemAtPosition(position);

                from.setText(history.getFrom_Area());
                to.setText(history.getTo_Area());
                dateTime.setText(history.getPickup_Date() + " " + history.getPickup_Time());

                for (int i =0; i< DurationAndCost.getDurationAndCosts().size(); i++){
                    if (DurationAndCost.getDurationAndCosts().get(i).getDuration_ID() == history.getDuration_ID()){
                        duration.setText(DurationAndCost.getDurationAndCosts().get(i).getDuration_Name());
                        cost.setText(String.valueOf(DurationAndCost.getDurationAndCosts().get(i).getCost()) +
                        getResources().getString(R.string.tk));
                        break;
                    }
                }

                for (int i =0; i< CarType.getCarTypes().size(); i++){
                    if (CarType.getCarTypes().get(i).getCar_Type_ID()== history.getCar_Type_ID()){
                        carType.setText(CarType.getCarTypes().get(i).getCar_Type_Name());
                        break;
                    }
                }

                for (int i = 0; i< UsingType.getUsingTypes().size(); i++){
                    if (UsingType.getUsingTypes().get(i).getUsing_Type_ID() == history.getUsing_Type_ID()){
                        usingType.setText(UsingType.getUsingTypes().get(i).getUsing_Type_Name());
                        break;
                    }
                }
                final MaterialDialog d =  new MaterialDialog.Builder(getContext())
                        .customView(viewl, true)
                        .autoDismiss(true)
                        .show();

                if (history.getRequest_State() != 0) button.setVisibility(View.GONE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        StringRequest request = new StringRequest(Request.Method.POST, Boo.MS_IS_CANCEL_REQUEST, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.contains("true")){
                                    ApplicationController.getInstance().cancelPendingRequests("c");


                                    new MaterialDialog.Builder(getContext())
                                            .content(getResources().getString(R.string.are_you))
                                            .positiveText(getResources().getString(R.string.sure))
                                            .positiveColor(getResources().getColor(R.color.green))
                                            .negativeText(getResources().getString(R.string.no))
                                            .negativeColor(getResources().getColor(R.color.red))
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    StringRequest request1 = new StringRequest(Request.Method.POST, Boo.MS_CANCEL_REQUEST,
                                                            new Response.Listener<String>() {
                                                                @Override
                                                                public void onResponse(String response) {
                                                                    ms_HistoryMaterialSpinner.setSelectedIndex(Boo.REJECT_BY_CLIENT);
                                                                    loadHistory(Boo.REJECT_BY_CLIENT);
                                                                    d.dismiss();
                                                                }
                                                            }, new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {
                                                            error.printStackTrace();
                                                        }
                                                    }){
                                                        @Override
                                                        protected Map<String, String> getParams() throws AuthFailureError {
                                                            Map<String , String > params = new HashMap<String, String>();
                                                            params.put(Boo.KEY_ID, String.valueOf(history.getRequest_ID()));
                                                            return params;
                                                        }
                                                    };

                                                    ApplicationController.getInstance().addToRequestQueue(request1);

                                                }
                                            })
                                            .autoDismiss(true)
                                            .show();
                                    } else{
                                    ms_HistoryMaterialSpinner.setSelectedIndex(Boo.REJECT_BY_ADMIN);
                                    loadHistory(Boo.REJECT_BY_ADMIN);
                                    d.dismiss();
                                    }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String , String > params = new HashMap<String, String>();
                                params.put(Boo.KEY_ID, String .valueOf(history.getRequest_ID()));
                                return params;
                            }
                        };

                        ApplicationController.getInstance().addToRequestQueue(request,"c");

                    }
                });
            }
        });

        return view;
    }

    private void loadHistory(final int state) {

        StringRequest request = new StringRequest(Request.Method.POST, Boo.MS_HISTORY, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                histories.clear();
                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        History history =  new History(jsonObject.getInt(Boo.REPLAY_REQUEST_ID), jsonObject.getInt(Boo.REPLAY_USER_ID),
                                    jsonObject.getInt(Boo.REPLAY_CAR_TYPE_ID), jsonObject.getInt(Boo.REPLAY_USING_TYPE_ID), jsonObject.getInt(Boo.REPLAY_DURATION_ID),
                                    jsonObject.getInt(Boo.REPLAY_REQUEST_STATE), jsonObject.getInt(Boo.REPLAY_STAFF_ID), jsonObject.getString(Boo.REPLAY_FROM_AREA),
                                    jsonObject.getString(Boo.REPLAY_TO_AREA), jsonObject.getString(Boo.REPLAY_PICKUP_DATE),
                                    jsonObject.getString(Boo.REPLAY_PICKUP_TIME));

                        histories.add(history);
                    }
                    historyAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage().contains("Unable to resolve host"))
                    new Messages(getActivity()).noInternet();
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();
                params.put(Boo.KEY_ID, String.valueOf(User.getUserID()));
                params.put(Boo.KEY_STATE, String.valueOf(state));
                return params;
            }
        };

        ApplicationController.getInstance().addToRequestQueue(request, "MS");
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
