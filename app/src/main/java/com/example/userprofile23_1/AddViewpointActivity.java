package com.example.userprofile23_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import Fragment.MainActivity;
import Utils.DBHelper;

public class AddViewpointActivity extends AppCompatActivity {
    DBHelper dbHelper;
    EditText et_viewpoint;
    Button btn_publish;
    String account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_viewpoint);

        dbHelper = new DBHelper(this);
        et_viewpoint = findViewById(R.id.et_viewpoint);
        btn_publish = findViewById(R.id.btn_publish);
        account = getIntent().getStringExtra("account");
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_viewpoint.getText().toString();
                if(content.length() < 10){
                    Toast.makeText(AddViewpointActivity.this,"您的观点应不少于10个字符",Toast.LENGTH_SHORT).show();
                    return;
                }
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("insert into information(account,viewpoint,time) " +
                        "values(?,?,?) "
                        , new String[]{account,content,new Date().toString()});
                Toast.makeText(AddViewpointActivity.this,"观点发表成功",Toast.LENGTH_SHORT).show();
                AddViewpointActivity.this.finish();
                db.close();
            }
        });

    }
}
