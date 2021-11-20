package com.example.kimjinseop.termp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.example.kimjinseop.termp.Insert_password.pas;
import static com.example.kimjinseop.termp.SplashActivity.AActivity;


public class select extends AppCompatActivity {
    private Button alone,family,friend,group,search,setting;
    Activity log;
    String on;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        log = AActivity;
        log.finish();
        if(pas!=null){
            pas.finish();
        }

        alone = (Button) findViewById(R.id.button1);
        family = (Button) findViewById(R.id.button2);
        friend = (Button) findViewById(R.id.button3);
        group = (Button) findViewById(R.id.button4);
        search = (Button) findViewById(R.id.button5);
        setting = (Button) findViewById(R.id.button6);

        alone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), alone.class);
                startActivity(intent);
            }
        });

        family.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), family.class);
                startActivity(intent);
            }
        });

        friend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), friend.class);
                startActivity(intent);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), group.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), search.class);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new android.content.Intent(getApplicationContext(), setting.class);
                startActivity(intent);
            }
        });
    }
}
