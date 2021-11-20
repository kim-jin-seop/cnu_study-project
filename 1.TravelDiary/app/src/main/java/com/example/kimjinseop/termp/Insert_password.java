package com.example.kimjinseop.termp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import static com.example.kimjinseop.termp.SplashActivity.AActivity;
import static com.example.kimjinseop.termp.SplashActivity.helper;
import static com.example.kimjinseop.termp.password.modify;


public class Insert_password extends AppCompatActivity implements View.OnClickListener{
    Button button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,button_clear,button_erase;
    EditText[] passcode = new EditText[4];
    String[] real = new String[4];
    TextView context;
    int count = 0;
    String password;
    Activity log;
    static Activity pas;
    boolean tmodify = modify;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode);
        modify = false;
        log = AActivity;
        if(log != null) {
            log.finish();
        }

        helper.data(5);
        password = helper.password;

        button0 = (Button) findViewById(R.id.button0);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button_clear = (Button) findViewById(R.id.button_clear);
        button_erase = (Button) findViewById(R.id.button_erase);

        passcode[0] = (EditText) findViewById(R.id.passcode_1);
        passcode[1] = (EditText) findViewById(R.id.passcode_2);
        passcode[2] = (EditText) findViewById(R.id.passcode_3);
        passcode[3] = (EditText) findViewById(R.id.passcode_4);

        context = (TextView) findViewById(R.id.tv_message);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button_clear.setOnClickListener(this);
        button_erase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Button temp = (Button) v;
        if(temp.getId() == R.id.button0){
            passcode[count].setText("   *");
            real[count] = "0";
            count++;
        }
        else if(temp.getId() == R.id.button1){
            passcode[count].setText("   *");
            real[count] = "1";
            count++;
        }
        else if(temp.getId() == R.id.button2){
            passcode[count].setText("   *");
            real[count] = "2";
            count++;
        }
        else if(temp.getId() == R.id.button3){
            passcode[count].setText("   *");
            real[count] = "3";
            count++;
        }
        else if(temp.getId() == R.id.button4){
            passcode[count].setText("   *");
            real[count] = "4";
            count++;
        }
        else if(temp.getId() == R.id.button5){
            passcode[count].setText("   *");
            real[count] = "5";
            count++;
        }
        else if(temp.getId() == R.id.button6){
            passcode[count].setText("   *");
            real[count] = "6";
            count++;
        }
        else if(temp.getId() == R.id.button7){
            passcode[count].setText("   *");
            real[count] = "7";
            count++;
        }
        else if(temp.getId() == R.id.button8){
            passcode[count].setText("   *");
            real[count] = "8";
            count++;
        }
        else if(temp.getId() == R.id.button9){
            passcode[count].setText("   *");
            real[count] = "9";
            count++;
        }
        else if(temp.getId() == R.id.button_clear){
            for(int i = 0; i < count; i++){
                passcode[i].setText("");
                real[i]="";
            }
            count = 0;
        }
        else if(temp.getId() == R.id.button_erase){
            if(count > 0) {
                passcode[--count].setText("");
                real[count] = "";
            }
        }

        if(count == 4){
            if(tmodify){
                tmodify();
            }
            else {
                password();
            }
            return;
        }
    }
    public void password(){
        String PASSWORD = "";
        for(int i = 0; i < count; i++){
//            PASSWORD += passcode[i].getText().toString();
            PASSWORD += real[i];
        }
        if (password.equals(PASSWORD)) {
            count = 0 ;
            Intent intent = new android.content.Intent(getApplicationContext(), select.class);
            startActivity(intent);
            pas = Insert_password.this;
            overridePendingTransition(R.anim.fade, R.anim.hold);
        } else {
//            Toast.makeText(Insert_password.this, "틀렸습니당~", Toast.LENGTH_SHORT).show();
            Toast.makeText(Insert_password.this, password, Toast.LENGTH_SHORT).show();
            for(int i = 0 ; i < count; i++) {
                passcode[i].setText("");
                real[i] = "";
            }
            count = 0;
        }
    }

    public void tmodify(){
        String PASSWORD = "";
        for(int i = 0; i < count; i++){
//            PASSWORD += passcode[i].getText().toString();
            PASSWORD += real[i];
        }

        count = 0;
        helper.deletePassword(null);
        helper.deletePassword(password);
        helper.setPassword(PASSWORD);
        tmodify = false;
        finish();
    }
}




































//    String password;
//    Button button;
//    EditText pass;
//    Activity log;
//    static Activity pas;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.insert_password);
//        log = AActivity;
//        if(log != null) {
//            log.finish();
//        }
//
//        button = (Button) findViewById(R.id.button);
//        pass = (EditText) findViewById(R.id.textView);
//
//        helper.data(5);
//        password = helper.password;
//
//        if(tmodify==true) {
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    if(pass.getText().toString().length() < 4){
//                        Toast.makeText(Insert_password.this, "4글자 이상", Toast.LENGTH_SHORT).show();
//                        pass.setText("");
//                    }
//                    else{
//                        helper.deletePassword(password);
//                        helper.setPassword(pass.getText().toString());
//                        tmodify = false;
//                        finish();
//                    }
//                }
//            });
//        }
//        else {
//            button.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View view) {
//                    String insertpass = pass.getText().toString();
//
//                    if (password.equals(insertpass)) {
//                        Intent intent = new android.content.Intent(getApplicationContext(), select.class);
//                        startActivity(intent);
//                        pas = Insert_password.this;
//                        overridePendingTransition(R.anim.fade, R.anim.hold);
//                    } else {
//                        Toast.makeText(Insert_password.this, "틀렸어"+password+"", Toast.LENGTH_SHORT).show();
//                        pass.setText("");
//                    }
//                }
//            });
//        }
//    }
//}
