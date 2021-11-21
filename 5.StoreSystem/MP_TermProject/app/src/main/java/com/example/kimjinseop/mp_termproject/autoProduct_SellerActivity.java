package com.example.kimjinseop.mp_termproject;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//기업 - 판매판매물품관리
public class autoProduct_SellerActivity extends Activity{
    ListView lv;
    int mode = 0;
    DBHelper helper;
    ArrayAdapter<String> adapter;
    String prevName;
    SQLiteDatabase db;
    ArrayList<String> listMenu;
    TextView menutext, pricetext;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autobespeak);
        helper = new DBHelper(this);
        try{
            db = helper.getWritableDatabase();

        }catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }
        helper.useDB(db);
        pricetext = (TextView) findViewById(R.id.pricetext);
        menutext = (TextView) findViewById(R.id.menutext);
        madeList();
        left_List();
    }

    //자동발주 설정 미구현
    public void option(){
        Cursor c = db.rawQuery("SELECT auto,menu,price FROM SELLER_LIST WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 0){
            String menu = c.getString(1);
            LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.alert_dialog2, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("자동발주 설정\n제품 : "+menu + "/가격 : " + c.getInt(2));
            alert.setMessage("제품의 하한선을 입력해 주세요.(자동발주량은 하한선의 절반입니다) ");
            alert.setView(textEntryView);

            final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);
            alert.setPositiveButton("설정", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    int menuNUM = Integer.parseInt(input2.getText().toString());
                    String regexStr = "^[0-9]*$";
                    if(input2.getText().toString().trim().matches(regexStr)) { }
                    else{
                    }
                    if (input2.getText().toString().matches("-?\\d+(\\.\\d+)?"))  {
                        db.execSQL("UPDATE SELLER_LIST SET auto = "+menuNUM+" WHERE menu ='"+ prevName + "';");
                        Toast.makeText(getApplicationContext(), "설정이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "잘못된 숫자입니다. 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
                        input2.setText("");
                    }
                    madeList();
                    left_List();
                }
            });
            alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            alert.show();
        }else{
            Toast.makeText(this, "이미 신청한 발주 입니다.", Toast.LENGTH_LONG).show();
        }
    }



    //자동발주요청 취소 미구현
    public void cancel(){
        Cursor c = db.rawQuery("SELECT auto,fkey FROM SELLER_LIST WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) != 0){
            int key = c.getInt(1);
            synchronized (db){
                db.execSQL("UPDATE SELLER_LIST SET auto = 0 WHERE fkey = '"+key+"';");
            }
            madeList();
            left_List();
            Toast.makeText(this, "자동 발주를 취소합니다.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "자동 발주 설정이 되어있지 않습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void option(View v){
        mode = 1;
        Toast.makeText(this, "상품을 선택하면 자동발주를 설정합니다.", Toast.LENGTH_LONG).show();
    }

    public void cancel(View v){
        mode = 2;
        Toast.makeText(this, "상품을 선택하면 자동발주를 취소를합니다.", Toast.LENGTH_LONG).show();
    }

    public void close(View v){
        finish();
    }


    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu,auto FROM SELLER_LIST;",null);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            int auto = cursor.getInt(1);
            if(auto == 0){
                menu += "(x)";
            }else{
                menu += "(o)";
            }
            listMenu.add(menu);
        }
        cursor.close();
    }

    public void left_List(){
        Cursor cursor;
        synchronized (db){
            cursor = db.rawQuery("SELECT menu, auto FROM SELLER_LIST WHERE auto > 0;",null);
        }
        String menunames="";
        String prices="";
        menutext.setText(menunames);
        pricetext.setText(prices);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            String num = cursor.getString(1);
            menunames += menu + "\r\n";
            prices += num + "\r\n";
        }
        menutext.setText(menunames);
        pricetext.setText(prices);
    }

    public void madeList(){
        lv = (ListView) findViewById(R.id.menuList);
        listMenu = new ArrayList<String>();
        addmenulist();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prevName = listMenu.get(position);
                prevName = prevName.substring(0,prevName.length()-3);
                if (mode == 1) {
                    option();
                } else if(mode ==2) {
                    cancel();
                }
            }
        });
    }
}
