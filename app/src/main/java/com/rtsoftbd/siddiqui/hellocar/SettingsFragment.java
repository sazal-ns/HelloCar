package com.rtsoftbd.siddiqui.hellocar;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.ApplicationController;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Boo;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Messages;
import com.rtsoftbd.siddiqui.hellocar.models.User;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class SettingsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String SP = "ms";

    @BindView(R.id.usernameEditText) EditText ms_UsernameEditText;
    @BindView(R.id.emailEditText) EditText ms_EmailEditText;
    @BindView(R.id.passwordEditText) EditText ms_PasswordEditText;
    @BindView(R.id.updateAppCompatButton) AppCompatButton ms_UpdateAppCompatButton;
    @BindView(R.id.logoutAppCompatButton) AppCompatButton ms_LogoutAppCompatButton;
    Unbinder unbinder;

    private String email, password;


    private String language;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, view);

        ms_EmailEditText.setText(User.getEmail());
        ms_UsernameEditText.setText(User.getUserName());

        return view;
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

    @OnClick({R.id.updateAppCompatButton, R.id.logoutAppCompatButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.updateAppCompatButton:
                getData();
                break;
            case R.id.logoutAppCompatButton:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    private void getData() {
        email = ms_EmailEditText.getText().toString().trim();
        password = ms_PasswordEditText.getText().toString().trim();

        update();
    }

    private void update() {
        if (!password.isEmpty()){
            if (!password.contentEquals(User.getPassword())){
                new MaterialDialog.Builder(getContext())
                        .title(getResources().getString(R.string.provide_old_password))
                        .input(null, null, false, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                if (User.getPassword().contentEquals(input)) {
                                    StringRequest request = new StringRequest(Request.Method.POST, Boo.MS_UPDATE_SAFETY, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if (response.contains("true")){
                                                new Messages(getActivity(), getResources().getString(R.string.success),
                                                        getResources().getString(R.string.update), true);
                                            }
                                            else new Messages(getActivity(), getResources().getString(R.string.error),
                                                    getResources().getString(R.string.server_error), true);
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String , String > params = new HashMap<String, String>();
                                            params.put(Boo.KEY_ID, String.valueOf(User.getUserID()));
                                            params.put(Boo.KEY_EMAIL, email);
                                            params.put(Boo.KEY_LOGIN_PASSWORD, password);

                                            return params;
                                        }
                                    };
                                    ApplicationController.getInstance().addToRequestQueue(request);
                                }else new Messages(getActivity(),null, getResources().getString(R.string.password_not_match), true);
                            }
                        })
                        .show();
            }
        }
    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
