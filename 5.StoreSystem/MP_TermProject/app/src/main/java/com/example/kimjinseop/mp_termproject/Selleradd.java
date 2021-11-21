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
public class Selleradd extends Activity{
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
        setContentView(R.layout.activity_selleradd);

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

    //발주요청모드
    public void add(){
        Cursor c = db.rawQuery("SELECT buy FROM BUY_SELLER WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 0){
            c = db.rawQuery("SELECT fkey FROM SELLER_LIST  WHERE menu = '"+prevName+"';",null);
            c.moveToNext();
            final int key = c.getInt(0);

            c = db.rawQuery("SELECT * FROM MENU_COMPANY  WHERE _id = '"+key+"';",null);
            c.moveToNext();
            final String menu = c.getString(1);

            //c는 기업에 전달할 데이터
            LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.alert_dialog2, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("판매 기업명 : (주)농심 \n발주 제품 : "+menu + "/가격 : " + c.getInt(2));
            alert.setMessage("제품의 발주 수량을 입력하세요");
            alert.setView(textEntryView);

            final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);
            alert.setPositiveButton("주문", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String menuNUM = input2.getText().toString();
                    String regexStr = "^[0-9]*$";
                    if(input2.getText().toString().trim().matches(regexStr)) { }
                    else{
                        //write code for failure
                    }
                    if (input2.getText().toString().matches("-?\\d+(\\.\\d+)?"))  {
                        //데이터베이스 추가 1.addCOMPANY_PRODUCT 2. updateBUY_SELLER
                        db.execSQL("INSERT INTO COMPANY_PRODUCT VALUES(null,'"+ menu +"','"+ key + "','"+menuNUM+"');");
                        //이 부분 이상하다!
                        db.execSQL("UPDATE BUY_SELLER SET buy = 1 WHERE menu ='"+ prevName + "';");
                        Toast.makeText(getApplicationContext(), "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show();
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



    //발주 취소
    public void delete(){
        Cursor c = db.rawQuery("SELECT buy FROM BUY_SELLER WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 1){
            c = db.rawQuery("SELECT fkey FROM SELLER_LIST  WHERE menu = '"+prevName+"';",null);
            c.moveToNext();
            final int key = c.getInt(0);

            synchronized (db){
                db.execSQL("DELETE FROM COMPANY_PRODUCT WHERE fkey = '"+key+"';");
                db.execSQL("UPDATE BUY_SELLER SET buy = 0 WHERE fkey = '"+key+"';");
            }
            madeList();
            left_List();
            Toast.makeText(this, "발주를 취소합니다.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "발주를 미 신청/처리 완료 재고입니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void add(View v){
        mode = 1;
        Toast.makeText(this, "상품을 선택하면 발주를 요청하게됩니다.", Toast.LENGTH_LONG).show();
    }

    public void delete(View v){
        mode = 2;
        Toast.makeText(this, "상품을 선택하면 발주를 취소를합니다.", Toast.LENGTH_LONG).show();
    }

    public void close(View v){
        finish();
    }


    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu FROM SELLER_LIST;",null);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            Cursor c = db.rawQuery("SELECT buy FROM BUY_SELLER WHERE menu = '"+menu+"';",null);
            c.moveToNext();
            if(c.getInt(0) == 0){
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
            cursor = db.rawQuery("SELECT * FROM COMPANY_PRODUCT;",null);
        }
        String menunames="";
        String prices="";
        menutext.setText(menunames);
        pricetext.setText(prices);
        while(cursor.moveToNext()){
            String menu = cursor.getString(1);
            String num = cursor.getString(3);
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
                    add();
                } else if(mode ==2) {
                    delete();
                }
            }
        });
    }
}
