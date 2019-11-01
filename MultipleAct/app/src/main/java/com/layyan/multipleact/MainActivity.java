package com.layyan.multipleact;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.btn_RelativeLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRelativeLayout(v);
            }
        });

        ((Button)findViewById(R.id.btn_ConstraintLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToConstraintLayout(v);
            }
        });

        ((Button)findViewById(R.id.btn_GridLayout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGridLayout(v);
            }
        });
    }

    private void navigateToConstraintLayout(View view) {
        Intent act_CL = new Intent(this, CLAct.class);
        startActivity(act_CL);
    }

    private void navigateToRelativeLayout(View view){
        Intent act_RL = new Intent(this, RLAct.class);
        startActivityForResult(act_RL,0);
    }

    private void navigateToGridLayout(View view){
        Intent act_GL = new Intent(this, CalculatorView.class);
        startActivity(act_GL);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if(resultCode == RESULT_OK) {
                String name = data.getStringExtra("NameEntered");
                Toast.makeText(this, "Welcome back, " + name + "!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
