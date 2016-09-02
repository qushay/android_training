package com.test.androidtrainingtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.test.androidtrainingtest.adapter.DaftarPertanyaanAdapter;
import com.test.androidtrainingtest.datasource.RestClient;
import com.test.androidtrainingtest.entity.Question;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class DaftarPertanyaanActivity extends AppCompatActivity {

    private GridView mGridView;
    private ProgressDialog pDialog;
    final Gson gson = new Gson();
    String[] mTitle = {"Title 1","Title 2","Title 3","Title 4","Title 5",
            "Title 6","Title 7","Title 8","Title 9","Title 10"};
    int[] mJumlah = {3,6,2,5,7,7,2,3,5,5};
    private QuestionsListTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pertanyaan);
        mGridView = (GridView) findViewById(R.id.gridView);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        showDialog();
        mAuthTask = new QuestionsListTask();
        mAuthTask.execute((Void) null);
    }

    class ListviewAction implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(DaftarPertanyaanActivity.this
                    ,mTitle[i]
                    ,Toast.LENGTH_SHORT)
                    .show();
        }
    }


    /**
     * Represents an asynchronous login task used to authenticate
     * the user.
     */
    public class QuestionsListTask extends AsyncTask<Void, Void, Boolean> {

        private String errMessage;
        private List<Question> mQuestions;

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
                    DaftarPertanyaanAdapter adapter = new DaftarPertanyaanAdapter(DaftarPertanyaanActivity.this,mQuestions);
                    mGridView.setAdapter(adapter);
                    mGridView.setOnItemClickListener(new ListviewAction());
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
