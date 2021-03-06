package es.source.code.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import es.source.code.model.User;

public class MainScreen extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private List<Map<String,Object>> dataList;
    private SimpleAdapter adapter;
    User user = new User();
    List<Integer> icon = Arrays.asList(R.drawable.ic_dish,R.drawable.ic_order,R.drawable.ic_login,R.drawable.ic_help);
    List<String> iconName = Arrays.asList("点菜","查看订单","登录/注册","系统帮助");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        SharedPreferences sharedPreferences = getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        int flag = sharedPreferences.getInt("loginState",-1);
        Intent intent = getIntent();
        try {
            String data = intent.getStringExtra("extra_data");
            if (flag != 1) {
                icon = Arrays.asList(R.drawable.ic_login, R.drawable.ic_help);
                iconName = Arrays.asList("登录/注册", "系统帮助");
            }
            if (flag == 1) {
                user = (User) getIntent().getSerializableExtra("user_info");
                if (data.equals("RegisterSuccess")) {
                    Toast.makeText(this, "欢迎您成为SCOS新用户！", Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        gridView = findViewById(R.id.gridView);
        dataList = new ArrayList<>();
        for (int i = 0; i < icon.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("ItemImage", icon.get(i));
            map.put("ItemText", iconName.get(i));
            dataList.add(map);
        }
        String[] from = {"ItemImage", "ItemText"};
        int[] to = {R.id.image, R.id.text};
        adapter = new SimpleAdapter(this, dataList, R.layout.item, from, to);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        if(dataList.size()==4) {
            if (i == 0) {
                Intent intent = new Intent(this, FoodView.class);
                intent.putExtra("user_info", user);
                startActivity(intent);
            }
            if (i == 1) {
                String data = "Yes";
                Intent intent = new Intent(this, FoodOrderView.class);
                intent.putExtra("user_info", user);
                intent.putExtra("YesOrNo", data);
                startActivity(intent);
            }
            if (i == 2) {
                Intent intent = new Intent(this, LoginOrRegister.class);
                startActivity(intent);
            }
            if (i == 3) {
                Intent intent = new Intent(this, SCOSHelper.class);
                startActivity(intent);
            }
        }
        if(dataList.size()==2) {
            if (i == 0) {
                Intent intent = new Intent(this, LoginOrRegister.class);
                startActivity(intent);
            }
            if (i == 1) {
                Intent intent = new Intent(this, SCOSHelper.class);
                startActivity(intent);
            }

        }
    }
}
