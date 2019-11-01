package com.layyan.mathematicapp;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addition(v);
            }
        });
    }


    public void addition(View view){
        EditText etvFirstNum = (EditText)findViewById(R.id.etv_firstNum);
        EditText etvSecondNum = (EditText)findViewById(R.id.etv_secondNum);
        int result = Integer.parseInt(etvFirstNum.getText().toString()) + Integer.parseInt(etvSecondNum.getText().toString());
        TextView resultTextView = (TextView) findViewById(R.id.tv_result);
        resultTextView.setText(Integer.toString(result));
        hideKeyboard(view);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
