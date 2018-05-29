package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.presenter.ModifyPresenter;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.ui.adapter.ShowPictureAdapter;
import com.yapin.shanduo.ui.contract.ModifyContract;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.DateTimePickDialogUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.ImageFilterUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.widget.ScrollGridLayoutManager;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dell on 2018/4/19.
 */

public class EditingformationAcivity extends BaseActivity implements ModifyContract.View ,ShowPictureAdapter.OnItemClickListener ,UploadContract.View{
    private ModifyPresenter presenter;
    private Context context;
    private Activity activity;

    String hometown;

    int mYear, mMonth, mDay;
    final int DATE_DIALOG = 1;

    private List<String> listShow = new ArrayList<>();


    @BindView(R.id.modify_tv_rg)
    TextView modify_tv_rg;
    @BindView(R.id.modify_tv_flicker)
    TextView modify_tv_flicker;
    @BindView(R.id.modify_et_nickname)
    TextView modify_et_nickname;
    @BindView(R.id.date_display)
    TextView date_display;
    @BindView(R.id.tv_Emotionalstate)
    TextView tv_Emotionalstate;
    @BindView(R.id.tv_Personalitysignature)
    TextView tv_Personalitysignature;
    @BindView(R.id.tv_Hometown)
    TextView tv_Hometown;
    @BindView(R.id.tv_Occupation)
    TextView tv_Occupation;
    @BindView(R.id.tv_School)
    TextView tv_School;
    @BindView(R.id.ib_Head_portrait)
    ImageButton ib_Head_portrait;
    @BindView(R.id.iv_background)
    ImageView iv_background;
    @BindView(R.id.ll_thebackground)
    LinearLayout ll_thebackground;

    String gender;
    String emotion;
    String name;
    String occupation;
    String signature;
    String school;
    private Bitmap bitmap;
    private ShowPictureAdapter showAdapter;
    private UploadPresenter uploadPresenter;


        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinginformation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
            uploadPresenter = new UploadPresenter();
            uploadPresenter.init(context ,this);

