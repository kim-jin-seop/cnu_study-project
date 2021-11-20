package com.example.kimjinseop.mp_termproject;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class tsinfo extends AppCompatActivity {
    DBHelper helper;
    SQLiteDatabase db;
    TextView idnum, tableNum, price, paymentcheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsinfo);
        idnum = (TextView) findViewById(R.id.idnum);
        tableNum = (TextView) findViewById(R.id.date);
        price = (TextView) findViewById(R.id.price);
        paymentcheck = (TextView) findViewById(R.id.paymentcheck);

        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();

        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();
        }


        Cursor cursor = db.rawQuery("SELECT * FROM SELL_INFO;",null);

        String idnumber ="거래 번호 \r\n";
        String prices="거래 금액 \r\n";
        String Date="거래 날짜\r\n";
        String payment="현금/카드 \r\n";
        while(cursor.moveToNext()){
            idnumber += cursor.getString(0) + "\r\n";
            Date += cursor.getString(1) + "\r\n";
            prices+= cursor.getString(2)+ "\r\n";
            payment +=cursor.getString(3)+ "\r\n";
        }
        idnum.setText(idnumber);
        tableNum.setText(Date);
        price.setText(prices);
        paymentcheck.setText(payment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tsinfo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
