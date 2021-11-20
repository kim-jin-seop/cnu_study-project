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

//판매업체 - 재고등록
public class SellerEnroll extends Activity{
    ListView lv;
    int mode = 0;
    DBHelper helper;
    ArrayAdapter<String> adapter;
    String prevName;
    SQLiteDatabase db;
    ArrayList<String> listMenu;
    TextView menutext, pricetext, nametext;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sellerenroll);
        helper = new DBHelper(this);
        try{
            db = helper.getWritableDatabase();

        }catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }
        helper.useDB(db);
        nametext = (TextView) findViewById(R.id.nametext);
        pricetext = (TextView) findViewById(R.id.pricetext);
        menutext = (TextView) findViewById(R.id.menutext);
        madeList();
        left_List();
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
                    ModifyMenu();
                } else if(mode ==2){
                    delete();
                }else if(mode == 3){
                    add();
                }

            }
        });
    }

    public void add(){
        Cursor c = db.rawQuery("SELECT use FROM SELECT_SELLER WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 0){
            c = db.rawQuery("SELECT _id,name,price FROM MENU_COMPANY  WHERE menu = '"+prevName+"';",null);
            c.moveToNext();
            final int key = c.getInt(0);
            final String name = c.getString(1);
            int price = c.getInt(2);
            LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.alert_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("판매 기업명 : (주)농심 \n등록 제품 : "+prevName + "/가격 : " + price);
            alert.setMessage("등록하실 제품의 이름과 가격을 입력하세요");
            alert.setView(textEntryView);

            final EditText input1 = (EditText) textEntryView.findViewById(R.id.edtMenu);
            input1.setText(prevName);
            final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);

            alert.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String menuNames = input1.getText().toString();
                    String menuPrice = input2.getText().toString();
                    String regexStr = "^[0-9]*$";
                    if(!hasTitle(menuNames)){
                        Toast.makeText(getApplicationContext(), "중복된 이름이 존재합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(input2.getText().toString().trim().matches(regexStr)) { }
                    else{
                        //write code for failure
                    }
                    if (input2.getText().toString().matches("-?\\d+(\\.\\d+)?"))  {
                        db.execSQL("INSERT INTO BUY_SELLER VALUES(null,'"+ menuNames + "','" + 0 + "','" + key +"');");
                        db.execSQL("INSERT INTO SELLER_LIST VALUES(null,'"+ key +"','" + menuNames + "','" + menuPrice + "','" + name +"','"+0+"','"+0+"');");
                        db.execSQL("UPDATE SELECT_SELLER SET use = 1 WHERE menu ='"+ prevName + "';");
                        db.execSQL("INSERT INTO MENU_LIST VALUES(null, '" + menuNames + "', '" + menuPrice + "','"+key+"');");
                        db.execSQL("INSERT INTO TABLE_DETAIL_1 VALUES(null,'" + menuNames + "','" + menuPrice + "', '"+ 0 + "','"+key+"');");
                        Toast.makeText(getApplicationContext(), "추가됨", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "잘못된 금액입니다. 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
                        input2.setText("");
                    }
                    madeList();
                    left_List();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                }
            });
            alert.show();

        }else{
            Toast.makeText(this, "이미 등록된 상태입니다.", Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void delete(){
        Cursor c = db.rawQuery("SELECT use FROM SELECT_SELLER WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 1){
            Cursor cursor = db.rawQuery("SELECT * FROM MENU_COMPANY WHERE menu = '"+prevName+"';",null);
            cursor.moveToNext();
            int key = cursor.getInt(0);
            synchronized (db){
                db.execSQL("DELETE FROM TABLE_DETAIL_1 WHERE fkey = '"+key+"';");
                db.execSQL("DELETE FROM MENU_LIST WHERE fkey = '"+key+"';");
                db.execSQL("DELETE FROM BUY_SELLER WHERE fkey = '"+key+"';");
                db.execSQL("DELETE FROM SELLER_LIST WHERE fkey = '"+key+"';");
                db.execSQL("UPDATE SELECT_SELLER SET use = 0 WHERE menu ='"+ prevName + "';");
            }
            madeList();
            left_List();
            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "입고하지 않는 재고입니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void modify(View v){
        mode = 1;
        Toast.makeText(this, "상품을 선택하면 수정됩니다.", Toast.LENGTH_LONG).show();
    }

    public void delete(View v){
        mode = 2;
        Toast.makeText(this, "상품을 선택하면 삭제됩니다.", Toast.LENGTH_LONG).show();
    }

    public void add(View v){
        mode = 3;
        Toast.makeText(this, "상품을 선택하면 추가됩니다.", Toast.LENGTH_LONG).show();
    }

    public void close(View v){
        finish();
    }

    public void ModifyMenu(){
        Cursor c = db.rawQuery("SELECT use FROM SELECT_SELLER WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        if(c.getInt(0) == 1){
            Cursor cursor = db.rawQuery("SELECT * FROM MENU_COMPANY WHERE menu = '"+prevName+"';",null);
            cursor.moveToNext();
            final int key = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            LayoutInflater factory = LayoutInflater.from(this);
            final View textEntryView = factory.inflate(R.layout.alert_dialog, null);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText input1 = (EditText) textEntryView.findViewById(R.id.edtMenu);
            final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);
            alert.setTitle("상품 수정\n 상품명 : "+name +"/가격 : "+price);
            alert.setMessage("제품의 이름과 가격을 입력하세요");
            alert.setView(textEntryView);
            alert.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String menuName = input1.getText().toString();
                    String menuPrice = input2.getText().toString();
                    String regexStr = "^[0-9]*$";
                    if(!hasTitle(menuName)){
                        Toast.makeText(getApplicationContext(), "중복된 이름이 존재합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(input2.getText().toString().trim().matches(regexStr)) { }
                    else{
                        //write code for failure
                    }
                    if (input2.getText().toString().matches("-?\\d+(\\.\\d+)?"))  {
                        Cursor cursor1 = db.rawQuery("SELECT menu FROM SELLER_LIST WHERE fkey = '"+key+"';",null);
                        cursor1.moveToNext();
                        String menu  = cursor1.getString(0);
                        synchronized (db){
                            db.execSQL("UPDATE TABLE_DETAIL_1 SET price = "+menuPrice+" WHERE menu ='"+ menu + "';");
                            db.execSQL("UPDATE TABLE_DETAIL_1 SET menu = '"+menuName+"' WHERE menu ='"+ menu + "';");
                            db.execSQL("UPDATE MENU_LIST SET price = "+menuPrice+" WHERE menu ='"+ menu + "';");
                            db.execSQL("UPDATE MENU_LIST SET menu = '"+menuName+"' WHERE menu ='"+ menu + "';");
                            db.execSQL("UPDATE SELLER_LIST SET price = "+menuPrice+" WHERE menu ='"+ menu + "';");
                            db.execSQL("UPDATE SELLER_LIST SET menu = '"+menuName+"' WHERE menu ='"+ menu + "';");
                        }
                        Toast.makeText(getApplicationContext(), "수정완료", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "잘못된 금액입니다. 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
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


            Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "입고하지 않는 재고입니다.", Toast.LENGTH_LONG).show();
            return;
        }




    }

    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu FROM MENU_COMPANY;",null);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            Cursor c = db.rawQuery("SELECT use FROM SELECT_SELLER WHERE menu = '"+menu+"';",null);
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

    public boolean hasTitle(String title){
        Cursor  cursor = db.rawQuery("SELECT * FROM SELLER_LIST;",null);
        while(cursor.moveToNext()){
            String menu = cursor.getString(2);
            if(menu.equals(title) && !prevName.equals(title))
                return false;
        }
        return true;
    }

    public void left_List(){
        Cursor cursor;
        synchronized (db){
            cursor = db.rawQuery("SELECT menu,price,name FROM SELLER_LIST;",null);
        }
        String menunames="";
        String prices="";
        String companys = "";
        menutext.setText(menunames);
        pricetext.setText(prices);
        nametext.setText(companys);

        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            String price = cursor.getString(1);
            String company = cursor.getString(2);
            menunames += menu + "\r\n";
            prices += price + "\r\n";
            companys += company + "\r\n";
        }
        menutext.setText(menunames);
        pricetext.setText(prices);
        nametext.setText(companys);
    }
}
