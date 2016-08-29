package com.test.androidtrainingtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    Button mButton;
    EditText mEditText;
    RadioButton mRadioButtonSabtu, mRadioButtonMinggu, mRadioButtonSenin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textView);
        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);
        mRadioButtonSabtu = (RadioButton) findViewById(R.id.radioButton);
        mRadioButtonMinggu = (RadioButton) findViewById(R.id.radioButton2);
        mRadioButtonSenin = (RadioButton) findViewById(R.id.radioButton3);
        mButton.setOnClickListener(new ButtonAction());
        mRadioButtonSabtu.setOnClickListener(new RadioButtonAction());
        mRadioButtonMinggu.setOnClickListener(new RadioButtonAction());
        mRadioButtonSenin.setOnClickListener(new RadioButtonAction());
    }

    class RadioButtonAction implements CheckBox.OnClickListener{
        @Override
        public void onClick(View view) {
            if (view == mRadioButtonSabtu){
                mTextView.setText("Benar");
            } else if (view == mRadioButtonMinggu) {
                mTextView.setText("Salah");
            } else {
                mTextView.setText("Salah");
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
