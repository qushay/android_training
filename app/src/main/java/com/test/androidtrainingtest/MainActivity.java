package com.test.androidtrainingtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton.setOnClickListener(new ButtonAction());
    }

    class ButtonAction implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, DaftarPertanyaanActivity.class);
            startActivity(i);
        }
    }
}
