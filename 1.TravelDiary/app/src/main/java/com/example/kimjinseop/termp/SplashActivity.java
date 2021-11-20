package com.example.kimjinseop.termp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    Button button;
    ImageView backGround;
    public static Activity AActivity;
    public static SQL helper;
    String on;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        AActivity = SplashActivity.this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);
        FrameLayout l = (FrameLayout) findViewById(R.id.linear);
        button = (Button) findViewById(R.id.button);
        backGround = (ImageView) findViewById(R.id.background);
        //     backGround.setImageResource(R.drawable.);


//        Intent intent = new android.content.Intent(getApplicationContext(), select.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.fade, R.anim.hold);
        helper = new SQL(this);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
//                Intent intent = new android.content.Intent(getApplicationContext(), FirstMap.class);
//                startActivity(intent);

                helper.data(5);
                on = helper.on;

                if(on.equals("true")){
                    Intent intent = new android.content.Intent(getApplicationContext(), Insert_password.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new android.content.Intent(getApplicationContext(), select.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                }
            }
        });

    }
}
