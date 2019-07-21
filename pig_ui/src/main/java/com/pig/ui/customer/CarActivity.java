package com.pig.ui.customer;

import android.app.Activity;
import android.os.Bundle;

public class CarActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CarView(this));
    }
}
