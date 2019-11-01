package com.layyan.colorcheckbox;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private int[] checkBoxesIdArray = {R.id.cb_Red,R.id.cb_Green,R.id.cb_Blue};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Note: Need to cast
        for (int i = 0; i < checkBoxesIdArray.length ; i++) {
            ((CheckBox)findViewById(checkBoxesIdArray[i])).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeBackground(v);
                }
            });
        }

    }

    public void changeBackground(View v){
        final ConstraintLayout layoutMain = findViewById(R.id.layout_Main);
        int colour = R.color.white;
        if(((CheckBox)v).isChecked()){
            switch (v.getId()){
                case R.id.cb_Red:
                    colour = R.color.light_red;
                    break;
                case R.id.cb_Green:
                    colour = R.color.light_green;
                    break;
                case R.id.cb_Blue:
                    colour = R.color.light_blue;
                    break;
                default:
                    colour = R.color.white;
                    break;
            }
            layoutMain.setBackgroundColor(getResources().getColor(colour));
        }else{
            layoutMain.setBackgroundColor(getResources().getColor(colour));
        }
    }

    public void showToastWhenOnClick(View view){
        CheckBox[] checkBoxesArray = {((CheckBox) findViewById(checkBoxesIdArray[0])),((CheckBox) findViewById(checkBoxesIdArray[1])),((CheckBox) findViewById(checkBoxesIdArray[2]))};
        HashMap<CheckBox,Boolean> checkboxMap = new HashMap<>();

        for (int i = 0; i < checkBoxesArray.length; i++) {
            checkboxMap.put(checkBoxesArray[i],checkBoxesArray[i].isChecked());
        }

        String displayText_2 = "";
        String displayText_1 = "";
        for (int j = 0; j < checkBoxesArray.length ; j++) {
            displayText_1 = "Color Selected: ";
            if(checkboxMap.get(checkBoxesArray[j])){
                displayText_2 += checkBoxesArray[j].getText().toString() + " " ;
            }
        }
        if(displayText_2.equals("")){
            displayText_2 = "None";
        }

        Toast.makeText(getApplicationContext(),displayText_1 + displayText_2,Toast.LENGTH_SHORT).show();
    }
}
