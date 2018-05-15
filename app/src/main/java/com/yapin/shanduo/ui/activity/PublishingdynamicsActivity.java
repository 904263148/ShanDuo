package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.PublishingdynamicsPrsesnter;
import com.yapin.shanduo.ui.contract.PublishingdynamicsContract;
import com.yapin.shanduo.utils.PrefUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/5/12.
 */

public class PublishingdynamicsActivity extends BaseActivity implements PublishingdynamicsContract.View , View.OnClickListener{

    private PublishingdynamicsPrsesnter presenter;
    private Context context;
    private Activity activity;

    @BindView(R.id.ll_textview_camera)      //相机
    LinearLayout ll_textview_camera;
    @BindView(R.id.tv_pd_cancel)    //取消
    TextView tv_pd_cancel;
    @BindView(R.id.tv_pd_text)  //动态内容
    EditText tv_pd_text;
    @BindView(R.id.ib_pd_Location)  //定位
    ImageButton ib_pd_Location;
    @BindView(R.id.ib_pd_Expression)    //表情
    ImageButton ib_pd_Expression;
    @BindView(R.id.tv_pd_Publish)   //发表
    TextView tv_pd_Publish;
    int i=0;



    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publishingdynamics);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
        presenter = new PublishingdynamicsPrsesnter();
        presenter.init(context,this);


    }


    @OnClick({R.id.ll_textview_camera,R.id.tv_pd_Publish,R.id.tv_pd_cancel})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_textview_camera:
                showPopwindow();

                break;
            case R.id.tv_pd_cancel:
                finish();
                break;

            case R.id.tv_pd_Publish:
                String content =tv_pd_text.getText().toString().trim();

                Utils.setLocation(context);
                String lat = PrefUtil.getLat(context);
                String lon = PrefUtil.getLon(context);
                String picture="";
                presenter.Publishingdynamics(content,picture,lat,lon);
                break;



        }
    }


//    private View.OnClickListener onClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.btn_take_photo:
//                    System.out.println("btn_take_photo");
//                    break;
//                case R.id.btn_pick_photo:
//                    System.out.println("btn_pick_photo");
//                    break;
//                case R.id.btn_cancel:
//                    finish();
//                    break;
//            }
//        }
//    };

    private void showPopwindow() {
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        View popView = View.inflate(this, R.layout.cameraselection, null);

        Button btnCamera = (Button) popView.findViewById(R.id.btn_take_photo);
        Button btnAlbum = (Button) popView.findViewById(R.id.btn_pick_photo);
        Button btnCancel = (Button) popView.findViewById(R.id.btn_cancel);

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;

        final PopupWindow popWindow = new PopupWindow(popView,width,height);
        popWindow.setAnimationStyle(R.style.take_photo_anim);
        popWindow.setFocusable(true);
//        popWindow.setBackgroundDrawable(newBitmapDrawable());// 响应返回键必须的语句。
        //设置点击弹窗外隐藏自身
        popWindow.setFocusable(true);
        popWindow.setOutsideTouchable(true);

        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_take_photo:
                        popWindow.dismiss();
                        lighton();
                        break;
                    case R.id.btn_pick_photo:
                        popWindow.dismiss();
                        lighton();
                        break;
                    case R.id.btn_cancel:
                        popWindow.dismiss();
                        lighton();
                        break;
                }
                popWindow.dismiss();
            }
        };

        btnCamera.setOnClickListener(listener);
        btnAlbum.setOnClickListener(listener);
        btnCancel.setOnClickListener(listener);

        ColorDrawable dw = new ColorDrawable(0x30000000);
        popWindow.setBackgroundDrawable(dw);
        popWindow.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        setBackgroundAlpha(0.5f);
    }

    //设置屏幕背景透明效果
    public void setBackgroundAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

    /**
     * 设置手机屏幕亮度显示正常
     */
    private void lighton() {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1f;
        getWindow().setAttributes(lp);
    }



    @Override
    public void initView() {

    }

    @Override
    public void success(String data) {
        ToastUtil.showShortToast(context,"发表成功");
        finish();
    }

    @Override
    public void loading() {

    }

    @Override
    public void networkError() {
        ToastUtil.showShortToast(context,"网络连接异常");
    }

    @Override
    public void error(String msg) {
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void showFailed(String msg) {

    }
}
