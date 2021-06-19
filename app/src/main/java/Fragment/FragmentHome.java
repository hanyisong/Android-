package Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.userprofile23_1.AddViewpointActivity;
import com.example.userprofile23_1.ImageUtil;
import com.example.userprofile23_1.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.DBHelper;


public class FragmentHome extends Fragment {

    int[] images={
      R.drawable.image_1,
      R.drawable.image_2,
      R.drawable.image_3,
      R.drawable.image_4,
      R.drawable.image_5
    };
    CarouselView carouselView;
    private ListView listView;
    private List<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private Button btnAdd;
    private String account;
    DBHelper dbHelper;

    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> mList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);

        account = ((MainActivity)getActivity()).account;//获得当前账号

        //播放轮播图
        carouselView = v.findViewById(R.id.carouselView);
        //设置轮播图数量
        carouselView.setPageCount(images.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(images[position]);
            }
        });

        //切换到发表观点Activity
        btnAdd = v.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddViewpointActivity.class);
                intent.putExtra("account",account);
                startActivity(intent);
            }
        });

        showListView(v);

        return  v;
    }

    @Override
    public void onResume() {
        super.onResume();
        showListView(getView());
    }

    void showListView(View v){
        listView = v.findViewById(R.id.lv);
        mList = new ArrayList<>();

        dbHelper = new DBHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information",new String[]{});

        while(cursor.moveToNext()){
            Map<String,Object> map = new HashMap<>();
            String account = cursor.getString(1);
            map.put("account",account);
            map.put("viewpoint",cursor.getString(2));

            if(account == null) continue;
            Cursor cursor1 = db.rawQuery("select avatar,nick_name from User where name=?",new String[]{account});

            cursor1.moveToNext();
            String nick_name = cursor1.getString(1);
            String imagBase64 = cursor1.getString(0);
            if(imagBase64 == null)
                map.put("img",R.drawable.ic_person_black_24dp);
            else
                map.put("img", ImageUtil.base64ToImage(imagBase64));

            if(nick_name == null) nick_name = "未设置昵称";
            map.put("nick_name",nick_name);

            mList.add(map);
            cursor1.close();
        }

        db.close();
        cursor.close();

        //将获得的元素加入SimpleAdapter
        simpleAdapter = new SimpleAdapter(getActivity(),mList,
                R.layout.sim_list_item,
                new String[]{"img","nick_name","viewpoint"},
                new int[]{R.id.img_avatar,R.id.lv_tv_name,R.id.tv_viewpoint});

        //实现SimpleAdpter加载bitmap
        simpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                // TODO Auto-generated method stub
                if(view instanceof ImageView && data instanceof Bitmap){
                    ImageView i = (ImageView)view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });

        listView.setAdapter(simpleAdapter);
    }

}