        modify_tv_flicker.setText(PrefJsonUtil.getProfile(context).getUserId());
        modify_et_nickname.setText(PrefJsonUtil.getProfile(context).getName());
        modify_tv_rg.setText(PrefJsonUtil.getProfile(context).getGender().equals("1")?"男":"女");
        date_display.setText(PrefJsonUtil.getProfile(context).getBirthday());
        tv_Emotionalstate.setText(PrefJsonUtil.getProfile(context).getEmotion());
        if (tv_Emotionalstate.equals("0")){
            tv_Emotionalstate.setText("保密");
        }else if (tv_Emotionalstate.equals("1")){
            tv_Emotionalstate.setText("已婚");
        }else {
            tv_Emotionalstate.setText("未婚");
        }
        tv_Personalitysignature.setText(PrefJsonUtil.getProfile(context).getSignature());
        tv_Hometown.setText(PrefJsonUtil.getProfile(context).getHometown());
        tv_Occupation.setText(PrefJsonUtil.getProfile(context).getOccupation());
        tv_School.setText(PrefJsonUtil.getProfile(context).getSchool());
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Head_portrait);
        GlideUtil.load(activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getBackground() , iv_background);

        if( !(TextUtils.isEmpty(PrefJsonUtil.getProfile(context).getBackground())) ){
            //异步处理
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //高斯模糊处理图片
                    bitmap = ImageFilterUtil.doBlur(getBitmap(ApiUtil.IMG_URL +PrefJsonUtil.getProfile(context).getBackground()), 10, false);
                    //处理完成后返回主线程
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv_background.setImageBitmap(bitmap);
                        }
                    });
                }
            }).start();
        }
        presenter = new ModifyPresenter();
        presenter.init(context,this);
    }

    //网络图片转Bitmap
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    @OnClick({R.id.fl_rg,R.id.fl_date,R.id.fl_emot,R.id.modify_et_nickname,
            R.id.fl_person,R.id.fl_hom,R.id.fl_scho,R.id.fl_occup,R.id.iv_back ,R.id.ib_Head_portrait , R.id.ll_thebackground})
    public void onClick(View v){
            switch (v.getId()){
                case R.id.ll_thebackground:     //修改背景图片
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("left", Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
                    bundle1.putInt("source", 0);
                    StartActivityUtil.start(activity, PictureFolderActivity.class, bundle1 , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW_THEBACKGROUND );
                    break;
                case R.id.ib_Head_portrait:     //修改头像
                    Bundle bundle = new Bundle();
                    bundle.putInt("left", Constants.COUNT_MAX_SHOW_PICTURE - listShow.size());
                    bundle.putInt("source", 0);
                    StartActivityUtil.start(activity, PictureFolderActivity.class, bundle , Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW );
                    break;
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.fl_rg:     //性别
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("请选择性别");
                    final String[] sex = {"男", "女"};
                    //    设置一个单项选择下拉框
                    /**
                     * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                     * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'男' 会被勾选上
                     * 第三个参数给每一个单选项绑定一个监听器
                     */
                    builder.setSingleChoiceItems(sex, getIsEvent(),new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // 自动生成的方法存根
                            if (which == 0) {//男
                                gender = "1";
                            }else if(which == 1){//女
                                gender = "0";
                            }
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
//                            Log.i("gender",gender.toString()+"");
                            presenter.modify("",gender,"","","","","","", "" , "");
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder.show();
                    break;
                case R.id.fl_date:     //出生年月
//                      show();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditingformationAcivity.this,
                            R.style.MyDatePickerDialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    mYear = year;
                                    mMonth = month;
                                    mDay = dayOfMonth;
                                    date_display.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                                    String birthday = date_display.getText().toString().trim();
                                    presenter.modify("","",birthday,"","","","","", "" , "");
                                }
                            },
                            mYear, mMonth, mDay);
                    //设置起始日期和结束日期
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    //datePicker.setMinDate();
                    datePicker.setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
                    break;
                case R.id.fl_emot:    //感情状态
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    final String[] emotions = {"已婚", "未婚","保密"};
                    //    设置一个单项选择下拉框
                    builder1.setSingleChoiceItems(emotions, getIsEvent(), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (which == 0) {
                                emotion = "1";
                            }else if(which == 1){
                                emotion = "2";
                            }else if (which ==2){
                                emotion = "0";
                            }
                        }
                    });
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            presenter.modify("","","",emotion,"","","","", "" , "");
                        }
                    });
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder1.show();
                    break;
                case R.id.modify_et_nickname:   //昵称
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                            builder2.setTitle("请输入你要修改的昵称");
                            //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                            View view = LayoutInflater.from(activity).inflate(R.layout.nicknamepop_up, null);
                            //    设置我们自己定义的布局文件作为弹出框的Content
                            builder2.setView(view);
                            final EditText et_nickname = (EditText)view.findViewById(R.id.nickname);
                            builder2.setPositiveButton("确定", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    name = et_nickname.getText().toString().trim();
                                    presenter.modify(name,"","","","","","","", "" , "");
                                }
                            });
                            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener()
                            {
                                @Override
                                public void onClick(DialogInterface dialog, int which)

                                {
                                }
                            });
                            builder2.show();
                            break;
                case R.id.fl_person:
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                    builder3.setTitle("请输入你要修改的个性签名");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view1 = LayoutInflater.from(activity).inflate(R.layout.personalitysignature, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder3.setView(view1);
                    final EditText tv_Personalitysignature = (EditText)view1.findViewById(R.id.Personalitysignature);
                    builder3.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            signature = tv_Personalitysignature.getText().toString().trim();
                            presenter.modify("","","","",signature,"","","", "" , "");
                        }
                    });
                    builder3.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder3.show();
                    break;
                case R.id.fl_hom:  //家乡
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(activity);
                    builder4.setTitle("请输入你要修改的家乡");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view2 = LayoutInflater.from(activity).inflate(R.layout.hometown, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder4.setView(view2);
                    final EditText tv_Hometown = (EditText)view2.findViewById(R.id.hometown);
                    builder4.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            hometown = tv_Hometown.getText().toString().trim();
                            presenter.modify("","","","","",hometown,"","", "" , "");
                        }
                    });
                    builder4.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder4.show();
                    break;
                case R.id.fl_scho:    //毕业学校
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(activity);
                    builder5.setTitle("请输入你要修改的学校");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view3 = LayoutInflater.from(activity).inflate(R.layout.school, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder5.setView(view3);
                    final EditText tv_School = (EditText)view3.findViewById(R.id.school);
                    builder5.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            school = tv_School.getText().toString().trim();
                            presenter.modify("","","","","","","",school, "" , "");
                        }
                    });
                    builder5.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder5.show();
                    break;
                case R.id.fl_occup:    //职业
                    AlertDialog.Builder builder6 = new AlertDialog.Builder(activity);
                    builder6.setTitle("请输入你要修改的职业");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view4 = LayoutInflater.from(activity).inflate(R.layout.occupation, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder6.setView(view4);
                    final EditText tv_Occupation = (EditText)view4.findViewById(R.id.Occupation);
                    builder6.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            occupation = tv_Occupation.getText().toString().trim();
                            presenter.modify("","","","","","",occupation,"", "" , "");
                        }
                    });
                    builder6.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                        }
                    });
                    builder6.show();
                    break;
            }

    }

    @Override
    public void initView() {
        listShow.add("");

    }

    @Override
    public void success(String data) {
        if ("1".equals(gender)) {//男
            modify_tv_rg.setText("男");
        }else if("0".equals(gender)){//女
            modify_tv_rg.setText("女");
        }else if ("0".equals(emotion)) {
            tv_Emotionalstate.setText("保密");
        }else if("1".equals(emotion)){
            tv_Emotionalstate.setText("已婚");
        }else if("2".equals(emotion)){
            tv_Emotionalstate.setText("未婚");
        }
        modify_et_nickname.setText(name);
        tv_Personalitysignature.setText(signature);
        tv_Hometown.setText(hometown);
        tv_School.setText(school);
        tv_Occupation.setText(occupation);

        ToastUtil.showShortToast(context,"修改成功");

    }

    private void show(List<String> paths){
        int size = listShow.size();
        listShow.addAll(paths);
        listShow.remove(0);
        uploadPresenter.upload(listShow);
//        showAdapter.notifyItemRangeInserted(size, listShow.size() - size);
    }

    private int uploadType = 1;

    @Override
    public void uploadSuccess(String imgIds) {
        publishTrend(imgIds);
    }

    public void publishTrend(String imgIds){
        if (uploadType == 1) {
            presenter.modify("", "", "", "", "", "", "", "", imgIds, "");
        }else if (uploadType == 2){
            presenter.modify("", "", "", "", "", "", "", "", "", imgIds);
        }
    }

    @Override
    public void loading() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;
        List<String> paths = new ArrayList<>();
        switch (requestCode) {
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW: {
                if (data == null) {
                    return;
                }
                paths.addAll(data.getStringArrayListExtra("path"));
                uploadType = 1;
//                presenter.upload(paths);
                break;
            }
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW_THEBACKGROUND:{
                if (data == null) {
                    return;
                }
                paths.addAll(data.getStringArrayListExtra("path"));
                uploadType = 2;
//                presenter.upload(paths);
                break;
            }

        }

        show(paths);
        super.onActivityResult(requestCode, resultCode, data);
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

    @Override
    public void onItemClick(int position, int source) {

    }


}
