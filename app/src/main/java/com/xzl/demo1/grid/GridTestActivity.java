package com.xzl.demo1.grid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.xzl.demo1.R;

public class GridTestActivity extends AppCompatActivity {

    private GridLayout gridLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridtest);

        gridLayout = findViewById(R.id.gridlayout);

        String abc[] = new String[]{"nihao","ABC啊啊"};
        LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0;i<abc.length;i++){
            String str = abc[i];
            TextView textView = (TextView)(inflater.inflate(R.layout.view_text,gridLayout,false));
            textView.setText(str);
            gridLayout.addView(textView);
        }

    }
}
