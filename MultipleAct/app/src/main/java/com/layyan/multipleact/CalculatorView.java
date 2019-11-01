package com.layyan.multipleact;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CalculatorView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_view);

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToHomePage(v);
            }
        });
    }

    private void backToHomePage(View v){
        finish();
    }

    public void clearText(View v){
        TextView result = (TextView)findViewById(R.id.tv_result);
        result.setText(Integer.toString(0));
    }

    public void updateTextView(View v){
        switch (v.getId()){
            case R.id.btn_zero:
                updateText(0);
                break;
            case R.id.btn_one:
                updateText(1);
                break;
            case R.id.btn_two:
                updateText(2);
                break;
            case R.id.btn_three:
                updateText(3);
                break;
            case R.id.btn_four:
                updateText(4);
                break;
            case R.id.btn_five:
                updateText(5);
                break;
            case R.id.btn_six:
                updateText(6);
                break;
            case R.id.btn_seven:
                updateText(7);
                break;
            case R.id.btn_eight:
                updateText(8);
                break;
            case R.id.btn_nine:
                updateText(9);
                break;
            case R.id.btn_dot:
                append(".");
            default:
                break;
        }
    }

    public void performAction(View v){
        switch (v.getId()){
            case R.id.btn_plus:
                append(" + ");
                break;
            case R.id.btn_minus:
                append(" - ");
                break;
            case R.id.btn_multiply:
                append(" * ");
                break;
            case R.id.btn_divide:
                append(" / ");
                break;
            case R.id.btn_equal:
                performFullCalculation();
                break;
            default:
                break;
        }

    }

    private void updateText(int i){
        TextView result = (TextView)findViewById(R.id.tv_result);
        if (result.getText().toString().equals("0")) {
            result.setText(Integer.toString(i));
        } else {
            result.append(Integer.toString(i));
        }
    }

    private void append(String x){
        TextView result = (TextView)findViewById(R.id.tv_result);
            result.append(x);
    }

    private void setText(String x){
        TextView result = (TextView)findViewById(R.id.tv_result);
        result.setText(x);
    }

    private void performFullCalculation(){
        TextView result = (TextView)findViewById(R.id.tv_result);
        String solution = result.getText().toString();
        String[] arr = solution.split(" ");

        float r = Float.parseFloat(arr[0]);
        for (int i = 1; i < arr.length-1; i++) {
            if(arr[i].equals("+") || arr[i].equals("-")  || arr[i].equals("*")  || arr[i].equals("/")  ){
                switch (arr[i]){
                    case "+":
                        r += Float.parseFloat(arr[i+1]);
                        System.out.println(r);
                        break;
                    case "-":
                        r -= Float.parseFloat(arr[i+1]);
                        System.out.println("?" + r);
                        break;
                    case "*":
                        r *= Float.parseFloat(arr[i+1]);
                        System.out.println(r);
                        break;
                    case "/":
                        r /= Float.parseFloat(arr[i+1]);
                        System.out.println(r);
                        break;
                    default:
                        break;
                }
            }
        }
        String[] tempArr = Float.toString(r).split("[.]");
        if (tempArr[1].equals("0")) {
            setText(Integer.toString((int) r));
        } else {
            setText(Float.toString(r));
        }
    }
}
