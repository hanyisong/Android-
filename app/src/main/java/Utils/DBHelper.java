package Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by han on 2021/6/10.
 */
public class DBHelper extends SQLiteOpenHelper {
    public String sql;
    public DBHelper(Context context){
        super(context,"foodSafety.db",null,2);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        sql = "create table User(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "name varchar(15) UNIQUE," +
                "password varchar(10)," +
                "mailbox varchar(20)," +
                "nick_name varchar(20)," +
                "sex varchar(5)," +
                "birth varchar(25)," +
                "city varchar(10)," +
                "school varchar(20)," +
                "sign varchar(100)," +
                "avatar varchar(100)" +
                ")";
        db.execSQL(sql);
        sql = "create table information(" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "account varchar(15)," + //账号
                "viewpoint varchar(100)," +
                "time varchar(20)" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
