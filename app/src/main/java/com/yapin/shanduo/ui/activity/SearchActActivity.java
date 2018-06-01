package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.widget.LoadMoreRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActActivity extends BaseActivity implements  View.OnKeyListener{


    @BindView(R.id.inputSearch)
    EditText inputSearch;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title)
    LinearLayout title;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.noResult)
    TextView noResult;
    @BindView(R.id.recycler_view)
    LoadMoreRecyclerView recyclerView;

    private Context context;
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_act);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        TextView tvCancel = findViewById(R.id.cancel);
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_UP){   // 忽略其它事件
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER:
                String key = inputSearch.getText().toString();
                if (key.equals("")) return true;
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}
