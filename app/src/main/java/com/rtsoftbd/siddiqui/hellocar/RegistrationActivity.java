package com.rtsoftbd.siddiqui.hellocar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.rtsoftbd.siddiqui.hellocar.helpingHand.AndroidMultiPartEntity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {
    private static final String TAG = "RegistrationActivity";

    private static int RESULT_LOAD_IMAGE = 1;

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

    private String imagePath, name, address, email, userName, password, repeatPassword;
    private int nid, number, emg_number;

    private  long totalSize = 0;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        saveImage(getBitmapFromAsset("user.png"),
                "user.png");
    }

    private Bitmap getBitmapFromAsset(String strName) {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(strName);
            if (istr.equals(null)) {
                bitmap = BitmapFactory.decodeStream(assetManager.open("user.png"));
            } else {
                bitmap = BitmapFactory.decodeStream(istr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    private void saveImage(Bitmap finalBitmap, String imageName) {
        File sdCard = Environment.getExternalStorageDirectory();
        File myDir = new File(sdCard.getAbsolutePath() +"/"+ getPackageName());
        String path = myDir.getPath();
        myDir.mkdirs();

        // String fname = "save_fatwa.jpg";
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


    @OnClick({R.id.uploadImageButton, R.id.registrationAppCompatButton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.uploadImageButton:
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        userName = ms_UsernameEditText.getText().toString().trim();
        password = ms_PasswordEditText.getText().toString().trim();
        repeatPassword = ms_RepeatPasswordEditText.getText().toString().trim();

        try {
            nid = Integer.parseInt(ms_NidEditText.getText().toString().trim());
            ms_NidEditText.setError(null);
        }catch (NumberFormatException e){
            ms_NidEditText.setError(getResources().getString(R.string.invalid_number));
            e.printStackTrace();
        }

        try {
            number = Integer.parseInt(ms_NumberEditText.getText().toString().trim());
            ms_NumberEditText.setError(null);
        }catch (NumberFormatException e){
            ms_NumberEditText.setError(getResources().getString(R.string.invalid_number));
            e.printStackTrace();
        }

        try {
            emg_number = Integer.parseInt(ms_EmgContactEditText.getText().toString().trim());
            ms_EmgContactEditText.setError(null);
        }catch (NumberFormatException e){
            ms_EmgContactEditText.setError(getResources().getString(R.string.invalid_number));
            e.printStackTrace();
        }

        new UploadFileToServer().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imagePath = cursor.getString(columnIndex);
            cursor.close();

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
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
        @Override
        protected void onPreExecute() {
            // setting progress bar to zero
            //progressBar.setProgress(0);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Making progress bar visible
           // progressBar.setVisibility(View.VISIBLE);

            // updating progress bar value
           // progressBar.setProgress(progress[0]);

            // updating percentage value
           // txtPercentage.setText(String.valueOf(progress[0]) + "%");
        }

        @Override
        protected String doInBackground(Void... params) {
            return uploadFile();
        }

        @SuppressWarnings("deprecation")
        private String uploadFile() {
            String responseString = null;

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://hellocar.ga/Mobile_api/user_register");

            try {
                AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                        new AndroidMultiPartEntity.ProgressListener() {

                            @Override
                            public void transferred(long num) {
                                publishProgress((int) ((num / (float) totalSize) * 100));
                            }
                        });

                File sourceFile = new File(imagePath);
                Log.i(TAG, imagePath);
                // Adding file data to http body
                entity.addPart("userfile", new FileBody(sourceFile));

                // Extra parameters if you want to pass to server
               // entity.addPart("userfile", new StringBody(sourceFile.getName()));
                entity.addPart("User_Address", new StringBody(address));
                entity.addPart("User_Emergncy_Number", new StringBody(String.valueOf(emg_number)));
                entity.addPart("User_Email", new StringBody(email));
                entity.addPart("User_NID", new StringBody(String.valueOf(nid)));
                entity.addPart("Mobile_Number", new StringBody(String.valueOf(number)));
                entity.addPart("Full_Name", new StringBody(name));
                entity.addPart("User_Password", new StringBody(password));
                entity.addPart("User_Name", new StringBody(userName));


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
            Log.e(TAG, "Response from server: " + result);

            // showing the server response in an alert dialog
           // showAlert(result);

            super.onPostExecute(result);
        }

    }


}
