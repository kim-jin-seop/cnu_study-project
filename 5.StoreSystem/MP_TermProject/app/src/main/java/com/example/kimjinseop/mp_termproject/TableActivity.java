package com.example.kimjinseop.mp_termproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableActivity extends AppCompatActivity {
    DBHelper helper;
    SQLiteDatabase db;
    int sumprice=0;
    ListView lv;
    ArrayList<String> listMenu;
    ArrayAdapter<String> adapter;
    String tableNum;
    TextView totalprice, menutext, pricetext, numbertext, modetext;
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String getTime = sdf.format(date);
    String tradeDate = getTime;
    int mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        Intent intent = getIntent();
        mode = 1;
        totalprice = (TextView) findViewById(R.id.totalprice);
        menutext = (TextView) findViewById(R.id.menutext);
        pricetext = (TextView) findViewById(R.id.pricetext);
        numbertext = (TextView) findViewById(R.id.nametext);
        modetext = (TextView) findViewById(R.id.modetext);
        tableNum = intent.getExtras().getString("tableNum"); //테이블의 넘버를 받는다.
        lv = (ListView) findViewById(R.id.menuList);
        listMenu = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);

        helper = new DBHelper(this);
        try {
            db = helper.getWritableDatabase();

        } catch (SQLiteException ex) {
            db = helper.getReadableDatabase();
        }
        helper.useDB(db);
        reneworderlist();
        addmenulist();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Cursor cursor;
                String menu = listMenu.get(position);
                if (mode == 1) {
                    int quantity = 0;
                    cursor = db.rawQuery("SELECT quantity FROM TABLE_DETAIL_" + tableNum + " WHERE menu='" + menu + "';", null);
                    while (cursor.moveToNext()) {
                        quantity = cursor.getInt(0) + 1;
                    }
                    cursor = db.rawQuery("SELECT num FROM SELLER_LIST WHERE menu='" + menu + "';", null);
                    cursor.moveToNext();
                    int num = cursor.getInt(0);
                    if(quantity <= num){
                        db.execSQL("UPDATE TABLE_DETAIL_" + tableNum.toString() + " SET quantity = " + quantity + " WHERE menu='" + menu + "'; ");
                    }else{
                        noStore();
                    }
                } else {
                    int quantity = 0;
                    cursor = db.rawQuery("SELECT quantity FROM TABLE_DETAIL_" + tableNum + " WHERE menu='" + menu + "';", null);
                    cursor.moveToFirst();
                    quantity = cursor.getInt(0);
                    if( quantity ==0){
                        notmenuorder();
                    }else{
                        quantity = quantity-1;
                        db.execSQL("UPDATE TABLE_DETAIL_" + tableNum.toString() + " SET quantity = " + quantity + " WHERE menu='" + menu + "'; ");
                    }
                }
                reneworderlist();
            }
        });
    }

    public void noStore(){
        Toast.makeText(this, "더 이상 재고가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
    }

    public void notmenuorder(){
        Toast.makeText(this, "주문하지 않은 메뉴입니다..", Toast.LENGTH_LONG).show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removemode:
                mode = 1;
                Toast.makeText(this, "메뉴를선택하면 추가됩니다.", Toast.LENGTH_LONG).show();
                modetext.setText("주문추가");
                break;
            case R.id.deletemode:
                mode = 2;
                Toast.makeText(this, "메뉴를선택하면 삭제됩니다.", Toast.LENGTH_LONG).show();
                modetext.setText("주문삭제");
                break;
            case R.id.addmode: // 현금결제
                payInCash();
                break;
            case R.id.button8: //카드결제
                payInCard();
                break;
        }
    }

    public void payInCash(){
        Cursor c =db.rawQuery("SELECT menu,quantity FROM TABLE_DETAIL_1 WHERE quantity > 0",null);
        while(c.moveToNext()){
            String menu = c.getString(0);
            Cursor c1 =db.rawQuery("SELECT num FROM SELLER_LIST WHERE menu ='"+menu+"'",null);
            c1.moveToNext();
            int num = c1.getInt(0);
            db.execSQL("UPDATE SELLER_LIST SET num = " + (num- +c.getInt(1))+" WHERE menu='" + menu + "';");

            //자동발주 부분
            Cursor c2 = db.rawQuery("SELECT auto,num,fkey FROM SELLER_LIST WHERE menu ='"+menu+"'",null);
            c2.moveToNext();
            int autoBound = c2.getInt(0);
            int nowNum = c2.getInt(1);
            int key = c2.getInt(2);
            c2 = db.rawQuery("SELECT menu FROM MENU_COMPANY  WHERE _id = '"+key+"';",null);
            c2.moveToNext();
            final String getMenu = c.getString(0);
            int orderNum;
            if(nowNum < autoBound){
                orderNum = autoBound*3/2 -nowNum;
                c2 = db.rawQuery("SELECT buy FROM BUY_SELLER  WHERE fkey ='"+ key + "';",null);
                c2.moveToNext();
                int use = c2.getInt(0);
                if(use == 0){
                    db.execSQL("INSERT INTO COMPANY_PRODUCT VALUES(null,'"+ getMenu +"','"+ key + "','"+orderNum+"');");
                    db.execSQL("UPDATE BUY_SELLER SET buy = 1 WHERE fkey ='"+ key + "';");
                    //자동발주 완료
                }
            }
        }

        Cursor cursor = db.rawQuery("SELECT total_price FROM TablePrice WHERE _id = "+tableNum+";",null);
        cursor.moveToFirst();
        int sum123 = cursor.getInt(0);
        if( sum123 > 0){
            int quantity = 0;
            db.execSQL("UPDATE TABLE_DETAIL_" + tableNum.toString() + " SET quantity = " + quantity + "; ");
            Toast.makeText(this, "현금결제가 완료되었습니다.", Toast.LENGTH_LONG).show();
            savePaymentCash();
            reneworderlist();
        }else{
            Toast.makeText(this, "주문한 메뉴가 없습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void payInCard(){
        Cursor c =db.rawQuery("SELECT menu,quantity FROM TABLE_DETAIL_1 WHERE quantity > 0",null);
        while(c.moveToNext()){
            String menu = c.getString(0);
            Cursor c1 =db.rawQuery("SELECT num FROM SELLER_LIST WHERE menu ='"+menu+"'",null);
            c1.moveToNext();
            int num = c1.getInt(0);
            db.execSQL("UPDATE SELLER_LIST SET num = " + (num- +c.getInt(1))+" WHERE menu='" + menu + "';");

            //자동발주 부분
            Cursor c2 = db.rawQuery("SELECT auto,num,fkey FROM SELLER_LIST WHERE menu ='"+menu+"'",null);
            c2.moveToNext();
            int autoBound = c2.getInt(0);
            int nowNum = c2.getInt(1);
            int key = c2.getInt(2);
            c2 = db.rawQuery("SELECT menu FROM MENU_COMPANY  WHERE _id = '"+key+"';",null);
            c2.moveToNext();
            final String getMenu = c.getString(0);
            int orderNum;
            if(nowNum < autoBound){
                orderNum = autoBound*3/2 -nowNum;
                c2 = db.rawQuery("SELECT buy FROM BUY_SELLER  WHERE fkey ='"+ key + "';",null);
                c2.moveToNext();
                int use = c2.getInt(0);
                if(use == 0){
                    db.execSQL("INSERT INTO COMPANY_PRODUCT VALUES(null,'"+ getMenu +"','"+ key + "','"+orderNum+"');");
                    db.execSQL("UPDATE BUY_SELLER SET buy = 1 WHERE fkey ='"+ key + "';");
                    //자동발주 완료
                }
            }
        }
        Cursor cursor = db.rawQuery("SELECT total_price FROM TablePrice WHERE _id = '"+tableNum+"';",null);
        cursor.moveToFirst();
        int sum123 = cursor.getInt(0);
        if( sum123 > 0){
            int quantity = 0;
            db.execSQL("UPDATE TABLE_DETAIL_" + tableNum.toString() + " SET quantity = " + quantity + "; ");
            Toast.makeText(this, sumprice+ "원이 카드로 결제완료되었습니다.", Toast.LENGTH_LONG).show();
            savePaymentCard();
            reneworderlist();
        }else{
            Toast.makeText(this, "주문한 메뉴가 없습니다.", Toast.LENGTH_LONG).show();
        }
    }

    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu FROM MENU_LIST;",null);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
            listMenu.add(menu);
        }
        cursor.close();
    }

    public void savePaymentCard(){
        String card = "카드";
        db.execSQL("INSERT INTO SELL_INFO VALUES(null, '" + tradeDate + "', '" + sumprice + "', '"+card+"' );");
    }

    public void savePaymentCash(){
        String cash = "현금";
        db.execSQL("INSERT INTO SELL_INFO VALUES(null, '" + tradeDate + "', '" + sumprice + "', '"+cash+"' );");
    }

    public void reneworderlist(){
        Cursor  cursor2 = db.rawQuery("SELECT * FROM TABLE_DETAIL_"+tableNum.toString()+" WHERE quantity > 0;",null);
        String menunames="";
        String prices="";
        String quantities="";
        int price1;
        int quantity1;
        menutext.setText(menunames);
        pricetext.setText(prices);
        numbertext.setText(quantities);
        sumprice=0;
        while(cursor2.moveToNext()){
            String menu = cursor2.getString(1);
            String price = cursor2.getString(2);
            String quantity = cursor2.getString(3);
            price1 = cursor2.getInt(2);
            quantity1 = cursor2.getInt(3);
            menunames += menu + "\r\n";
            prices += price + "\r\n";
            quantities += quantity + "\r\n";
            sumprice += price1 * quantity1;
        }
        menutext.setText(menunames);
        pricetext.setText(prices);
        numbertext.setText(quantities);
        db.execSQL("UPDATE TablePrice SET total_price = " + sumprice + " WHERE _id = '"+tableNum+"'; ");
        totalprice.setText( Integer.toString(sumprice)  );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_table, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
