package com.example.kimjinseop.termp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.util.LinkedList;

import static com.example.kimjinseop.termp.family.storelatlng;
import static com.example.kimjinseop.termp.family.titlename;
import static com.example.kimjinseop.termp.family.main;
import static com.example.kimjinseop.termp.SplashActivity.helper;


public class family_list extends AppCompatActivity {
    Button modify, delete;
    ImageView image;
    static Bitmap bt;
    TextView text,title;
    static boolean ismodify = false;
    public static Activity dActivity;
    LatLng tlatlng;
    public static String cont;

    TextView noLoad;

    protected void onCreate(Bundle savedInstanceState) {
        dActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_list);

        text = (TextView) findViewById(R.id.status);
        title = (TextView) findViewById(R.id.title);
        image = (ImageView) findViewById(R.id.ivImage);
        modify = (Button) findViewById(R.id.modify);
        delete = (Button) findViewById(R.id.delete);
        noLoad = (TextView) findViewById(R.id.noloadImage) ;
        tlatlng = storelatlng;
        title.setText(titlename);

        LinkedList<String> ImageTitle = new LinkedList<String>();
        LinkedList<String> context = new LinkedList<String>();
        helper.getContext(1);
        ImageTitle = helper.str;
        context = helper.str1;
        int length = context.size();
        for(int i =0 ; i < length; i++){
            cont = context.pollFirst();
            if(ImageTitle.pollFirst().equals(titlename)){
                //text.setText(context.get(i));
//                cont = context.pollFirst();
                text.setText(cont);
                break;
            }
        }
        int np = ImageTitle.size();
        for(int i = 0 ; i < np; i++){
            ImageTitle.pollFirst();
            context.pollFirst();
        }
        ImageTitle = null;
        context = null;

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        path += "/junior/family/"+titlename+".jpeg";
        bt = BitmapFactory.decodeFile(path);

        final File file = new File(path);

        if(bt != null){
            image.setImageBitmap(bt);
            noLoad.setText("");
        }

        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ismodify = true;
                Intent intent = new android.content.Intent(getApplicationContext(), add_family.class);
                startActivity(intent);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                family add = (family) main;
                add.dbdelete(tlatlng,titlename);
                file.delete();
                add.deletemark();
                titlename = "";

                finish();
            }
        });
    }
}
