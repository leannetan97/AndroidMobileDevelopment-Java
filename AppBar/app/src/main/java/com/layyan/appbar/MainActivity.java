package com.layyan.appbar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((Button) findViewById(R.id.btn_child_activity)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toChildActivity = new Intent(v.getContext(), ChildActivity.class);
                startActivity(toChildActivity);
                //or
//                Intent toChildActivity = new Intent(getApplicationContext(), ChildActivity.class);
//                startActivity(toChildActivity);
                //or
//                navigateToChildActivity(v);
            }

        });
    }

    public void navigateToChildActivity(View v){
        Intent toChildActivity = new Intent(this, ChildActivity.class);
        startActivity(toChildActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_item_1:
                return true;
            case R.id.action_favourite:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}