package com.example.kimjinseop.termp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

public class SQL extends SQLiteOpenHelper {
    // 안드로이드에서 SQLite 데이터 베이스를 쉽게 사용할 수 있도록 도와주는 클래스
//    private static final String DATABASE_NAME = "mapdiary1.db";
    private static final String DATABASE_NAME = "mapDiary.db";
    private static final int DATABASE_VERSION = 2;
    public LinkedList<String> str = new LinkedList<String>();
    public LinkedList<String> str1 = new LinkedList<String>();
    public LinkedList<LatLng> str2 = new LinkedList<LatLng>();
    public LinkedList<LatLng> search = new LinkedList<LatLng>();
    public String password="";
    public String on = "false";


    public SQL(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 최초에 데이터베이스가 없을경우, 데이터베이스 생성을 위해 호출됨
        // 테이블 생성하는 코드를 작성한다
        db.execSQL("CREATE TABLE MYDIARY1 (title TEXT PRIMARY KEY, context TEXT, lat DOUBLE, lon DOUBLE);");
        db.execSQL("CREATE TABLE MYDIARY2 (title TEXT PRIMARY KEY, context TEXT, lat DOUBLE, lon DOUBLE);");
        db.execSQL("CREATE TABLE MYDIARY3 (title TEXT PRIMARY KEY, context TEXT, lat DOUBLE, lon DOUBLE);");
        db.execSQL("CREATE TABLE MYDIARY4 (title TEXT PRIMARY KEY, context TEXT, lat DOUBLE, lon DOUBLE);");
        db.execSQL("CREATE TABLE MYDIARY5 (title TEXT PRIMARY KEY, context TEXT, lat DOUBLE, lon DOUBLE);");
        db.execSQL("CREATE TABLE MYDIARY6 (password TEXT PRIMARY KEY, isOn TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스의 버전이 바뀌었을 때 호출되는 콜백 메서드
        // 버전 바뀌었을 때 기존데이터베이스를 어떻게 변경할 것인지 작성한다
        // 각 버전의 변경 내용들을 버전마다 작성해야함
        String sql = "drop table mytable;"; // 테이블 드랍
        db.execSQL(sql);
        onCreate(db); // 다시 테이블 생성
    }

    public void insert(LatLng latlng, String title, String context, int tablenumber){
        SQLiteDatabase db = getWritableDatabase();
        double lat = latlng.latitude;
        double lon = latlng.longitude;
        title = "'"+title+"'";
        context = "'"+context+"'";
        switch(tablenumber){
            case 0:
                db.execSQL("INSERT INTO MYDIARY1(title,context,lat,lon) VALUES("+title+", "+context+", "+lat+"," +lon+");");
                break;
            case 1:
                db.execSQL("INSERT INTO MYDIARY2(title,context,lat,lon) VALUES("+title+", "+context+", "+lat+"," +lon+");");
                break;
            case 2:
                db.execSQL("INSERT INTO MYDIARY3(title,context,lat,lon) VALUES("+title+", "+context+", "+lat+"," +lon+");");
                break;
            case 3:
                db.execSQL("INSERT INTO MYDIARY4(title,context,lat,lon) VALUES("+title+", "+context+", "+lat+"," +lon+");");
                break;
            case 4:
                db.execSQL("INSERT INTO MYDIARY5(title,context,lat,lon) VALUES("+title+", "+context+", "+lat+"," +lon+");");
                break;
        }
        //db.execSQL("INSERT INTO MYDIARY(title,lat,lon) VALUES("+title+", "+lat+"," +lon+");");
        db.close();
    }

    public void setPassword(String password){
        SQLiteDatabase db = getWritableDatabase();
        password = "'"+password+"'";
        on = "true";
        String isOn = "'"+on+"'";
        db.execSQL("INSERT INTO MYDIARY6(password,isOn) VALUES("+password+","+isOn+");");
        db.close();
    }

    public void delete(LatLng latlng, String title, int tablenumber){
            SQLiteDatabase db = getWritableDatabase();
            // 입력한 항목과 일치하는 행 삭제
        switch(tablenumber){
            case 0:
                db.execSQL("DELETE FROM MYDIARY1 WHERE title='"+ title + "';");
                break;
            case 1:
                db.execSQL("DELETE FROM MYDIARY2 WHERE title='"+ title + "';");
                break;
            case 2:
                db.execSQL("DELETE FROM MYDIARY3 WHERE title='"+ title + "';");
                break;
            case 3:
                db.execSQL("DELETE FROM MYDIARY4 WHERE title='"+ title + "';");
                break;
            case 4:
                db.execSQL("DELETE FROM MYDIARY5 WHERE title='"+ title + "';");
                break;
        }
//            db.execSQL("DELETE FROM MYDIARY WHERE title='"+ title + "';");
            db.close();
    }

    public void deletePassword(String password){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MYDIARY6 WHERE password='" + password + "';");
        on = "false";
        db.close();
    }

    public void data(int tablenumber){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        switch(tablenumber){
            case 0:
                cursor = db.rawQuery("SELECT * FROM MYDIARY1;",null);
                break;
            case 1:
                cursor = db.rawQuery("SELECT * FROM MYDIARY2;",null);
                break;
            case 2:
                cursor = db.rawQuery("SELECT * FROM MYDIARY3;",null);
                break;
            case 3:
                cursor = db.rawQuery("SELECT * FROM MYDIARY4;",null);
                break;
            case 4:
                cursor = db.rawQuery("SELECT * FROM MYDIARY5;",null);
                break;
            case 5:
                cursor = db.rawQuery("SELECT * FROM MYDIARY6;",null);
                break;
        }
        if(tablenumber==5 && cursor.moveToNext()){
            password = cursor.getString(0);
            on = cursor.getString(1);
            return;
        }

        while(cursor.moveToNext()){
            str.add(cursor.getString(0));
            str1.add(cursor.getString(1));
            LatLng latlng = new LatLng(cursor.getDouble(2),cursor.getDouble(3));
            str2.add(latlng);

            str1.pollFirst();
        }
    }

    public void getContext(int tablenumber){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        switch(tablenumber){
            case 0:
                cursor = db.rawQuery("SELECT * FROM MYDIARY1;",null);
                break;
            case 1:
                cursor = db.rawQuery("SELECT * FROM MYDIARY2;",null);
                break;
            case 2:
                cursor = db.rawQuery("SELECT * FROM MYDIARY3;",null);
                break;
            case 3:
                cursor = db.rawQuery("SELECT * FROM MYDIARY4;",null);
                break;
            case 4:
                cursor = db.rawQuery("SELECT * FROM MYDIARY5;",null);
                break;
            case 5:
                cursor = db.rawQuery("SELECT * FROM MYDIARY6;",null);
                break;
        }
//        Cursor cursor = db.rawQuery("SELECT * FROM MYDIARY;",null);
        while(cursor.moveToNext()){
            str.add(cursor.getString(0));
            str1.add(cursor.getString(1));
            double d1 = cursor.getDouble(2);
            double d2 = cursor.getDouble(3);
        }
    }

    public void searchContext(int tablenumber){
        Cursor cursor = null;
        SQLiteDatabase db = getReadableDatabase();
        switch(tablenumber){
            case 0:
                cursor = db.rawQuery("SELECT * FROM MYDIARY1;",null);
                break;
            case 1:
                cursor = db.rawQuery("SELECT * FROM MYDIARY2;",null);
                break;
            case 2:
                cursor = db.rawQuery("SELECT * FROM MYDIARY3;",null);
                break;
            case 3:
                cursor = db.rawQuery("SELECT * FROM MYDIARY4;",null);
                break;
            case 4:
                cursor = db.rawQuery("SELECT * FROM MYDIARY5;",null);
                break;
            case 5:
                cursor = db.rawQuery("SELECT * FROM MYDIARY6;",null);
                break;
        }
        while(cursor.moveToNext()){
            str.add(cursor.getString(0));
            str1.add(cursor.getString(1));
            double d1 = cursor.getDouble(2);
            double d2 = cursor.getDouble(3);
            LatLng lat = new LatLng(d1,d2);
            search.add(lat);
        }
    }
}
