package com.example.togglebutton.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.togglebutton.R;
import com.example.togglebutton.myview.ToggleSwitchView;
/**
 * Created by li biao
 * 2017/8/23
 * email:207563927@qq.com
 */
public class MainActivity extends AppCompatActivity
{
    private ToggleSwitchView switchView;
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchView = (ToggleSwitchView) findViewById(R.id.toggleView);
        switchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switchView.changeOpenState(flag);
                flag = !flag;
            }
        });
    }
}
