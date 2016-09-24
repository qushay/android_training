package com.test.androidtrainingtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.test.androidtrainingtest.datasource.DatabaseHelper;
import com.test.androidtrainingtest.datasource.RestClient;
import com.test.androidtrainingtest.entity.User;

import java.sql.SQLException;

import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends Activity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private UserLoginTask mAuthTask = null;
    final Gson gson = new Gson();
    private DatabaseHelper databaseHelper = null;


    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Check for empty data in the form
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    showDialog();
                    mAuthTask = new UserLoginTask(email, password);
                    mAuthTask.execute((Void) null);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext(),
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               // do register
            }
        });

    }

    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;
        private String errMessage;
        private User mUser;

        UserLoginTask(String email, String password) {
            mUsername = email;
            mPassword = Utils.sha1(password);
            errMessage = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            RestClient.LoginApi LoginApi = RestClient.create(RestClient.LoginApi.class);
            try {
                Call<JsonObject> call = LoginApi.login(mUsername, mPassword);
                Response<JsonObject> response = call.execute();
                // failed
                if (!response.isSuccessful()) {
                    JsonObject errorBody = gson.fromJson(response.errorBody().string(), JsonObject.class);
                    errMessage = errorBody.get("message").getAsString();
                    return false;
                }
                JsonObject body = response.body();
                if (body.get("success").getAsBoolean()){
                    mUser = gson.fromJson(body.get("user").getAsJsonObject().toString(), User.class);
                    errMessage = body.get("message").getAsString();
                    try {
                        final Dao<User, Integer> userDao = getHelper().getUserDao();
                        userDao.createOrUpdate(mUser);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else {
                    errMessage = body.get("message").getAsString();
                    return false;
                }
            } catch (Exception e) {
                // server not connect
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            hideDialog();
            try {
                if (success) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(User.TAG_UID, mUser.getUid());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);       // remove whole nav stack
                    LoginActivity.this.startActivity(intent);
                    finish();
                }
            }
            catch (Exception e) {
                hideDialog();
            }
        }

        @Override
        protected void onCancelled() {
            hideDialog();
        }
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}