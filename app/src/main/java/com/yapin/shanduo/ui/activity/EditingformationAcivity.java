package com.yapin.shanduo.ui.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.yapin.shanduo.R;
import com.yapin.shanduo.app.ShanDuoPartyApplication;
import com.yapin.shanduo.im.model.UserInfo;
import com.yapin.shanduo.model.entity.TokenInfo;
import com.yapin.shanduo.presenter.ModifyPresenter;
import com.yapin.shanduo.presenter.UploadPresenter;
import com.yapin.shanduo.presenter.UserDetailPresenter;
import com.yapin.shanduo.ui.adapter.ShowPictureAdapter;
import com.yapin.shanduo.ui.contract.ModifyContract;
import com.yapin.shanduo.ui.contract.UploadContract;
import com.yapin.shanduo.ui.contract.UserDetailContract;
import com.yapin.shanduo.ui.manage.UserManage;
import com.yapin.shanduo.utils.ApiUtil;
import com.yapin.shanduo.utils.Constants;
import com.yapin.shanduo.utils.DateTimePickDialogUtil;
import com.yapin.shanduo.utils.GlideUtil;
import com.yapin.shanduo.utils.ImageFilterUtil;
import com.yapin.shanduo.utils.PrefJsonUtil;
import com.yapin.shanduo.utils.StartActivityUtil;
import com.yapin.shanduo.utils.ToastUtil;
import com.yapin.shanduo.utils.Utils;
import com.yapin.shanduo.widget.EmojiEditText;
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

public class EditingformationAcivity extends BaseActivity implements ModifyContract.View ,
        ShowPictureAdapter.OnItemClickListener ,UploadContract.View , UserDetailContract.View{
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
    String birthday;
    private Bitmap bitmap;
    private ShowPictureAdapter showAdapter;
    private UploadPresenter uploadPresenter;
    private UserDetailPresenter userDetailPresenter;

    int a ;

    private String head_path;
    private String bg_path;

        @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editinginformation);
        context = ShanDuoPartyApplication.getContext();
        activity = this;
        ButterKnife.bind(this);
            uploadPresenter = new UploadPresenter();
            uploadPresenter.init(context ,this);
            userDetailPresenter = new UserDetailPresenter();
            userDetailPresenter.init(this);

            modify_tv_flicker.setText(PrefJsonUtil.getProfile(context).getUserId());

            if (PrefJsonUtil.getProfile(context).getName() == null){
                modify_et_nickname.setText("还没有填写哦！");
            }else {
                modify_et_nickname.setText(Utils.unicodeToString(PrefJsonUtil.getProfile(context).getName()));
            }
            modify_tv_rg.setText(PrefJsonUtil.getProfile(context).getGender().equals("1")?"男":"女");
            date_display.setText(PrefJsonUtil.getProfile(context).getBirthday());

                if ("0".equals(PrefJsonUtil.getProfile(context).getEmotion())){
                    tv_Emotionalstate.setText("保密");
                }else if ("1".equals(PrefJsonUtil.getProfile(context).getEmotion())){
                    tv_Emotionalstate.setText("已婚");
                }else {
                    tv_Emotionalstate.setText("未婚");
                }

            if (PrefJsonUtil.getProfile(context).getSignature() == null){
                tv_Personalitysignature.setText("还没有填写哦！");
            }else {
                tv_Personalitysignature.setText(Utils.unicodeToString(PrefJsonUtil.getProfile(context).getSignature()));
            }
            if (PrefJsonUtil.getProfile(context).getHometown() == null){
                tv_Hometown.setText("还没有填写哦！");
            }else {
                tv_Hometown.setText(PrefJsonUtil.getProfile(context).getHometown());
            }
            if (PrefJsonUtil.getProfile(context).getOccupation() == null){
                tv_Occupation.setText("还没有填写哦！");
            }else {
                tv_Occupation.setText(PrefJsonUtil.getProfile(context).getOccupation());
            }
            if (PrefJsonUtil.getProfile(context).getSchool() == null){
                tv_School.setText("还没有填写哦！");
            }else {
                tv_School.setText(PrefJsonUtil.getProfile(context).getSchool());
            }
        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Head_portrait);

        if (PrefJsonUtil.getProfile(context).getBackground() == null){
            iv_background.setImageDrawable(getResources().getDrawable(R.drawable.icin_vip_back));
        }else {
            GlideUtil.load(activity, ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getBackground(), iv_background);
        }
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
                    a = 1;
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("请选择性别");
                    final String[] sex = {"女", "男"};
                    //    设置一个单项选择下拉框
                    /**
                     * 第一个参数指定我们要显示的一组下拉单选框的数据集合
                     * 第二个参数代表索引，指定默认哪一个单选框被勾选上，0表示默认'男' 会被勾选上
                     * 第三个参数给每一个单选项绑定一个监听器
                     */
                    final AlertDialog dialoga = builder.show();
                    builder.setSingleChoiceItems(sex, Integer.parseInt(PrefJsonUtil.getProfile(context).getGender()),new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            // 自动生成的方法存根
                            if (which == 0) {//男
                                gender = "0";
                            }else if(which == 1){//女
                                gender = "1";
                            }
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (gender == null) {
                                dialoga.dismiss();
                            } else {
                                presenter.modify("", gender, "", "", "", "", "", "", "", "");
                                dialoga.dismiss();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialoga.dismiss();
                        }
                    });
                    builder.show();
                    break;
                case R.id.fl_date:     //出生年月
                    a = 2;
                    DatePickerDialog datePickerDialog = new DatePickerDialog(EditingformationAcivity.this,R.style.MyDatePickerDialogTheme,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    mYear = year;
                                    mMonth = month;
                                    mDay = dayOfMonth;
                                    date_display.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                                    birthday = date_display.getText().toString().trim();
                                    presenter.modify("","",birthday,"","","","","", "" , "");
                                }
                            },
                            mYear, mMonth, mDay);
                    //设置起始日期和结束日期
                    DatePicker datePicker = datePickerDialog.getDatePicker();
                    //datePicker.setMinDate();
                    datePicker.setMaxDate(System.currentTimeMillis());
                    datePickerDialog.show();
