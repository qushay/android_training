package com.test.androidtrainingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;
    EditText mEditText;
    CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);
        mCheckBox = (CheckBox) findViewById(R.id.checkBox);
        mButton.setOnClickListener(new ButtonAction());
        mCheckBox.setOnClickListener(new CheckboxAction());
    }

    class CheckboxAction implements CheckBox.OnClickListener{
        @Override
        public void onClick(View view) {
            if (mCheckBox.isChecked()){
                mCheckBox.setText("Show");
                mButton.setVisibility(View.VISIBLE);
            } else {
                mCheckBox.setText("Hide");
                mButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    class ButtonAction implements Button.OnClickListener{
        @Override
        public void onClick(View view) {
            // action of button
            mTextView.setText(mEditText.getText().toString());
        }
    }
}
