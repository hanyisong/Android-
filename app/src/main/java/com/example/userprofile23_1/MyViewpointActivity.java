package com.example.userprofile23_1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Utils.DBHelper;

public class MyViewpointActivity extends AppCompatActivity {
    String account;
    ListView mListview;
    List<String> list,idList;
    ArrayAdapter<String> arrayAdapter;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_viewpoint);

        dbHelper = new DBHelper(this);
        account = getIntent().getStringExtra("account");
        list = new ArrayList<>();
        idList = new ArrayList<>();
        mListview = findViewById(R.id.my_lv);
        shoListView();

        mListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("delete from information where id=?",new String[]{idList.get(position)});
                Toast.makeText(MyViewpointActivity.this, "删除成功！",Toast.LENGTH_SHORT).show();
                mListview.clearTextFilter();
                shoListView();
                db.close();
                return true;
            }
        });

    }

    void shoListView(){
        list.clear();
        idList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information where account=?",new String[]{account});
        while(cursor.moveToNext()){
            list.add(cursor.getString(2));
            idList.add(cursor.getString(0));
        }
        cursor.close();
        db.close();
        arrayAdapter= new ArrayAdapter<>(this,R.layout.list_item,list);
        mListview.setAdapter(arrayAdapter);
    }

}
