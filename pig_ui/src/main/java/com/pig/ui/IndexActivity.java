package com.pig.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pig.ui.customer.CarActivity;
import com.pig.ui.density.MainActivity;


public class IndexActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, CarActivity.class);
        startActivity(intent);
        finish();
//        setContentView(R.layout.activity_test);
    }
}
