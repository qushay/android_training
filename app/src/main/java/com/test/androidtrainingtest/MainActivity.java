package com.test.androidtrainingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Spinner mSpinner;
    String[] mBuah = {"Apel","Jeruk","Pisang","Semangka","Nanas"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,mBuah);
        mSpinner.setAdapter(spinnerAdapter);
        mSpinner.setOnItemSelectedListener(new spinnerAction());
    }

    class spinnerAction implements Spinner.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            mTextView.setText(adapterView.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}
