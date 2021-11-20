package com.example.kimjinseop.termp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

import static com.example.kimjinseop.termp.SplashActivity.helper;
import static com.example.kimjinseop.termp.search.SEARCH;
import static com.example.kimjinseop.termp.search.TABLE_NUMBER;


public class search_list extends AppCompatActivity {
    LinkedList<String> str = new LinkedList<>();
    LinkedList<LatLng> str1 = new LinkedList<>();

    LinkedList<String> str3 = new LinkedList<>();
    LinkedList<LatLng> str4 = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        helper.data(TABLE_NUMBER);
        str = helper.str;
        str1 = helper.str2;
        int length = str.size();
        for(int i = 0; i < length; i++){
            String temp = str.pollFirst();
            LatLng temp1 = str1.pollFirst();
            if(temp.contains(SEARCH)){
                str3.add(temp);
                str4.add(temp1);
            }
        }
        str = null;
        str1 = null;

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, str3) ;

        ListView listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
                for(int i = 0 ; i < str3.size(); i++){
                    if(str3.get(i).equals(strText)){
                        if(TABLE_NUMBER==0){
                            alone.titlename = strText;
                            alone.storelatlng = str4.get(i);
                            Intent intent = new android.content.Intent(getApplicationContext(), alone_list.class);
                            startActivity(intent);
                            break;
                        }
                        else if(TABLE_NUMBER == 1){
                            family.titlename = strText;
                            family.storelatlng = str4.get(i);
                            Intent intent = new android.content.Intent(getApplicationContext(), family_list.class);
                            startActivity(intent);
                            break;
                        }
                        else if(TABLE_NUMBER == 2){
                            friend.titlename = strText;
                            friend.storelatlng = str4.get(i);
                            Intent intent = new android.content.Intent(getApplicationContext(), friend_list.class);
                            startActivity(intent);
                            break;
                        }
                        else if(TABLE_NUMBER == 3){
                            group.titlename = strText;
                            group.storelatlng = str4.get(i);
                            Intent intent = new android.content.Intent(getApplicationContext(), group_list.class);
                            startActivity(intent);
                            break;
                        }
                    }
                }
                finish();
            }
        });
    }
}
