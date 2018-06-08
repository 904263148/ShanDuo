package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.ReportPresenter;
import com.yapin.shanduo.ui.contract.ReportContract;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReportActivity extends RightSlidingActivity implements ReportContract.View{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.bt_report)
    Button btReport;

    private String[] report;

    private Context context;
    private Activity activity;

    private int checkPosition = -1;

    private ReportPresenter presenter;

    private String activityId = "";
    private String dynamicId = "";
    private String typeId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        presenter = new ReportPresenter();
        presenter.init(this);
    }

    public void initView() {

        context = ShanDuoPartyApplication.getContext();
        activity = this;

        typeId = getIntent().getStringExtra("typeId");
        String id = getIntent().getStringExtra("id");
        if(Constants.REPORT_ACT.equals(typeId)){
            activityId = id;
        }else {
            dynamicId = id;
        }

        report = getResources().getStringArray(R.array.report);

        final MyAdapter adapter = new MyAdapter();
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkPosition = position;
                adapter.notifyDataSetChanged();
            }
        });

        btReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getData(PrefJsonUtil.getProfile(context).getUserId() , activityId , dynamicId , typeId , report[checkPosition]);
            }
        });

    }

    @Override
    public void show(String success) {
        ToastUtil.showShortToast(context , success);
        onBackPressed();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {

    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context , msg);
    }

    @Override
    public void showFailed(String msg) {

    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return report.length;
        }

        @Override
        public Object getItem(int position) {
            return report[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.report_item, null);
                holder.tvReport = convertView.findViewById(R.id.tv_report);
                holder.ivReport = convertView.findViewById(R.id.iv_report);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvReport.setText(report[position]);

            if (position == checkPosition) {
                holder.ivReport.setBackgroundResource(R.drawable.icon_report_checked);
            } else {
                holder.ivReport.setBackgroundResource(R.drawable.icon_report_unchecked);
            }

            return convertView;
        }

        class ViewHolder {
            TextView tvReport;
            ImageView ivReport;
        }

    }

}
