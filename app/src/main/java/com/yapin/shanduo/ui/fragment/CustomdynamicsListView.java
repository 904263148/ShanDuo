package com.yapin.shanduo.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.activity.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2018/5/3.
 */

public class CustomdynamicsListView extends BaseActivity {

    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customdynamics);

        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();

        String[] from={"img"};
        int[] to={R.id.img};

        adapter=new SimpleAdapter(this, dataList, R.layout.image_cust, from , to);

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                AlertDialog.Builder builder= new AlertDialog.Builder(CustomdynamicsListView.this);
                builder.setTitle("提示").setMessage(dataList.get(arg2).get("text").toString()).create().show();
            }
        });
    }

    void initData() {
        //图标
        int icno[] = { R.drawable.timg, R.drawable.timg, R.drawable.timg};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("img", icno[i]);
            dataList.add(map);
        }
    }
}
