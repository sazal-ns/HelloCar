package com.rtsoftbd.siddiqui.hellocar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.AndroidMultiPartEntity;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.ApplicationController;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Boo;
import com.rtsoftbd.siddiqui.hellocar.helpingHand.Messages;
import com.rtsoftbd.siddiqui.hellocar.models.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    private static int RESULT_LOAD_IMAGE = 1;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @BindView(R.id.thumbnailImageView) ImageView ms_ThumbnailImageView;
    @BindView(R.id.uploadImageButton) ImageButton ms_UploadImageButton;
    @BindView(R.id.nameEditText) EditText ms_NameEditText;
    @BindView(R.id.numberEditText) EditText ms_NumberEditText;
    @BindView(R.id.nidEditText) EditText ms_NidEditText;
    @BindView(R.id.addressEditText) EditText ms_AddressEditText;
    @BindView(R.id.emgContactEditText) EditText ms_EmgContactEditText;
    @BindView(R.id.emailEditText) EditText ms_EmailEditText;
    @BindView(R.id.usernameEditText) EditText ms_UsernameEditText;
    @BindView(R.id.passwordEditText) EditText ms_PasswordEditText;
    @BindView(R.id.repeatPasswordEditText) EditText ms_RepeatPasswordEditText;
    @BindView(R.id.registrationAppCompatButton) AppCompatButton ms_RegistrationAppCompatButton;
    @BindView(R.id.scrollView) ScrollView ms_ScrollView;
    @BindView(R.id.logoImageView) ImageView ms_LogoImageView;
    Unbinder unbinder;

    private String imagePath, name, address, email, nid, emg_number, path;
    private long totalSize = 0;
    private ProgressDialog progressDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        ms_ScrollView.setBackgroundColor(getResources().getColor(android.R.color.white));
        ms_EmailEditText.setVisibility(View.GONE);
        ms_UsernameEditText.setVisibility(View.GONE);
        ms_PasswordEditText.setVisibility(View.GONE);
        ms_RepeatPasswordEditText.setVisibility(View.GONE);
        ms_RegistrationAppCompatButton.setText(getResources().getString(R.string.update));
        ms_NumberEditText.setClickable(false);
        ms_NumberEditText.setFocusable(false);
        ms_LogoImageView.setVisibility(View.GONE);

        progressDialog = new ProgressDialog(getContext());



        setData();

        return view;
    }

    private void setData() {
        ms_NameEditText.setText(User.getFullName());
        ms_NumberEditText.setText("0"+String.valueOf(User.getMobile()));
        ms_NidEditText.setText(String.valueOf(User.getnID()));
        ms_AddressEditText.setText(User.getAddress());
        ms_EmgContactEditText.setText("0"+String.valueOf(User.getEngContact()));

        imagePath = "";

        ImageRequest request = new ImageRequest(Boo.PICTURE_URL + User.getImageName(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                ms_ThumbnailImageView.setImageBitmap(response);

                BitmapDrawable bitmapDrawable = ((BitmapDrawable) ms_ThumbnailImageView.getDrawable());
                Bitmap bitmap = bitmapDrawable .getBitmap();
                saveImage(bitmap, User.getImageName());
            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        ApplicationController.getInstance().addToRequestQueue(request);

    }

    private void saveImage(Bitmap finalBitmap, String imageName) {
        File sdCard = Environment.getExternalStorageDirectory();
        File myDir = new File(sdCard.getAbsolutePath() +"/"+ getContext().getPackageName());
        path = myDir.getPath();
        myDir.mkdirs();

        File file = new File(myDir, imageName);
        if (file.exists()) {
            Log.i("file exists", "" + imageName);
            file.delete();
        } else {
            Log.i("file does not exists", "" + imageName);
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
            imagePath = path.concat("/"+imageName);

        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @OnClick({R.id.thumbnailImageView, R.id.uploadImageButton, R.id.registrationAppCompatButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.thumbnailImageView:
                break;
            case R.id.uploadImageButton:
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.registrationAppCompatButton:
                getData();
                break;
        }
    }

    private void getData() {
        name = ms_NameEditText.getText().toString().trim();
        address = ms_AddressEditText.getText().toString().trim();
        email = ms_EmailEditText.getText().toString().trim();
        nid = ms_NidEditText.getText().toString().trim();
        emg_number = ms_EmgContactEditText.getText().toString().trim();


        if (valid()){
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            new UploadFileToServer().execute();
            progressDialog.show();}
    }

    private boolean valid() {
        boolean valid = true;

        if (name.isEmpty() || name.length() <3){
            ms_NameEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_NameEditText.setError(null);

        if (!emg_number.matches("^01[15-9]\\d{8}$")){
            ms_EmgContactEditText.setError(getResources().getString(R.string.invalid_number));
            valid = false;
        }else ms_EmgContactEditText.setError(null);

        if (!nid.matches("^\\d{17}$")){
            ms_NidEditText.setError(getResources().getString(R.string.invalid_number));
            valid = false;
        }else ms_NidEditText.setError(null);

        if (address.isEmpty()){
            ms_AddressEditText.setError(getResources().getString(R.string.required));
            valid = false;
        }else ms_AddressEditText.setError(null);

        if (emg_number.contentEquals(String.valueOf(User.getMobile()))){
            ms_EmgContactEditText.setError(getResources().getString(R.string.emg_person_contact));
            valid = false;
        }else ms_EmgContactEditText.setError(null);

        return valid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

            File sourceFile = new File(imagePath);
            double fileSize = (sourceFile.length() / 1024.0) / 1024.0;
            if (fileSize >= 2.0) {
                new MaterialDialog.Builder(getContext())
                        .cancelable(true)
                        .content(getResources().getString(R.string.file_size) + " " + String.valueOf(fileSize))
                        .show();

                return;
            }

            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);

            } catch (IOException e) {
                e.printStackTrace();
            }
            ms_ThumbnailImageView.setImageBitmap(bmp);

        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContext().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setMessage("Uploading & Sing Up . . . " + progress[0]);
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Boo.MS_UPDATE_PROFILE);

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(imagePath);
                // Adding file data to http body
                entity.addPart(Boo.KEY_USER_FILE, new FileBody(sourceFile));
                // Extra parameters if you want to pass to server
                // entity.addPart("userfile", new StringBody(sourceFile.getName()));
                entity.addPart(Boo.KEY_USER_ADDRESS, new StringBody(address));
                entity.addPart(Boo.KEY_USER_EMERGENCY_NUMBER, new StringBody(String.valueOf(emg_number)));
                entity.addPart(Boo.KEY_USER_NID, new StringBody(String.valueOf(nid)));
                entity.addPart(Boo.KEY_FULL_NAME, new StringBody(name));
                entity.addPart(Boo.KEY_ID, new StringBody(String.valueOf(User.getUserID())));

                totalSize = entity.getContentLength();
                httppost.setEntity(entity);

                // Making server call
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity r_entity = response.getEntity();

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    // Server response
                    responseString = EntityUtils.toString(r_entity);
                } else {
                    responseString = "Error occurred! Http Status Code: "
                            + statusCode;
                }

            } catch (ClientProtocolException e) {
                responseString = e.toString();
            } catch (IOException e) {
                responseString = e.toString();
            }

            return responseString;

        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("RESULT", result);

            if (result.contains("true")) {
                new Messages(getActivity(), getResources().getString(R.string.success), getResources().getString(R.string.profile_updated), true);

            } else new MaterialDialog.Builder(getContext())
                    .title(getResources().getString(R.string.error))
                    .content(getResources().getString(R.string.update_failed))
                    .icon(getResources().getDrawable(R.drawable.ic_error_red_a700_36dp))
                    .show();

            progressDialog.dismiss();
            super.onPostExecute(result);
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
