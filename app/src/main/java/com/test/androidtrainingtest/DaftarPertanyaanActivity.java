package com.test.androidtrainingtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.test.androidtrainingtest.adapter.DaftarPertanyaanAdapter;
import com.test.androidtrainingtest.datasource.DatabaseHelper;
import com.test.androidtrainingtest.datasource.RestClient;
import com.test.androidtrainingtest.entity.Question;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DaftarPertanyaanActivity extends AppCompatActivity {

    private GridView mGridView;
    private ProgressDialog pDialog;
    final Gson gson = new Gson();
    private DatabaseHelper databaseHelper = null;
    private QuestionsListTask mAuthTask = null;
    private List<Question> mQuestions;
    private DaftarPertanyaanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pertanyaan);
        mGridView = (GridView) findViewById(R.id.gridView);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        try {
            final Dao<Question, Integer> questionDao = getHelper().getQuestionDao();
            mQuestions = questionDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (mQuestions != null && !mQuestions.isEmpty()) {
            adapter = new DaftarPertanyaanAdapter(DaftarPertanyaanActivity.this,mQuestions);
            mGridView.setAdapter(adapter);
        }

        showDialog();
        mAuthTask = new QuestionsListTask();
        mAuthTask.execute((Void) null);

        mGridView.setOnItemClickListener(new ListviewAction());
    }

    class ListviewAction implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            Toast.makeText(DaftarPertanyaanActivity.this
//                    ,adapterView
//                    ,Toast.LENGTH_SHORT)
//                    .show();
        }
    }

    private DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this,DatabaseHelper.class);
        }
        return databaseHelper;
    }


    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class QuestionsListTask extends AsyncTask<Void, Void, Boolean> {

        private String errMessage;

        QuestionsListTask() {
            errMessage = "";
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            RestClient.QuestionApi questionList = RestClient.create(RestClient.QuestionApi.class);
            try {
                Call<JsonObject> call = questionList.questionList();
                Response<JsonObject> response = call.execute();
                // failed
                if (!response.isSuccessful()) {
                    JsonObject errorBody = gson.fromJson(response.errorBody().string(), JsonObject.class);
                    errMessage = errorBody.get("message").getAsString();
                    return false;
                }
                JsonObject body = response.body();
                if (body.get("success").getAsBoolean()){
                    Type listType = new TypeToken<ArrayList<Question>>() {}.getType();
                    mQuestions = gson.fromJson(body.get("questions").getAsJsonArray().toString(), listType);
                    errMessage = body.get("message").getAsString();
                    try {
                        final Dao<Question, Integer> questionDao = getHelper().getQuestionDao();
                        for (Question question : mQuestions) {
                            questionDao.createOrUpdate(question);
                        }
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
                    adapter = new DaftarPertanyaanAdapter(DaftarPertanyaanActivity.this,mQuestions);
                    mGridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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