//                    Calendar cal = Calendar.getInstance();
//                    final DatePickerDialog mDialog =new DatePickerDialog(this,null,
//                            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
//                    //手动设置按钮
//                    mDialog.setButton(DialogInterface.BUTTON_POSITIVE,"完成",new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog,int which){
//                            //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
//                            DatePicker datePicker = mDialog.getDatePicker();
//                            int year = datePicker.getYear();
//                            int month = datePicker.getMonth();
//                            int day = datePicker.getDayOfMonth();
//                            System.out.println(year +","+ month +","+ day);
//                        }
//                    });
//
//                    //取消按钮，如果不需要直接不设置即可
//                    mDialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",new DialogInterface.OnClickListener(){
//                        @Override
//                        public void onClick(DialogInterface dialog,int which){
//                            System.out.println("BUTTON_NEGATIVE~~");
//                        }
//                    });
//                    mDialog.show();
                    break;
                case R.id.fl_emot:    //感情状态
                    a = 3;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                    final String[] emotions = {"保密" , "已婚", "未婚"};
                    //    设置一个单项选择下拉框
                    final AlertDialog dialogb = builder1.show();
                    builder1.setSingleChoiceItems(emotions, Integer.parseInt(PrefJsonUtil.getProfile(context).getEmotion()), new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            if (which == 0) {
                                emotion = "0";
                            }else if(which == 1){
                                emotion = "1";
                            }else if (which ==2){
                                emotion = "2";
                            }
                        }
                    });
                    builder1.setPositiveButton("确定", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (emotion == null) {
                                dialogb.dismiss();
                            } else {
                                presenter.modify("", "", "", emotion, "", "", "", "", "", "");
                                dialogb.dismiss();
                            }
                        }
                    });
                    builder1.setNegativeButton("取消", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            dialogb.dismiss();
                        }
                    });
                    builder1.show();
                    break;
                case R.id.modify_et_nickname:   //昵称
                            a = 4;
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(activity);
                            builder2.setTitle("请输入你要修改的昵称");
                            //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                            View view = LayoutInflater.from(activity).inflate(R.layout.nicknamepop_up, null);
                            //    设置我们自己定义的布局文件作为弹出框的Content
                            builder2.setView(view);
                            final EmojiEditText et_nickname = (EmojiEditText)view.findViewById(R.id.nickname);
                            et_nickname.setText(PrefJsonUtil.getProfile(context).getName());
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
                    a = 5;
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(activity);
                    builder3.setTitle("请输入你要修改的个性签名");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view1 = LayoutInflater.from(activity).inflate(R.layout.personalitysignature, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder3.setView(view1);
                    final EditText tv_Personalitysignature = (EditText)view1.findViewById(R.id.Personalitysignature);
                    tv_Personalitysignature.setText(Utils.unicodeToString(PrefJsonUtil.getProfile(context).getSignature()));
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
                    a = 6;
                    AlertDialog.Builder builder4 = new AlertDialog.Builder(activity);
                    builder4.setTitle("请输入你要修改的家乡");
                    View view2 = LayoutInflater.from(activity).inflate(R.layout.hometown, null);
                    builder4.setView(view2);
                    final EmojiEditText tv_Hometown = (EmojiEditText)view2.findViewById(R.id.hometown);
                    tv_Hometown.setText(PrefJsonUtil.getProfile(context).getHometown());
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
                    a = 7;
                    AlertDialog.Builder builder5 = new AlertDialog.Builder(activity);
                    builder5.setTitle("请输入你要修改的学校");
                    View view3 = LayoutInflater.from(activity).inflate(R.layout.school, null);
                    builder5.setView(view3);
                    final EmojiEditText tv_School = (EmojiEditText)view3.findViewById(R.id.school);
                    tv_School.setText(PrefJsonUtil.getProfile(context).getSchool());
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
                    a = 8;
                    AlertDialog.Builder builder6 = new AlertDialog.Builder(activity);
                    builder6.setTitle("请输入你要修改的职业");
                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
                    View view4 = LayoutInflater.from(activity).inflate(R.layout.occupation, null);
                    //    设置我们自己定义的布局文件作为弹出框的Content
                    builder6.setView(view4);
                    final EmojiEditText tv_Occupation = (EmojiEditText)view4.findViewById(R.id.Occupation);
                    tv_Occupation.setText(PrefJsonUtil.getProfile(context).getOccupation());
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
        ToastUtil.showShortToast(context,data);
        TokenInfo info = PrefJsonUtil.getProfile(context);
        if (a==6) {
            info.setHometown(hometown);
        }else if (a==4) {
            info.setName(name);
        }else if (a==5) {
            info.setSignature(signature);
        }else if (a ==7) {
            info.setSchool(school);
        }else if (a==8) {
            info.setOccupation(occupation);
        }else if (a==1) {
            info.setGender(gender);
        }else if (a==3) {
            info.setEmotion(emotion);
        }else if (a==2) {
            info.setBirthday(birthday);
        }

        Gson gs = new Gson();
        String objectStr = gs.toJson(info);//把对象转为JSON格式的字符串
        PrefJsonUtil.setProfile(context ,objectStr);
        Log.i("objectStr", "success: "+objectStr);
        String gender = PrefJsonUtil.getProfile(context).getEmotion();
        userDetailPresenter.start();

        if ("1".equals(PrefJsonUtil.getProfile(context).getGender())) {//男
            modify_tv_rg.setText("男");
        }else if("0".equals(PrefJsonUtil.getProfile(context).getGender())){//女
            modify_tv_rg.setText("女");
        }
        if ("0".equals(PrefJsonUtil.getProfile(context).getEmotion())) {
            tv_Emotionalstate.setText("保密");
        }else if("1".equals(PrefJsonUtil.getProfile(context).getEmotion())){
            tv_Emotionalstate.setText("已婚");
        }else if("2".equals(PrefJsonUtil.getProfile(context).getEmotion())){
            tv_Emotionalstate.setText("未婚");
        }
        date_display.setText(PrefJsonUtil.getProfile(context).getBirthday());
        if (PrefJsonUtil.getProfile(context).getName() == null){
            modify_et_nickname.setText("还没有填写哦！");
        }else {
            modify_et_nickname.setText(PrefJsonUtil.getProfile(context).getName());
        }
        if (PrefJsonUtil.getProfile(context).getSignature() == null){
            tv_Personalitysignature.setText("还没有填写哦！");
        }else {
            tv_Personalitysignature.setText(Utils.unicodeToString(PrefJsonUtil.getProfile(context).getSignature()));
        }
        if (PrefJsonUtil.getProfile(context).getHometown() == null){
            tv_Hometown.setText("还没有填写哦！");
        }else {
            tv_Hometown.setText(PrefJsonUtil.getProfile(context).getHometown());
        }
        if (PrefJsonUtil.getProfile(context).getOccupation() == null){
            tv_Occupation.setText("还没有填写哦！");
        }else {
            tv_Occupation.setText(PrefJsonUtil.getProfile(context).getOccupation());
        }
        if (PrefJsonUtil.getProfile(context).getSchool() == null){
            tv_School.setText("还没有填写哦！");
        }else {
            tv_School.setText(PrefJsonUtil.getProfile(context).getSchool());
        }

    }

    /**
     * 调用onCreate(), 目的是刷新数据,
     * 从另一activity界面返回到该activity界面时, 此方法自动调用
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    private void show(List<String> paths){
        int size = listShow.size();
        listShow.addAll(paths);
        listShow.remove(0);
        uploadPresenter.upload(listShow);
    }

    private int uploadType = 1;

    @Override
    public void uploadSuccess(String imgIds) {
        publishTrend(imgIds);
        if (uploadType == 1) {
            Glide.with(activity).load(head_path).transform(GlideUtil.transform(context)).into(ib_Head_portrait);
        }else if (uploadType == 2){
            Glide.with(activity).load(bg_path).into(iv_background);
        }
    }

    public void publishTrend(String imgIds){
        if (uploadType == 1) {
            presenter.modify("", "", "", "", "", "", "", "", imgIds, "");
        }else if (uploadType == 2){
            presenter.modify("", "", "", "", "", "", "", "", "", imgIds);
        }
    }

    @Override
    public void dataSuccess(String data) {

        UserInfo.getInstance().setUserSig(PrefJsonUtil.getProfile(context).getUserSig());
        UserInfo.getInstance().setId(PrefJsonUtil.getProfile(context).getUserId());

        GlideUtil.load(context ,activity , ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getPicture() , ib_Head_portrait);

        if (PrefJsonUtil.getProfile(context).getBackground() == null){
            iv_background.setImageDrawable(getResources().getDrawable(R.drawable.icin_vip_back));
        }else {
            GlideUtil.load(activity, ApiUtil.IMG_URL + PrefJsonUtil.getProfile(context).getBackground(), iv_background);
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
                head_path = data.getStringArrayListExtra("path").get(0);
                paths.addAll(data.getStringArrayListExtra("path"));
                uploadType = 1;
                break;
            }
            case Constants.REQUEST_CODE_FOR_SELECT_PHOTO_SHOW_THEBACKGROUND:{
                if (data == null) {
                    return;
                }
                bg_path = data.getStringArrayListExtra("path").get(0);
                paths.addAll(data.getStringArrayListExtra("path"));
                uploadType = 2;
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
        ToastUtil.showShortToast(context,msg);
    }

    @Override
    public void onItemClick(int position, int source) {

    }


}
