package com.layyan.multipleact;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class CLAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cl);
    }

    public void backToMain(View v){
        finish();
    }
}
