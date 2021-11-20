package com.example.kimjinseop.termp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;



public class setting extends AppCompatActivity {
    static final String[] LIST_MENU = {"언어", "비밀번호", "도움말"} ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU) ;

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);

                for(int i = 0 ; i < LIST_MENU.length; i++){
                    if(LIST_MENU[i].equals(strText)){
                        switch (i){
                            case 0:
                                AlertDialog.Builder alert_context = new AlertDialog.Builder(setting.this);
                                final int finalI = i;
                                alert_context.setMessage("언어는 아직 준비중입니다!").setCancelable(true).setPositiveButton("확인",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                AlertDialog alert = alert_context.create();
                                alert.show();
//                                Intent intent1 = new android.content.Intent(getApplicationContext(), language.class);
//                                startActivity(intent1);
                                break;
                            case 1:
                                Intent intent2 = new android.content.Intent(getApplicationContext(), password.class);
                                startActivity(intent2);
                                break;
                            case 2:
                                AlertDialog.Builder alert_context1 = new AlertDialog.Builder(setting.this);
                                final int finalI1 = i;
                                alert_context1.setMessage("문의는 010-9999-6384로 주세요!").setCancelable(true).setPositiveButton("확인",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                AlertDialog alert1 = alert_context1.create();
                                alert1.show();

//                                Intent intent3 = new android.content.Intent(getApplicationContext(), help.class);
//                                startActivity(intent3);
                                break;
                        }
                    }
                }
            }
        }) ;
    }
}
