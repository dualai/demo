package com.pig.ui.density;

import android.app.Activity;
import android.os.Bundle;

import com.pig.ui.R;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Density.setDensity(getApplication(),this);
        setContentView(R.layout.activity_density);
    }
}
