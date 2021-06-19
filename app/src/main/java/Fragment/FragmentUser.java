package Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.userprofile23_1.EditProfileActivity;
import com.example.userprofile23_1.ImageUtil;
import com.example.userprofile23_1.MyViewpointActivity;
import com.example.userprofile23_1.R;

import LoginAndRegister.LRActivity;
import Utils.DBHelper;

public class FragmentUser extends Fragment {
    private DBHelper dbHelper;
    private String account;
    private TextView tvNickName,tvAccount,tvAge,tvGender,tvCity,tvHome,tvSchool,tvSign,tvBirthdayTime;
    private ImageView ivAvatar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user,container,false);
        return  v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        Button toEdit = view.findViewById(R.id.btn_toEdit);
        Button logout = view.findViewById(R.id.btn_logout);
        Button btn_viewpoint = view.findViewById(R.id.btn_viewpoint);
        account = ((MainActivity)getActivity()).account;
        btn_viewpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),MyViewpointActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        });

        toEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LRActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initView(getView());
    }

    void initView(View view){
        tvAccount = view.findViewById(R.id.tv_account_text);
        tvBirthdayTime = view.findViewById(R.id.tv_birth_time_text);
        tvCity = view.findViewById(R.id.tv_home_text);
        tvSchool = view.findViewById(R.id.tv_school_text);
        tvSign = view.findViewById(R.id.tv_sign_text);
        tvNickName = view.findViewById(R.id.tv_nick_name);
        tvGender = view.findViewById(R.id.tv_gender);
        ivAvatar = view.findViewById(R.id.iv_avatar);

        dbHelper = new DBHelper(getActivity());

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        account = ((MainActivity)getActivity()).account;//获得账号
        Cursor cursor = db.rawQuery("select * from User where name =?",new String[]{account});
        if(cursor.moveToNext()){
            tvAccount.setText(cursor.getString(1));
            tvNickName.setText(cursor.getString(4));
            tvGender.setText(cursor.getString(5));
            tvBirthdayTime.setText(cursor.getString(6));
            tvCity.setText(cursor.getString(7));
            tvSchool.setText(cursor.getString(8));
            tvSign.setText(cursor.getString(9));
            if(cursor.getString(10) == null)
                ivAvatar.setImageResource(R.drawable.ic_person_black_24dp);
            else
                ivAvatar.setImageBitmap(ImageUtil.base64ToImage(cursor.getString(10)));

        }
        dbHelper.close();
        cursor.close();

    }


}
