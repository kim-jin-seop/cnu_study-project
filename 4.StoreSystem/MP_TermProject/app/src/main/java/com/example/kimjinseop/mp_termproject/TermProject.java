package com.example.kimjinseop.mp_termproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TermProject extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_project);
    }
    public void isClickSeller(View view){
        Intent intent = new Intent(getApplicationContext(),SellerActivity.class);
        startActivity(intent);
    }

    public void isClickProvider(View view) {
        Intent intent = new Intent(getApplicationContext(),providerActivity.class);
        startActivity(intent);
    }

    public void isClickPos(View view){
        Intent intent = new Intent(getApplicationContext(),posActivity.class);
        startActivity(intent);
    }
}
