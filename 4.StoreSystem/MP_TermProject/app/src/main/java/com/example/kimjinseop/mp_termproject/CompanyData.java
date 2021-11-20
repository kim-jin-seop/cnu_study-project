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
public class CompanyData extends Activity{
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
        setContentView(R.layout.activity_showcompanydata);
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

    public void madeList(){
        lv = (ListView) findViewById(R.id.menuList);
        listMenu = new ArrayList<String>();
        addmenulist();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                prevName = listMenu.get(position);
                if (mode == 1) {
                    ModifyMenu();
                } else if(mode == 2){
                    delete();
                }

            }
        });
    }
    public void delete(){
        synchronized (db){
            Cursor c = db.rawQuery("SELECT * FROM MENU_COMPANY WHERE menu = '"+prevName+"';",null);
            c.moveToNext();
            int key = c.getInt(0);
            db.execSQL("DELETE FROM BUY_SELLER WHERE fkey = '"+key+"';");
            db.execSQL("DELETE FROM SELLER_LIST WHERE fkey = '"+key+"';");
            db.execSQL("DELETE FROM TABLE_DETAIL_1 WHERE fkey = '"+key+"';");
            db.execSQL("DELETE FROM MENU_LIST WHERE fkey = '"+key+"';");
            db.execSQL("DELETE FROM SELECT_SELLER WHERE menu = '"+prevName+"';");
            db.execSQL("DELETE FROM MENU_COMPANY WHERE menu = '"+prevName+"';");
        }
        madeList();
        left_List();
        Toast.makeText(this, "삭제완료", Toast.LENGTH_LONG).show();
    }

    public void modify(View v){
        mode = 1;
        Toast.makeText(this, "상품을 선택하면 수정됩니다.", Toast.LENGTH_LONG).show();
    }

    public void delete(View v){
        mode = 2;
        Toast.makeText(this, "상품을 선택하면 삭제됩니다.", Toast.LENGTH_LONG).show();
    }

    public void close(View v){
        finish();
    }

    public void ModifyMenu(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("제품 수정");
        alert.setMessage("제품의 이름과 가격을 입력하세요");
        alert.setView(textEntryView);

        final EditText input1 = (EditText) textEntryView.findViewById(R.id.edtMenu);
        input1.setText(prevName);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);

        alert.setPositiveButton("수정", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String CompanyName = "농심(제과)";
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
                    synchronized (db){
                        db.execSQL("UPDATE SELECT_SELLER SET menu = '"+menuName+"' WHERE menu ='"+ prevName + "';");
                        db.execSQL("UPDATE MENU_COMPANY SET price = "+menuPrice+" WHERE menu ='"+ prevName + "';");
                        db.execSQL("UPDATE MENU_COMPANY SET menu = '"+menuName+"' WHERE menu ='"+ prevName + "';");
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
    }

    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu FROM MENU_COMPANY;",null);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            listMenu.add(menu);
        }
        cursor.close();
    }

    public boolean hasTitle(String title){
        Cursor  cursor = db.rawQuery("SELECT * FROM MENU_COMPANY;",null);
        while(cursor.moveToNext()){
            String menu = cursor.getString(1);
            if(menu.equals(title) && !prevName.equals(title))
                return false;
        }
        return true;
    }
    public void left_List(){
        Cursor cursor;
        synchronized (db){
            cursor = db.rawQuery("SELECT * FROM MENU_COMPANY;",null);
        }
        String menunames="";
        String prices="";
        menutext.setText(menunames);
        pricetext.setText(prices);
        while(cursor.moveToNext()){
            String menu = cursor.getString(1);
            String price = cursor.getString(2);
            menunames += menu + "\r\n";
            prices += price + "\r\n";
        }
        menutext.setText(menunames);
        pricetext.setText(prices);
    }
}
