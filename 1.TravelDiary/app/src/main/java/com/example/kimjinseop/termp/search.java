package com.example.kimjinseop.termp;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class search extends AppCompatActivity {
    static final String[] Category = {"혼자의 여행", "가족들과 함께", "친구와 함께", "그룹원들과 함께"} ;
    static String SEARCH;
    static int TABLE_NUMBER;

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_search);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Category) ;

        final ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);

                for(int i = 0 ; i < Category.length; i++){
                    if(Category[i].equals(strText)){
                        AlertDialog.Builder alert_context = new AlertDialog.Builder(search.this);
                        final EditText input = new EditText(search.this);
                        alert_context.setView(input);
                        final int finalI = i;
                        alert_context.setMessage("검색하고 싶은 제목을 쓰세요").setCancelable(true).setPositiveButton("검색",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String value = input.getText().toString();
                                        SEARCH = value;
                                        TABLE_NUMBER = finalI;
                                        if(!SEARCH.equals("")) {
                                            Intent intent = new android.content.Intent(getApplicationContext(), search_list.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            Toast.makeText(search.this, "다시 입력해주세요", Toast.LENGTH_SHORT).show();
                                            listview.performClick();
                                        }
                                    }
                                });
                        AlertDialog alert = alert_context.create();
                        alert.show();
                    }
                }
            }
        }) ;
    }
}