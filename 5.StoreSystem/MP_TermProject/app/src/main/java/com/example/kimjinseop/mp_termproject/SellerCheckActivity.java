package com.example.kimjinseop.mp_termproject;

        import android.app.Activity;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;

//기업 - 판매판매물품관리
public class SellerCheckActivity extends Activity{
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
        setContentView(R.layout.activity_seller_db_check);

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

    public void process(){
        Cursor c = db.rawQuery("SELECT fkey, num FROM COMPANY_PRODUCT WHERE menu = '"+prevName+"';",null);
        c.moveToNext();
        int key = c.getInt(0);
        int newNum = c.getInt(1);
        db.execSQL("DELETE FROM COMPANY_PRODUCT WHERE menu = '"+prevName+"';");
        db.execSQL("UPDATE BUY_SELLER SET buy = 0 WHERE fkey = '"+key+"';");
        c = db.rawQuery("SELECT num FROM SELLER_LIST WHERE fkey = '"+key+"';",null);
        c.moveToNext();
        int number = c.getInt(0);
        db.execSQL("UPDATE SELLER_LIST SET num = "+(number+newNum)+" WHERE fkey = '"+key+"';");
        Toast.makeText(this, "처리가 완료되었습니다.", Toast.LENGTH_LONG).show();
        left_List();
        madeList();
    }

    public void process(View v){
        mode = 1;
        Toast.makeText(this, "처리된 주문을 클릭해 주세요.", Toast.LENGTH_LONG).show();
    }

    public void close(View v){
        finish();
    }

    public void addmenulist(){
        Cursor cursor = db.rawQuery("SELECT menu FROM COMPANY_PRODUCT;",null);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listMenu);
        lv.setAdapter(adapter);
        while(cursor.moveToNext()){
            String menu = cursor.getString(0);
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
                if (mode == 1) {
                    process();
                }
            }
        });
    }
}
