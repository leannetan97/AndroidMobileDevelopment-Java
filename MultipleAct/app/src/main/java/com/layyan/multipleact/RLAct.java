package com.layyan.multipleact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;


public class RLAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rl);
    }

    public void backToMain(View view) {
        String name = ((EditText)findViewById(R.id.et_name)).getText().toString();
        System.out.println("[DEBUG] Name: "+name);
        Intent nameData = new Intent();
        nameData.putExtra("NameEntered",name);
        setResult(RESULT_OK,nameData);
        finish();
    }
}

