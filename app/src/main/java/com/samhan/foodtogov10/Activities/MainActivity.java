package com.samhan.foodtogov10.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.samhan.foodtogov10.Activities.detection_activity;
import com.samhan.foodtogov10.R;

public class MainActivity extends AppCompatActivity {

    Button btn_toBlog,btn_toDetection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_toBlog=findViewById(R.id.btn_toBlog);
        btn_toDetection=findViewById(R.id.btn_toDetection);

        btn_toDetection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), detection_activity.class);
                startActivity(intent);
            }
        });

        btn_toBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), nav_drawer_activity.class);
                startActivity(intent);
            }
        });

    }
}