package com.example.kimjinseop.mp_termproject;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//포스
public class posActivity extends Activity {
    DBHelper helper;
    SQLiteDatabase db;
    TextView table1price, table2price, table3price, table4price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        helper = new DBHelper(this);
        try{
            db = helper.getWritableDatabase();
        }catch(SQLiteException ex){
            db = helper.getReadableDatabase();
        }
        helper.useDB(db);
        table1price =(TextView) findViewById(R.id.table1price);
        db.execSQL("insert into TablePrice values(" + 1 + "," + 0 + ");");
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Cursor cursor2 = db.rawQuery("SELECT * FROM TablePrice;",null);
                                cursor2.moveToFirst();
                                table1price.setText(cursor2.getString(1));
                                cursor2.close();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        t.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button: //table1
                Intent intent = new Intent(this, TableActivity.class);
                intent.putExtra("tableNum", "1" );
                this.startActivity(intent);
                break;
            case R.id.viewTransaction:
                Intent intent5 = new Intent(this, tsinfo.class);
                this.startActivity(intent5);
                break;
        }
    }



    // 없는 기술
    public void inputMenu(){
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.alert_dialog, null);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("New Menu");
        alert.setMessage("메뉴이름과 가격을 입력하세요");
        alert.setView(textEntryView);

        final EditText input1 = (EditText) textEntryView.findViewById(R.id.edtMenu);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.edtPrice);

        alert.setPositiveButton("메뉴추가", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String menuNames = input1.getText().toString();
                String menuPrice = input2.getText().toString();
                String regexStr = "^[0-9]*$";

                if(input2.getText().toString().trim().matches(regexStr)) { }
                else{
                    //write code for failure
                }

                if (input2.getText().toString().matches("-?\\d+(\\.\\d+)?"))  {
                    db.execSQL("INSERT INTO MENU_LIST_COMPANY VALUES(null, '" + menuNames + "', '" + menuPrice + "');");
                    Toast.makeText(getApplicationContext(), "추가됨", Toast.LENGTH_SHORT).show();
                    db.execSQL("INSERT INTO TABLE_DETAIL_1 VALUES(null,'" + menuNames + "','" + menuPrice + "', '"+ 0 + "');");
                } else {
                    Toast.makeText(getApplicationContext(), "잘못된 금액입니다. 다시 시도해 주십시오.", Toast.LENGTH_SHORT).show();
                    input2.setText("");
                }
            }
        });
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        alert.show();
    }
}

