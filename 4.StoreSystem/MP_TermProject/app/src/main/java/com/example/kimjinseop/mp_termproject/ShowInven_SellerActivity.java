package com.example.kimjinseop.mp_termproject;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.TextView;

public class ShowInven_SellerActivity extends Activity {
    TextView menutext, pricetext, numtext, nametext;
    SQLiteDatabase db;
    DBHelper helper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller2);
        pricetext = (TextView) findViewById(R.id.price);
        menutext = (TextView) findViewById(R.id.date);
        numtext = (TextView) findViewById(R.id.paymentcheck);
        nametext = (TextView)findViewById(R.id.idnum);
        helper = new DBHelper(this);
        try{
            db = helper.getWritableDatabase();

        }catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }
        helper.useDB(db);
        update();
    }

    private void update(){
        Cursor c = db.rawQuery("SELECT menu,price,name,num FROM SELLER_LIST;",null);
        String menunames="| 제품이름 |\r\n-------------\r\n";
        String prices="| 물품 가격 |\r\n-------------\r\n";
        String nums = "| 수량 |\r\n-------------\r\n";
        String companys = "| 기업명  |\r\n------------- \r\n";
        menutext.setText(menunames);
        pricetext.setText(prices);
        numtext.setText(nums);
        nametext.setText(companys);

        while(c.moveToNext()){
            String menu = c.getString(0);
            String price = c.getString(1);
            String company = c.getString(2);
            String num = c.getString(3);
            menunames += menu + "\r\n";
            prices += price + "\r\n";
            nums += num + "\r\n";
            companys += company + "\r\n";
        }
        menutext.setText(menunames);
        pricetext.setText(prices);
        numtext.setText(nums);
        nametext.setText(companys);
    }

}
