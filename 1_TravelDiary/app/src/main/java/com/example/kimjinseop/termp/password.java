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

import static com.example.kimjinseop.termp.SplashActivity.helper;


public class password extends AppCompatActivity {
    static final String[] PASSWORD_MENU = {"비밀번호 ON/OFF","비밀번호 변경"};
    static boolean modify;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        helper.data(5);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,PASSWORD_MENU) ;

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter) ;


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get TextView's Text.
                String strText = (String) parent.getItemAtPosition(position);

                for(int i = 0 ; i < PASSWORD_MENU.length; i++){
                    if(PASSWORD_MENU[i].equals(strText)){
                        switch (i){
                            case 0:
                                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(password.this);
                                alert_confirm.setMessage("비밀번호 설정?").setCancelable(true).setPositiveButton("ON",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                modify = true;
//                                                Toast.makeText(password.this, helper.password, Toast.LENGTH_SHORT).show();  //
                                                Intent intent2 = new Intent(getApplicationContext(), Insert_password.class);
                                                startActivity(intent2);
                                            }
                                        }).setNegativeButton("OFF",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                helper.deletePassword(null);
                                                helper.data(5);
                                                helper.deletePassword(helper.password);
                                                return;
                                            }
                                        });
                                AlertDialog alert = alert_confirm.create();
                                alert.show();

                                break;
                            case 1:
                                modify = true;
                                Intent intent2 = new android.content.Intent(getApplicationContext(), Insert_password.class);
                                startActivity(intent2);
                                break;
                        }
                    }
                }
            }
        }) ;
    }
}
