package com.xzl.demo1.internation;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xzl.demo1.LLog;
import com.xzl.demo1.R;

import java.util.Locale;

import butterknife.OnClick;

/**
 * Created by xuluming on 2019/5/5
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView descText;
    private Button changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internation);

        descText = findViewById(R.id.tv_desc);
        changeBtn = findViewById(R.id.btn_change);
        changeBtn.setOnClickListener(this);

        InternationManager.getInstance().start(getApplicationContext());

        LLog.d(InternationManager.getInstance().getLanguage());

        String data = getMString(R.string.app_name,"你好",1200.10234f);

        double abcc = 1203.001234d;
        String data22 = getMString(R.string.app_name2,"你好",1200.10234f,abcc,10);
        LLog.d(data22);

        descText.setText(getMString(R.string.meters_n,1.2323f));
    }

    public String getMString(@StringRes int resId, Object ...formatArgs){
        return this.getString(resId,formatArgs);
    }

    public String getMString(@StringRes int resId){
        return this.getString(resId);
    }

    @Override
    public void onClick(View v) {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = Locale.TRADITIONAL_CHINESE;
        resources.updateConfiguration(config, dm);

    }
}
