package LoginAndRegister;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userprofile23_1.R;
import com.example.userprofile23_1.UserInfo;

import Fragment.MainActivity;
import Utils.DBHelper;
import Utils.StringUtil;

public class LRActivity extends Activity {
    private DBHelper dbHelper;
    private EditText r_et_name;
    private EditText r_et_password;
    private EditText r_et_mailbox;
    private Button r_btn_login;
    private Button r_btn_register;
    private EditText l_et_name;
    private EditText l_et_password;
    private Button l_btn_login;
    private Button l_btn_register;
    UserInfo userInfo;
//    private boolean LoginFlag = false;
//    private boolean RegisterFlag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //创建数据库
        dbHelper = new DBHelper(this);
        //登录界面初始化
        login_init();
    }

    protected void login_init(){
        //显示登录界面
        setContentView(R.layout.activity_login);
        //获得组件
        l_et_name = findViewById(R.id.et_name);
        l_et_password = findViewById(R.id.et_password);
        l_btn_login = findViewById(R.id.btn_login);
        l_btn_register = findViewById(R.id.btn_register);
        //切换到注册界面
        l_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register_init();
            }
        });
        //进行登录
        l_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //输入框账号密码
                String name = l_et_name.getText().toString();
                String psd = l_et_password.getText().toString();
                //获得db操作对象与游标
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select password from User where name=?", new String[]{name});
                //判断是否存在该用户
                if (cursor.moveToNext()) {
                    if (!psd.equals(cursor.getString(0))) {
                        Toast.makeText(LRActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                    } else {
//                        userInfo = new UserInfo();
                        cursor = db.rawQuery("select * from User where name =?",new String[]{name});
//                        userInfo.setName(cursor.getString(4));
//                        userInfo.setSex(cursor.getString(5));
//                        userInfo.setBirthday(cursor.getString(6));
//                        userInfo.setCity(cursor.getString(7));
//                        userInfo.setSchool(cursor.getString(8));
//                        userInfo.setSign(cursor.getString(9));
                        Intent intent = new Intent(LRActivity.this, MainActivity.class);
                        intent.putExtra("account",name);
                        startActivity(intent);
                        LRActivity.this.finish();
                        Toast.makeText(LRActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LRActivity.this, "不存在该用户！", Toast.LENGTH_SHORT).show();
                }
                db.close();
                cursor.close();
            }
        });
    }

    protected void register_init(){
        //显示注册界面
        setContentView(R.layout.activity_register);
        //获得组件
        r_et_name = findViewById(R.id.et_name);
        r_et_password = findViewById(R.id.et_password);
        r_et_mailbox = findViewById(R.id.et_mailbox);
        r_btn_login = findViewById(R.id.btn_login);
        r_btn_register = findViewById(R.id.btn_register);
        //切换到登录界面
        r_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_init();
            }
        });
        //注册
        r_btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db;
                //获得输入框信息
                String name = r_et_name.getText().toString();
                String psd = r_et_password.getText().toString();
                String mailbox = r_et_mailbox.getText().toString();
                //校验账号格式
                if(name.length() < 4 || name.length() > 6){
                    Toast.makeText(LRActivity.this,"账号位数错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtil.StrCheck(name)){
                    Toast.makeText(LRActivity.this,"账号格式错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //校验密码格式
                if(psd.length() < 6 || psd.length() > 10){
                    Toast.makeText(LRActivity.this,"密码位数错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!StringUtil.StrCheck(psd)){
                    Toast.makeText(LRActivity.this,"密码格式错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //校验邮箱 -待更新*************************************************************************************
                if(mailbox.length() < 6){
                    Toast.makeText(LRActivity.this,"邮箱错误！",Toast.LENGTH_SHORT).show();
                    return;
                }
                //获得db操作对象
                db = dbHelper.getReadableDatabase();
                Cursor cursor = db.rawQuery("select * from User where name=?",new String[]{name});
                if(cursor.moveToNext()){
                    Toast.makeText(LRActivity.this,"账号已存在，请修改！",Toast.LENGTH_SHORT).show();
                    return;
                }
                db.close();
                cursor.close();
                //获得db操作对象
                db = dbHelper.getWritableDatabase();
                //插入注册用户信息
                db.execSQL("insert into User(name,password,mailbox) values(?,?,?)",new String[]{name,psd,mailbox});
                Toast.makeText(LRActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                db.close();
            }
        });

    }

}
