package com.layyan.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                if (v == findViewById(R.id.BtnPenguin)) {
                    fragment = new FragmentPenguin();
                } else {
                    fragment = new FragmentRabbit();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.FragOutput, fragment);
                transaction.commit();
            }
        };
        Button BtnPenguin = (Button) findViewById(R.id.BtnPenguin);
        BtnPenguin.setOnClickListener(listener);
        Button BtnRabbit = (Button) findViewById(R.id.BtnRabbit);
        BtnRabbit.setOnClickListener(listener);

    }
}
