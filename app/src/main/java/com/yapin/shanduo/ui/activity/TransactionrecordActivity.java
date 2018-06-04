package com.yapin.shanduo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.ui.adapter.TransactionrecordAdapter;

/**
 * Created by dell on 2018/6/3.
 */

public class TransactionrecordActivity extends BaseActivity {

    private ListView listView;
    //自定义BaseAdapter
    private TransactionrecordAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactionrecord);

//        adapter=new TransactionrecordAdapter(context,this);
//        listView.setAdapter(adapter);
    }
}
